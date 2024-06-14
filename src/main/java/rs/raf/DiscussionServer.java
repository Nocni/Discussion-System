package rs.raf;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import rs.raf.grpc.*;
import rs.raf.grpc.DiscussionGrpc.DiscussionImplBase;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DiscussionServer extends DiscussionImplBase {

    public static void main(String[] args) throws IOException, InterruptedException {
        final DiscussionServer discussionServer = new DiscussionServer();

        Server server = ServerBuilder.forPort(50051)
                .addService(discussionServer)
                .build()
                .start();

        System.out.println("Server started, listening on " + 50051);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            server.shutdown();
            System.err.println("*** server shut down");
        }));

        server.awaitTermination();
    }

    private CopyOnWriteArrayList<Topic> topics;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public DiscussionServer() {
        this.topics = new CopyOnWriteArrayList<>();
    }

    @Override
    public void sendNewTopic(NewTopicRequest request, StreamObserver<Response> responseObserver) {
        lock.writeLock().lock();
        try {
            Topic topic = new Topic.Builder()
                    .setTitle(request.getTitle())
                    .setComments(new ArrayList<>())
                    .build();
            this.topics.add(topic);

            Comment comment = new Comment.Builder()
                    .setMessage(request.getComment())
                    .setTimestamp(LocalDateTime.now())
                    .setParent(null)
                    .setReplies(new ArrayList<>())
                    .build();
            for (Topic t : topics) {
                if (t.getTitle().equals(request.getTitle())) {
                    t.getComments().add(comment);
                    break;
                }
            }

            Response response = Response.newBuilder()
                    .setResponse("Operation successful")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("An error occurred while adding a topic: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
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

        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(request.getTopic())) {
                    topic.getComments().add(comment);
                    break;
                }
            }
            Response response = Response.newBuilder()
                    .setResponse("Operation successful")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("An error occurred while adding a comment to a topic: " + e.getMessage());
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void replyToComment(CommentReplyRequest request, StreamObserver<Response> responseObserver) {
        String topicTitle = request.getTopic();
        int parentCommentId = request.getParentCommentId();
        Comment parentComment = null;

        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    for (Comment comment : topic.getComments()) {
                        if (comment.getId() == parentCommentId) {
                            parentComment = comment;
                            break;
                        }
                    }
                    if (parentComment != null) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while replying to a comment: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }

        if (parentComment != null) {
            Comment comment = new Comment.Builder()
                    .setMessage(request.getMessage())
                    .setTimestamp(LocalDateTime.now())
                    .setParent(parentComment)
                    .setReplies(new ArrayList<>())
                    .build();

            lock.writeLock().lock();
            try {
                parentComment.getReplies().add(comment);
                Response response = Response.newBuilder()
                        .setResponse("Operation successful")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (Exception e) {
                System.err.println("An error occurred while replying to a comment: " + e.getMessage());
            } finally {
                lock.writeLock().unlock();
            }
        } else {
            System.err.println("Parent comment not found");
        }
    }

    @Override
    public void updateMyComment(CommentUpdateRequest request, StreamObserver<Response> responseObserver) {
        String topicTitle = request.getTopic();
        String newComment = request.getMessage();
        int commentId = request.getCommentId();
        Duration t = Duration.ofSeconds(30);

        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    for (Comment comment : topic.getComments()) {
                        if (comment.getId() == commentId) {
                            if (Duration.between(comment.getTimestamp(), LocalDateTime.now()).compareTo(t) < 0) {
                                comment.setMessage(newComment);
                                break;
                            } else {
                                System.out.println("Cannot update the comment as it was created more than " + t.toSeconds() + " seconds ago.");
                            }
                        }
                    }
                    break;
                }
            }
            Response response = Response.newBuilder()
                    .setResponse("Operation successful")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("An error occurred while updating a comment: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void deleteMyComment(CommentDeleteRequest request, StreamObserver<Response> responseObserver) {
        String topicTitle = request.getTopic();
        int commentId = request.getCommentId();
        boolean commentFound = false;

        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    Iterator<Comment> iterator = topic.getComments().iterator();
                    while (iterator.hasNext()) {
                        Comment comment = iterator.next();
                        if (comment.getId() == commentId) {
                            commentFound = true;
                            if (comment.getReplies().isEmpty()) {
                                iterator.remove();
                                System.out.println("Comment deleted successfully.");
                            } else {
                                System.err.println("Cannot delete the comment as it has replies.");
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            if (!commentFound) {
                System.err.println("Comment not found.");
            }
            Response response = Response.newBuilder()
                    .setResponse("Operation successful")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("An error occurred while deleting a comment: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void getTopicsList(TopicsRequest request, StreamObserver<TopicsResponse> responseObserver) {
        lock.readLock().lock();
        try {
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
        String topicTitle = request.getTopic();

        lock.readLock().lock();
        try {
            TopicCommentsResponse.Builder responseBuilder = TopicCommentsResponse.newBuilder();
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    for (Comment comment : topic.getComments()) {
                        CommentResponse commentResponse = CommentResponse.newBuilder()
                                .setMessage(comment.getMessage())
                                .setCommentId(comment.getId())
                                .build();
                        responseBuilder.addComments(commentResponse);
                    }
                    break;
                }
            }
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } finally {
            lock.readLock().unlock();
        }
    }
}