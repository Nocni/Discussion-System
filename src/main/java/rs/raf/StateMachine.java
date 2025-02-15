package rs.raf;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.error.RaftError;
import com.alipay.sofa.jraft.error.RaftException;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.alipay.sofa.jraft.util.NamedThreadFactory;
import com.alipay.sofa.jraft.util.ThreadPoolUtil;
import com.caucho.hessian.io.Hessian2Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

public class StateMachine extends StateMachineAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(StateMachine.class);

    private static ThreadPoolExecutor executor = ThreadPoolUtil
            .newBuilder()
            .poolName("JRAFT_EXECUTOR")
            .enableMetric(true)
            .coreThreads(3)
            .maximumThreads(5)
            .keepAliveSeconds(60L)
            .workQueue(new SynchronousQueue<>())
            .threadFactory(
                    new NamedThreadFactory("JRaft-Executor-", true)).build();

    private CopyOnWriteArrayList<Topic> topics = new CopyOnWriteArrayList<>();
    private ConcurrentHashMap<Integer, Comment> comments = new ConcurrentHashMap<>();

    private final AtomicLong leaderTerm = new AtomicLong(-1);

    File file = new File("data");
    String absolutePath = file.getAbsolutePath();

    public boolean isLeader() {
        return this.leaderTerm.get() > 0;
    }

    public List<Topic> getTopicsList() {
        return new ArrayList<>(this.topics);
    }

    public List<Integer> getCommentIds() {
        return new ArrayList<>(comments.keySet());
    }

    public Topic findTopic (String topicTitle) {
        for (Topic topic : topics) {
            if ((topic.getTitle().equals(topicTitle)))
                return topic;
        }
        System.err.println("Topic not found!");
        return null;
    }

    public Comment getCommentById(int commentId) {
        return comments.get(commentId);
    }

    @Override
    public void onApply(final Iterator iter) {
        while (iter.hasNext()) {
            Operation operation;
            Closure closure = null;

            if (iter.done() != null) {
                closure = iter.done();
            }
            final ByteBuffer data = iter.getData();
            try {
                operation = SerializerManager.getSerializer(SerializerManager.Hessian2).deserialize(
                        data.array(), Operation.class.getName());

                System.out.println("Operation received: \n" + operation);
            } catch (CodecException e) {
                e.printStackTrace();
                System.out.println("Error while deserializing operation");
                LOG.error("Fail to decode AccountOperation", e);
                throw new RuntimeException(e);
            }

            if (operation != null) {
                System.out.println("Operation not null");
                OperationType operationType = operation.getOperationType();
                System.out.println("Operation type: " + operationType);

                switch (operationType) {
                    case SEND_NEW_TOPIC:
                        System.out.println("SEND_NEW_TOPIC");
                        Topic newTopic = operation.getTopic();
                        Comment firstComment = operation.getComment();
                        if (newTopic != null && firstComment != null) {
                            topics.add(newTopic);
                            newTopic.getComments().add(firstComment);
                            System.out.println("New topic added: " + newTopic.getTitle() + " with first comment: " + firstComment.getMessage() + " by " + firstComment.getId());
                        } else {
                            System.err.println("Invalid operation data for SEND_NEW_TOPIC. Expected a Topic object.");
                        }
                        break;
                    case SEND_NEW_COMMENT_TO_TOPIC:
                        System.out.println("SEND_NEW_COMMENT_TO_TOPIC");
                        String topicTitle = operation.getTopicTitle();
                        Comment newComment = operation.getComment();
                        Topic topic = findTopic(topicTitle);
                        if (topics.contains(topic) && newComment != null) {
                            assert topic != null;
                            topic.getComments().add(newComment);
                            comments.put(newComment.getId(), newComment);
                        } else {
                            System.err.println("Invalid operation data for SEND_NEW_TOPIC. Expected a Topic object.");
                        }
                        break;
                    case REPLY_TO_COMMENT:
                        System.out.println("REPLY_TO_COMMENT");
                        String replyTopicTitle = operation.getTopicTitle();
                        Topic replyTopic = findTopic(replyTopicTitle);
                        Comment replyComment = operation.getComment();
                        int parentCommentId = operation.getCommentId();
                        if (topics.contains(replyTopic) && replyComment != null && parentCommentId >= 0) {
                            Comment parentComment = null;
                            assert replyTopic != null;
                            for (Comment comment : replyTopic.getComments()) {
                                if (comment.getId() == parentCommentId) {
                                    parentComment = comment;
                                    break;
                                }
                            }
                            if (parentComment != null) {
                                parentComment.getReplies().add(replyComment);
                                comments.put(replyComment.getId(), replyComment);
                            } else {
                                System.err.println("Parent comment not found: " + parentCommentId);
                            }
                        } else {
                            System.err.println("Invalid operation data for REPLY_TO_COMMENT. Expected a Topic object and a Comment object.");
                        }
                        break;
                    case UPDATE_MY_COMMENT:
                        System.out.println("UPDATE_MY_COMMENT");
                        String updateTopicTitle = operation.getTopicTitle();
                        Topic updateTopic = findTopic(updateTopicTitle);
                        Comment updateComment = operation.getComment();
                        int commentId = operation.getCommentId();
                        if (topics.contains(updateTopic) && updateComment != null && commentId >= 0) {
                            assert updateTopic != null;
                            for (Comment comment : updateTopic.getComments()) {
                                if (comment.getId() == commentId) {
                                    comment.setMessage(updateComment.getMessage());
                                    comments.put(comment.getId(), comment);
                                    break;
                                } else System.err.println("Comment not found: " + commentId);
                            }
                        } else {
                            System.err.println("Invalid operation data for UPDATE_MY_COMMENT. Expected a Topic object and a Comment object.");
                        }
                        break;
                    case DELETE_MY_COMMENT:
                        System.out.println("DELETE_MY_COMMENT");
                        Topic deleteTopic = operation.getTopic();
                        int deleteCommentId = operation.getCommentId();
                        if (topics.contains(deleteTopic) && deleteCommentId >= 0) {
                            for (Comment comment : deleteTopic.getComments()) {
                                if (comment.getId() == deleteCommentId) {
                                    deleteTopic.getComments().remove(comment);
                                    comments.remove(comment.getId());
                                    break;
                                } else System.err.println("Comment not found: " + deleteCommentId);
                            }
                        } else {
                            System.err.println("Invalid operation data for DELETE_MY_COMMENT. Expected a Topic object and a Comment id.");
                        }
                        break;
                    default:
                        System.err.println("Unknown operation type: " + operationType);
                        break;
                }
                if (closure != null) {
                    // SLANJE ODGOVORA KLIJENTU!!!
                    closure.run(Status.OK());
                }
            } else {
                System.err.println("Operation is null");
            }
            iter.next();
        }
    }

    @Override
    public void onSnapshotSave(final SnapshotWriter writer, final Closure done) {
        executor.submit(() -> {
            StateMachineSnapshot snapshot = createSnapshot();
            final SnapshotFile snapshotFile = new SnapshotFile(writer.getPath() + File.separator + "data");
            if (snapshotFile.save(snapshot)) {
                if (writer.addFile("data")) {
                    done.run(Status.OK());
                } else {
                    done.run(new Status(RaftError.EIO, "Fail to add file to writer"));
                }
            } else {
                done.run(new Status(RaftError.EIO, "Fail to save state machine snapshot %s", snapshotFile.getPath()));
            }
        });
    }

    @Override
    public void onError(final RaftException e) {
        LOG.error("Raft error: {}", e, e);
    }

    @Override
    public boolean onSnapshotLoad(final SnapshotReader reader) {
        String snapshotPath = reader.getPath() + File.separator + "data";
        File snapshotFile1 = new File(snapshotPath);
        System.out.println("Absolute path of the snapshot file: " + snapshotFile1.getAbsolutePath());

        if (isLeader()) {
            LOG.warn("Leader is not supposed to load snapshot");
            return false;
        }
        if (reader.getFileMeta("data") == null) {
            LOG.error("Fail to find data file in {}", reader.getPath());
            return false;
        }
        System.out.println("Snapshot path: " + reader.getPath());

        final SnapshotFile snapshotFile = new SnapshotFile(reader.getPath() + File.separator + "data");
        StateMachineSnapshot snapshot = snapshotFile.load();
        if (snapshot != null) {
            restoreFromSnapshot(snapshot);
            return true;
        } else {
            LOG.error("Fail to load snapshot from {}", snapshotFile.getPath());
            return false;
        }
    }

    @Override
    public void onLeaderStart(final long term) {
        this.leaderTerm.set(term);
        super.onLeaderStart(term);
    }

    @Override
    public void onLeaderStop(final Status status) {
        this.leaderTerm.set(-1);
        super.onLeaderStop(status);
    }

    public StateMachineSnapshot createSnapshot() {
        return new StateMachineSnapshot(new CopyOnWriteArrayList<>(this.topics), new ConcurrentHashMap<>(this.comments), absolutePath);
    }

    public void restoreFromSnapshot(StateMachineSnapshot snapshot) {
        this.topics = snapshot.getTopics();
        this.comments = snapshot.getComments();
    }

}
