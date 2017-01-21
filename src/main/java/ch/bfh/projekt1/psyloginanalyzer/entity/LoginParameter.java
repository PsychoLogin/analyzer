package ch.bfh.projekt1.psyloginanalyzer.entity;

/**
 * Entity class for NN analyze at logon
 */
public class LoginParameter {
    private long currentSessionId;
    private long blogUserId;

    public long getCurrentSessionId() {
        return currentSessionId;
    }

    public void setCurrentSessionId(long currentSessionId) {
        this.currentSessionId = currentSessionId;
    }

    public long getBlogUserId() {
        return blogUserId;
    }

    public void setBlogUserId(long blogUserId) {
        this.blogUserId = blogUserId;
    }
}
