package rs.raf;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Comment implements Serializable {

    private static int counter = 0;

    private final int id;
    private String message;
    private String timestamp;
    private Comment parent;
    private List<Comment> replies;

    public Comment(Builder builder) {
        this.id = counter++;
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
        return LocalDateTime.parse(this.timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
        private String timestamp;
        private Comment parent;
        private List<Comment> replies;

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTimestamp(String timestamp) {
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
