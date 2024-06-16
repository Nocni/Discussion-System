package rs.raf;

import java.io.Serializable;
import java.util.List;

public class Topic implements Serializable {

    private String title;
    private List<Comment> comments;

    public Topic(String title, List<Comment> comments) {
        this.title = title;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class Builder {
        private String title;
        private List<Comment> comments;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setComments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Topic build() {
            return new Topic(title, comments);
        }
    }
}
