package rs.raf;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operation implements Serializable {

    private OperationType operationType;
    private Topic topic;
    private Comment comment;
    private int commentId;
    private String topicTitle;

    public Operation(OperationType operationType, Topic topic, Comment comment) {
        this.operationType = operationType;
        this.topic = topic;
        this.comment = comment;
    }

    public Operation(OperationType operationType) {
        this.operationType = operationType;
    }


    public Operation(OperationType operationType, String topicTitle, Comment comment) {
        this.operationType = operationType;
        this.topicTitle = topicTitle;
        this.comment = comment;
    }

    public Operation(OperationType operationType, String topicTitle, Comment reply, int parentCommentId) {
        this.operationType = operationType;
        this.topicTitle = topicTitle;
        this.comment = reply;
        this.commentId = parentCommentId;
    }

    public Operation(OperationType operationType, String topicTitle, int commentId) {
        this.operationType = operationType;
        this.topicTitle = topicTitle;
        this.commentId = commentId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Topic getTopic() {
        return topic;
    }

    public Comment getComment() {
        return comment;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }
}