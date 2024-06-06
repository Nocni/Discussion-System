package rs.raf;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {

    private String user;
    private String message;
    private LocalDateTime timestamp;
    private Comment parent;
    private List<Comment> replies;

    public Comment(String user, String message, LocalDateTime timestamp, Comment parent, List<Comment> replies) {
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
        this.parent = parent;
        this.replies = replies;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}
