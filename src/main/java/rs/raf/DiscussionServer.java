package rs.raf;

import rs.raf.grpc.DiscussionGrpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DiscussionServer extends DiscussionGrpc.DiscussionImplBase {
    private CopyOnWriteArrayList<Topic> topics;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public DiscussionServer() {
        this.topics = new CopyOnWriteArrayList<>();
    }

    public void sendNewTopic(Topic topic) {
        lock.writeLock().lock();
        try {
            this.topics.add(topic);
        } catch (Exception e) {
            System.err.println("An error occurred while adding a topic: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void sendNewCommentToTopic(String topicTitle, Comment comment) {
        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    topic.getComments().add(comment);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding a comment to a topic: " + e.getMessage());
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public void replyToComment(String topicTitle, String parentCommentUser, Comment comment) {
        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    for (Comment parentComment : topic.getComments()) {
                        if (parentComment.getUser().equals(parentCommentUser)) {
                            parentComment.getReplies().add(comment);
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while replying to a comment: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void updateMyComment(String topicTitle, String user, String newComment) {
        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    for (Comment comment : topic.getComments()) {
                        if (comment.getUser().equals(user)) {
                            comment.setMessage(newComment);
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating a comment: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void deleteMyComment(String topicTitle, String user) {
        lock.writeLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    Iterator<Comment> iterator = topic.getComments().iterator();
                    while (iterator.hasNext()) {
                        Comment comment = iterator.next();
                        if (comment.getUser().equals(user) && comment.getReplies().isEmpty()) {
                            iterator.remove();
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while deleting a comment: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Topic> getTopicsList() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(topics);
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<Comment> getTopicComments(String topicTitle) {
        lock.readLock().lock();
        try {
            for (Topic topic : topics) {
                if (topic.getTitle().equals(topicTitle)) {
                    return new ArrayList<>(topic.getComments());
                }
            }
            return new ArrayList<>();
        } finally {
            lock.readLock().unlock();
        }
    }
}