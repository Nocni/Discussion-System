package rs.raf;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Comment implements Serializable {

    private static int counter = 0;

    private final int id;
    private String message;
    private LocalDateTime timestamp;
    private Comment parent;
    private List<Comment> replies;

    public Comment(Builder builder) {
        this.id = ++counter;
        this.message = builder.message;
        this.timestamp = builder.timestamp;
        this.parent = builder.parent;
        this.replies = builder.replies;
    }

    public int getId() {
        return id;
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

    public static class Builder {
        private String message;
        private LocalDateTime timestamp;
        private Comment parent;
        private List<Comment> replies;

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setParent(Comment parent) {
            this.parent = parent;
            return this;
        }

        public Builder setReplies(List<Comment> replies) {
            this.replies = replies;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
