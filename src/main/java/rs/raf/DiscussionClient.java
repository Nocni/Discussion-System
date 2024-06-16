package rs.raf;

import com.alipay.sofa.jraft.RouteTable;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.CliOptions;
import com.alipay.sofa.jraft.rpc.impl.cli.CliClientServiceImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rs.raf.grpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class DiscussionClient {
    private final List<DiscussionGrpc.DiscussionBlockingStub> blockingStubs;
    private int nextServerIndex = 0;

    public DiscussionClient(List<ManagedChannel> channels) {
        this.blockingStubs = new ArrayList<>();
        for (ManagedChannel channel : channels) {
            this.blockingStubs.add(DiscussionGrpc.newBlockingStub(channel));
        }
    }

    private DiscussionGrpc.DiscussionBlockingStub getNextBlockingStub() {
        DiscussionGrpc.DiscussionBlockingStub stub = this.blockingStubs.get(this.nextServerIndex);
        this.nextServerIndex = (this.nextServerIndex + 1) % this.blockingStubs.size();
        return stub;
    }

    public static void main(String[] args) throws InterruptedException, TimeoutException {
        if (args.length != 3) {
            System.out.println("Useage : java rs.raf.DiscussionClient {groupId} {conf}");
            System.out
                    .println("Example: java rs.raf.DiscussionClient account 127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
            System.exit(1);
        }
        final String groupId = args[0];
        final String confStr = args[1];

        List<ManagedChannel> channels = new ArrayList<>();
        for (String address : args[2].split(",")) {
            String[] hostPort = address.split(":");
            channels.add(ManagedChannelBuilder.forAddress(hostPort[0], Integer.parseInt(hostPort[1]))
                    .usePlaintext()
                    .build());
        }

        DiscussionClient client = new DiscussionClient(channels);

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
        System.out.println("Leader is " + leader);
        final int n = 10;
        final CountDownLatch latch = new CountDownLatch(n);
        final long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            client.sendNewTopic("New Topic " + i, "This is the first comment in the new topic " + i);
            client.sendNewCommentToTopic("Existing Topic", "This is a new comment in the existing topic " + i);
            client.replyToComment("Existing Topic", "This is a reply to a comment in the existing topic " + i, i);
            client.updateMyComment("Existing Topic", "This is an updated comment in the existing topic " + i, i);
            client.deleteMyComment("Existing Topic", i);
            Thread.sleep(200);
        }
        latch.await();
        System.out.println(n + " ops, cost : " + (System.currentTimeMillis() - start) + " ms.");
        System.exit(0);
    }

    public void sendNewTopic(String title, String comment) {
        NewTopicRequest request = NewTopicRequest.newBuilder()
                .setTitle(title)
                .setComment(comment)
                .build();
        Response response = getNextBlockingStub().sendNewTopic(request);
        System.out.println(response.getResponse());
    }

    public void sendNewCommentToTopic(String topic, String message) {
        NewCommentRequest request = NewCommentRequest.newBuilder()
                .setTopic(topic)
                .setMessage(message)
                .build();
        Response response = getNextBlockingStub().sendNewCommentToTopic(request);
        System.out.println(response.getResponse());
    }

    public void replyToComment(String topic, String message, int parentCommentId) {
        CommentReplyRequest request = CommentReplyRequest.newBuilder()
                .setTopic(topic)
                .setMessage(message)
                .setParentCommentId(parentCommentId)
                .build();
        Response response = getNextBlockingStub().replyToComment(request);
        System.out.println(response.getResponse());
    }

    public void updateMyComment(String topic, String message, int commentId) {
        CommentUpdateRequest request = CommentUpdateRequest.newBuilder()
                .setTopic(topic)
                .setMessage(message)
                .setCommentId(commentId)
                .build();
        Response response = getNextBlockingStub().updateMyComment(request);
        System.out.println(response.getResponse());
    }

    public void deleteMyComment(String topic, int commentId) {
        CommentDeleteRequest request = CommentDeleteRequest.newBuilder()
                .setTopic(topic)
                .setCommentId(commentId)
                .build();
        Response response = getNextBlockingStub().deleteMyComment(request);
        System.out.println(response.getResponse());
    }

    public void getTopicsList() {
        TopicsRequest request = TopicsRequest.newBuilder().build();
        TopicsResponse response = getNextBlockingStub().getTopicsList(request);
        System.out.println("Topics: ");
        for (String topic : response.getTopicsList()) {
            System.out.println(topic);
        }
    }

    public void getTopicComments(String topicTitle) {
        TopicCommentsRequest request = TopicCommentsRequest.newBuilder()
                .setTopic(topicTitle)
                .build();
        TopicCommentsResponse response = getNextBlockingStub().getTopicComments(request);
        System.out.println("Comments for topic " + topicTitle + ": ");
        for (CommentResponse comment : response.getCommentsList()) {
            System.out.println("Comment ID: " + comment.getCommentId() + ", Message: " + comment.getMessage());
        }
    }

}