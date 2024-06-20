package rs.raf;

import com.alipay.sofa.jraft.storage.snapshot.Snapshot;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.google.protobuf.Message;

import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StateMachineSnapshot extends Snapshot implements Serializable {
    private CopyOnWriteArrayList<Topic> topics;
    private ConcurrentHashMap<Integer, Comment> comments;
    private String path;

    public StateMachineSnapshot(CopyOnWriteArrayList<Topic> topics, ConcurrentHashMap<Integer, Comment> comments, String path) {
        this.topics = topics;
        this.comments = comments;
        this.path = new File(path).getAbsolutePath();
    }

    public CopyOnWriteArrayList<Topic> getTopics() {
        return topics;
    }

    public ConcurrentHashMap<Integer, Comment> getComments() {
        return comments;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Set<String> listFiles() {
        return Set.of("data");
    }

    @Override
    public Message getFileMeta(String s) {
        return null;
    }
}