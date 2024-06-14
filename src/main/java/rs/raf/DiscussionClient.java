package rs.raf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rs.raf.grpc.*;

public class DiscussionClient {
    private final DiscussionGrpc.DiscussionBlockingStub blockingStub;

    public DiscussionClient(ManagedChannel channel) {
        blockingStub = DiscussionGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        DiscussionClient client = new DiscussionClient(channel);

        // Call the methods on the client as needed
        client.sendNewTopic("New Topic", "This is the first comment in the new topic.");

        // Add calls to other methods as needed...
    }

    public void sendNewTopic(String title, String comment) {
        NewTopicRequest request = NewTopicRequest.newBuilder()
                .setTitle(title)
                .setComment(comment)
                .build();
        Response response = blockingStub.sendNewTopic(request);
        System.out.println(response.getResponse());
    }

    public void sendNewCommentToTopic(String topic, String message) {
        NewCommentRequest request = NewCommentRequest.newBuilder()
                .setTopic(topic)
                .setMessage(message)
                .build();
        Response response = blockingStub.sendNewCommentToTopic(request);
        System.out.println(response.getResponse());
    }

    public void replyToComment(String topic, String message, int parentCommentId) {
        CommentReplyRequest request = CommentReplyRequest.newBuilder()
                .setTopic(topic)
                .setMessage(message)
                .setParentCommentId(parentCommentId)
                .build();
        Response response = blockingStub.replyToComment(request);
        System.out.println(response.getResponse());
    }

    public void updateMyComment(String topic, String message, int commentId) {
        CommentUpdateRequest request = CommentUpdateRequest.newBuilder()
                .setTopic(topic)
                .setMessage(message)
                .setCommentId(commentId)
                .build();
        Response response = blockingStub.updateMyComment(request);
        System.out.println(response.getResponse());
    }

    public void deleteMyComment(String topic, int commentId) {
        CommentDeleteRequest request = CommentDeleteRequest.newBuilder()
                .setTopic(topic)
                .setCommentId(commentId)
                .build();
        Response response = blockingStub.deleteMyComment(request);
        System.out.println(response.getResponse());
    }

    public void getTopicsList() {
        TopicsRequest request = TopicsRequest.newBuilder().build();
        TopicsResponse response = blockingStub.getTopicsList(request);
        System.out.println("Topics: ");
        for (String topic : response.getTopicsList()) {
            System.out.println(topic);
        }
    }
}