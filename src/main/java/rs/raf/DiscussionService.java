package rs.raf;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.entity.Task;
import com.alipay.sofa.jraft.error.RaftError;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.raf.grpc.*;
import rs.raf.grpc.DiscussionGrpc.DiscussionImplBase;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DiscussionService extends DiscussionImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(DiscussionService.class);

    private final DiscussionServer discussionServer;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public DiscussionService(DiscussionServer discussionServer) {
        this.discussionServer = discussionServer;
    }

    private boolean isLeader() {
        return this.discussionServer.getStateMachine().isLeader();
    }

    @Override
    public void sendNewTopic(NewTopicRequest request, StreamObserver<Response> responseObserver) {
        Topic topic = new Topic.Builder()
                .setTitle(request.getTitle())
                .setComments(new ArrayList<>())
                .build();

        Comment comment = new Comment.Builder()
                .setMessage(request.getComment())
                .setTimestamp(LocalDateTime.now())
                .setParent(null)
                .setReplies(new ArrayList<>())
                .build();

        if (isLeader()) {
            Operation operation = new Operation(OperationType.SEND_NEW_TOPIC, topic, comment);

            Closure closure = status -> {
                if (status.isOk()) {
                    Response response = Response.newBuilder()
                            .setResponse("Operation successful")
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } else {
                    System.err.println("An error occurred while adding a topic: " + status.getErrorMsg());
                }
            };
            executeOperation(operation, closure);
        } else {
            System.err.println("Cannot apply operation. Current node is not the leader.");
        }
    }

    private void executeOperation(Operation operation, Closure closure) {
        try {
            discussionServer.getNode().apply(new Task(ByteBuffer.wrap(SerializerManager.getSerializer(SerializerManager.Hessian2).serialize(operation)), closure, -1));
        } catch (CodecException e) {
            String errorMsg = "Fail to encode Operation";
            LOG.error(errorMsg, e);
            closure.run(new Status(RaftError.EINTERNAL, errorMsg));
        }
    }

    @Override
    public void sendNewCommentToTopic(NewCommentRequest request, StreamObserver<Response> responseObserver) {
        String topicTitle = request.getTopic();
        Comment comment = new Comment.Builder()
                .setMessage(request.getMessage())
                .setTimestamp(LocalDateTime.now())
                .setParent(null)
                .setReplies(new ArrayList<>())
                .build();

        if (isLeader()) {
            Operation operation = new Operation(OperationType.SEND_NEW_COMMENT_TO_TOPIC, topicTitle, comment);

            Closure closure = status -> {
                if (status.isOk()) {
                    Response response = Response.newBuilder()
                            .setResponse("Operation successful")
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } else {
                    System.err.println("An error occurred while adding a comment to a topic: " + status.getErrorMsg());
                }
            };
            executeOperation(operation, closure);
        } else {
            System.err.println("Cannot apply operation. Current node is not the leader.");
        }
    }

    @Override
    public void replyToComment(CommentReplyRequest request, StreamObserver<Response> responseObserver) {
        String topicTitle = request.getTopic();
        int parentCommentId = request.getParentCommentId();
        Comment reply = new Comment.Builder()
                .setMessage(request.getMessage())
                .setTimestamp(LocalDateTime.now())
                .setParent(discussionServer.getStateMachine().getCommentById(parentCommentId))
                .setReplies(new ArrayList<>())
                .build();

        if (isLeader()) {
            Operation operation = new Operation(OperationType.REPLY_TO_COMMENT, topicTitle, reply, parentCommentId);

            Closure closure = status -> {
                if (status.isOk()) {
                    Response response = Response.newBuilder()
                            .setResponse("Operation successful")
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } else {
                    System.err.println("An error occurred while replying to a comment: " + status.getErrorMsg());
                }
            };
            executeOperation(operation, closure);
        } else {
            System.err.println("Cannot apply operation. Current node is not the leader.");
        }
    }

    @Override
    public void updateMyComment(CommentUpdateRequest request, StreamObserver<Response> responseObserver) {
        long t = 30;
        String topicTitle = request.getTopic();
        int commentId = request.getCommentId();
        Comment existingComment = discussionServer.getStateMachine().getCommentById(commentId);

        if (existingComment == null) {
            System.err.println("Comment not found: " + commentId);
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime commentTime = existingComment.getTimestamp();

        long timeDifference = ChronoUnit.SECONDS.between(commentTime, now);

        if (timeDifference <= t) {
            existingComment.setMessage(request.getMessage());
            existingComment.setTimestamp(LocalDateTime.now());

            if (isLeader()) {
                Operation operation = new Operation(OperationType.UPDATE_MY_COMMENT, topicTitle, existingComment, commentId);

                Closure closure = status -> {
                    if (status.isOk()) {
                        Response response = Response.newBuilder()
                                .setResponse("Operation successful")
                                .build();
                        responseObserver.onNext(response);
                        responseObserver.onCompleted();
                    } else {
                        System.err.println("An error occurred while updating a comment: " + status.getErrorMsg());
                    }
                };
                executeOperation(operation, closure);
            } else {
                System.err.println("Cannot apply operation. Current node is not the leader.");
            }
        } else {
            System.err.println("Cannot update comment. More than " + t + " seconds have passed since it was created.");
        }
    }

    @Override
    public void deleteMyComment(CommentDeleteRequest request, StreamObserver<Response> responseObserver) {
        String topicTitle = request.getTopic();
        int commentId = request.getCommentId();
        Comment existingComment = discussionServer.getStateMachine().getCommentById(commentId);

        if (existingComment == null) {
            System.err.println("Comment not found: " + commentId);
            return;
        }

        if (isLeader()) {
            Operation operation = new Operation(OperationType.DELETE_MY_COMMENT, topicTitle, commentId);

            Closure closure = status -> {
                if (status.isOk()) {
                    Response response = Response.newBuilder()
                            .setResponse("Operation successful")
                            .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } else {
                    System.err.println("An error occurred while deleting a comment: " + status.getErrorMsg());
                }
            };
            executeOperation(operation, closure);
        } else {
            System.err.println("Cannot apply operation. Current node is not the leader.");
        }
    }

    @Override
    public void getTopicsList(TopicsRequest request, StreamObserver<TopicsResponse> responseObserver) {
        lock.readLock().lock();
        try {
            List<Topic> topics = discussionServer.getStateMachine().getTopicsList();

            TopicsResponse.Builder responseBuilder = TopicsResponse.newBuilder();
            for (Topic topic : topics) {
                responseBuilder.addTopics(topic.getTitle());
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getTopicComments(TopicCommentsRequest request, StreamObserver<TopicCommentsResponse> responseObserver) {
        lock.readLock().lock();
        try {
            String topicTitle = request.getTopic();
            Topic topic = discussionServer.getStateMachine().findTopic(topicTitle);

            if (topic == null) {
                System.err.println("Topic not found: " + topicTitle);
                return;
            }

            TopicCommentsResponse.Builder responseBuilder = TopicCommentsResponse.newBuilder();
            for (Comment comment : topic.getComments()) {
                CommentResponse commentResponse = CommentResponse.newBuilder()
                        .setMessage(comment.getMessage())
                        .setCommentId(comment.getId())
                        .build();
                responseBuilder.addComments(commentResponse);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } finally {
            lock.readLock().unlock();
        }
    }
}
