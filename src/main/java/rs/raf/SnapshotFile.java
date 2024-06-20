package rs.raf;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.*;

public class SnapshotFile {
    private final String path;

    public SnapshotFile(String path) {
        this.path = path;
    }

    public boolean save(final StateMachineSnapshot snapshot) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(snapshot);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save snapshot: " + e.getMessage());
            return false;
        }
    }

    public StateMachineSnapshot load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (StateMachineSnapshot) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load snapshot: " + e.getMessage());
            return null;
        }
    }

    public void saveSnapshotWithHessian(StateMachineSnapshot snapshot, OutputStream os) throws IOException {
        Hessian2Output out = new Hessian2Output(os);
        out.writeObject(snapshot);
        out.close();
    }

    public StateMachineSnapshot loadSnapshotWithHessian(InputStream is) throws IOException {
        Hessian2Input in = new Hessian2Input(is);
        StateMachineSnapshot snapshot = (StateMachineSnapshot) in.readObject();
        in.close();
        return snapshot;
    }

    public String getPath() {
        return path;
    }
}