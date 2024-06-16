package rs.raf;

import com.alipay.sofa.jraft.Node;
import com.alipay.sofa.jraft.RaftGroupService;
import com.alipay.sofa.jraft.conf.Configuration;
import com.alipay.sofa.jraft.entity.PeerId;
import com.alipay.sofa.jraft.option.NodeOptions;
import com.alipay.sofa.jraft.rpc.RaftRpcServerFactory;
import com.alipay.sofa.jraft.rpc.RpcServer;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DiscussionServer {

    private RaftGroupService raftGroupService;
    private Node node;
    private StateMachine stateMachine;
    private Server grpcServer;
    private DiscussionService discussionService;

    public DiscussionServer(final String dataPath, final String groupId, final PeerId serverId,
                            final NodeOptions nodeOptions) throws IOException {

        File f = new File(dataPath);
        System.out.println("File Path ="+f.getAbsolutePath());

        FileUtils.forceMkdir(f);

        final RpcServer rpcServer = RaftRpcServerFactory.createRaftRpcServer(serverId.getEndpoint());

        this.stateMachine = new StateMachine();

        nodeOptions.setFsm(this.stateMachine);

        nodeOptions.setLogUri(dataPath + File.separator + "log");

        nodeOptions.setRaftMetaUri(dataPath + File.separator + "raft_meta");

        nodeOptions.setSnapshotUri(dataPath + File.separator + "snapshot");

        this.raftGroupService = new RaftGroupService(groupId, serverId, nodeOptions, rpcServer);

        this.node = this.raftGroupService.start();
    }

    public RaftGroupService getRaftGroupService() {
        return raftGroupService;
    }

    public Node getNode() {
        return node;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public void startGrpcServer(int port) throws IOException {
        this.grpcServer = ServerBuilder.forPort(port)
                .addService(new DiscussionService(this))
                .build()
                .start();
    }

    public void stopGrpcServer() throws InterruptedException {
        if (this.grpcServer != null) {
            this.grpcServer.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 5) {
            System.out
                    .println("Useage : java rs.raf.DiscussionServer {dataPath} {groupId} {serverId} {initConf}");
            System.out
                    .println("Example: java rs.raf.DiscussionServer /tmp/server1 account 127.0.0.1:8081 127.0.0.1:8081,127.0.0.1:8082,127.0.0.1:8083");
            System.exit(1);
        }
        final DiscussionServer discussionServer = getDiscussionServer(args);
        System.out.println("Started Discussion server at port:"
                + discussionServer.getNode().getNodeId().getPeerId().getPort());

        discussionServer.startGrpcServer(Integer.parseInt(args[4]));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Kontrolisan shutdown!");
            discussionServer.getNode().shutdown();
            try {
                discussionServer.stopGrpcServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    private static @NotNull DiscussionServer getDiscussionServer(String[] args) throws IOException {
        final String dataPath = args[0];
        final String groupId = args[1];
        final String serverIdStr = args[2];
        final String initConfStr = args[3];

        final NodeOptions nodeOptions = new NodeOptions();
        // snapshot
        // Timeout za izbor lidera
        nodeOptions.setElectionTimeoutMs(1000);
        // CLI
        nodeOptions.setDisableCli(false);
        // snapshot
        nodeOptions.setSnapshotIntervalSecs(30);
        //
        final PeerId serverId = new PeerId();
        if (!serverId.parse(serverIdStr)) {
            throw new IllegalArgumentException("Fail to parse serverId:" + serverIdStr);
        }
        final Configuration initConf = new Configuration();
        if (!initConf.parse(initConfStr)) {
            throw new IllegalArgumentException("Fail to parse initConf:" + initConfStr);
        }
        //
        nodeOptions.setInitialConf(initConf);

        // 启动
        return new DiscussionServer(dataPath, groupId, serverId, nodeOptions);
    }
}