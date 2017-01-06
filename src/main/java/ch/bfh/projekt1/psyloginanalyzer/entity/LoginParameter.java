package ch.bfh.projekt1.psyloginanalyzer.entity;

/**
 * Created by Jan on 06.01.2017.
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
