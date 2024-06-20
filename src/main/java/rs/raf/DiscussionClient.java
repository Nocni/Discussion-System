package rs.raf;

import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.impl.cli.CliClientServiceImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import rs.raf.grpc.*;

import java.awt.*;
import java.awt.List;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class DiscussionClient {
    private final DiscussionGrpc.DiscussionBlockingStub blockingStub;
    private final DiscussionGrpc.DiscussionStub asyncStub;

    private ArrayList<Integer> commentIds = new ArrayList<>();

    public DiscussionClient(ManagedChannel channel) {
        this.blockingStub = DiscussionGrpc.newBlockingStub(channel);
        this.asyncStub = DiscussionGrpc.newStub(channel);
    }

    public static void main(String[] args) throws InterruptedException, TimeoutException, UnknownHostException {
        if (args.length != 2) {
            System.out.println("Useage : java rs.raf.DiscussionClient {groupId} {conf}");
            System.out
                    .println("Example: java rs.raf.DiscussionClient account 127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
            System.exit(1);
        }
        final String groupId = args[0];
        final String confStr = args[1];

        final Configuration conf = new Configuration();
        if (!conf.parse(confStr)) {
            throw new IllegalArgumentException("Fail to parse conf:" + confStr);
        }

        RouteTable.getInstance().updateConfiguration(groupId, conf);

        final CliClientServiceImpl cliClientService = new CliClientServiceImpl();
        cliClientService.init(new CliOptions());

        if (!RouteTable.getInstance().refreshLeader(cliClientService, groupId, 1000).isOk()) {
            throw new IllegalStateException("Refresh leader failed");
        }

        final PeerId leader = RouteTable.getInstance().selectLeader(groupId);

        InetAddress leaderAddr = InetAddress.getByName(leader.getEndpoint().getIp());
        ManagedChannel grpcChannel = ManagedChannelBuilder.forAddress("127.0.0.1", leader.getPort()-6)
                .usePlaintext()
                .build();

        DiscussionClient client = new DiscussionClient(grpcChannel);

        final int n = 10;
        final CountDownLatch latch = new CountDownLatch(n);
        final long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            System.out.println("Operation " + i);
            client.sendNewTopic("New Topic " + i, "This is the first comment in the new topic " + i);
            client.sendNewCommentToTopic("New Topic 0", "This is a new comment in the existing topic " + i);
            client.getTopicsList();
            client.getTopicComments("New Topic 0");
            client.replyToComment("New Topic 0", "This is a reply to a comment in the existing topic " + i, client.getRandomCommentId());
            client.updateMyComment("New Topic 0", "This is an updated comment in the existing topic " + i, client.getRandomCommentId());
            client.deleteMyComment("New Topic 0", client.getRandomCommentId());
            System.out.println("Operation " + i + " done.");
            Thread.sleep(200);
            System.out.println("Operation " + i + " done.");
        }
        System.out.println("Operations done.");
        client.getTopicsList();
        client.getTopicComments("New Topic 5");
        latch.await();
        System.out.println(n + " ops, cost : " + (System.currentTimeMillis() - start) + " ms.");
        System.exit(0);
    }

    public void sendNewTopic(String title, String comment) {
        System.out.println("Sending new topic");
        NewTopicRequest request = NewTopicRequest.newBuilder()
                .setTitle(title)
                .setComment(comment)
                .build();
        System.out.println("Request built" + request.toString());
        try {
            Response response = blockingStub.sendNewTopic(request);
            System.out.println(response.getResponse());
            System.out.println("New topic sent");
        } catch (StatusRuntimeException e) {
            System.out.println("gRPC call failed: " + e.getStatus());
        }
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
        asyncStub.replyToComment(request, new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                System.out.println(response.getResponse());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gRPC call failed: " + Status.fromThrowable(t));
            }

            @Override
            public void onCompleted() {
                System.out.println("Reply sent");
            }
        });
    }

    public void updateMyComment(String topic, String message, int commentId) {
        CommentUpdateRequest request = CommentUpdateRequest.newBuilder()
                .setTopic(topic)
                .setMessage(message)
                .setCommentId(commentId)
                .build();
        asyncStub.updateMyComment(request, new StreamObserver<Response>() {
            @Override
            public void onNext(Response response) {
                System.out.println(response.getResponse());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gRPC call failed: " + Status.fromThrowable(t));
            }

            @Override
            public void onCompleted() {
                System.out.println("Comment updated");
            }
        });
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

    public void getTopicComments(String topicTitle) {
        TopicCommentsRequest request = TopicCommentsRequest.newBuilder()
                .setTopic(topicTitle)
                .build();
        TopicCommentsResponse response = blockingStub.getTopicComments(request);
        System.out.println("Comments for topic " + topicTitle + ": ");
        for (CommentResponse comment : response.getCommentsList()) {
            System.out.println("Comment ID: " + comment.getCommentId() + ", Message: " + comment.getMessage());
        }
    }

    public int getRandomCommentId() {
        TopicCommentsRequest request = TopicCommentsRequest.newBuilder()
                .setTopic("New Topic 0")
                .build();
        TopicCommentsResponse response = blockingStub.getTopicComments(request);
        System.out.println("Comments");
        for (CommentResponse comment : response.getCommentsList()) {
            commentIds.add(comment.getCommentId());
            System.out.println("Comment ID: " + comment.getCommentId() + ", Message: " + comment.getMessage());
        }
        if (commentIds.isEmpty()) {
            throw new IllegalStateException("No comments available");
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(commentIds.size());

        return commentIds.get(randomIndex);
    }

}