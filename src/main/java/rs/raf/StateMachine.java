package rs.raf;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void onApply(Iterator iter) {
        while (iter.hasNext()) {
            ByteBuffer buffer = iter.next();
            Operation operation = null;
            System.out.println("Operation received: \n" + buffer);
            try {
                System.out.println("Operation received: \n" + buffer);
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                System.out.println("Operation received: \n" + bytes);

                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis);
                System.out.println("ObjectInputStream created");
                operation = (Operation) ois.readObject();
                System.out.println("Operation type: " + operation.getOperationType() + " received");
                System.out.println("Operation data: " + operation.toString());
            } catch (IOException | ClassNotFoundException e) {
                LOG.error("Error during operation deserialization", e);
                e.printStackTrace();
                System.out.println("Error during operation deserialization");
            }

            if (operation != null) {
                System.out.println("Operation not null");
                OperationType operationType = operation.getOperationType();

                switch (operationType) {
                    case SEND_NEW_TOPIC:
                        System.out.println("SEND_NEW_TOPIC");
                        Topic newTopic = operation.getTopic();
                        Comment firstComment = operation.getComment();
                        if (newTopic != null && firstComment != null) {
                            topics.add(newTopic);
                            newTopic.getComments().add(firstComment);
                            System.out.println("New topic added: " + newTopic.getTitle() + " with first comment: " + firstComment.getMessage());
                        } else {
                            System.err.println("Invalid operation data for SEND_NEW_TOPIC. Expected a Topic object.");
                        }
                        break;
                    case SEND_NEW_COMMENT_TO_TOPIC:
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
            }
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
        if (isLeader()) {
            LOG.warn("Leader is not supposed to load snapshot");
            return false;
        }
        if (reader.getFileMeta("data") == null) {
            LOG.error("Fail to find data file in {}", reader.getPath());
            return false;
        }
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
