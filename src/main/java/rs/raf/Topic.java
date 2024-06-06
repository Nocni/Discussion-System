package rs.raf;

import java.util.List;

public class Topic {

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
}
