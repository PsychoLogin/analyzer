package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entity class representing actions (keystroke)
 */

@NamedQueries(
        {
                @NamedQuery(name = Action.GET_TRAINING_DATA, query = "select a From Action a where a.sessionId IN (SELECT id from Session s where s.blogUserId = :blogUserId and id <> :currentSessionId) and a.actionTypeId = 38"),
                @NamedQuery(name = Action.GET_TEST_DATA, query = "select a From Action a where a.sessionId = :currentSessionId and a.actionTypeId = 38")
        }
)
@Entity
@Table(name = "actions")
public class Action {

    public static final String GET_TRAINING_DATA = "getTrainingData";
    public static final String GET_TEST_DATA = "getTestData";
    @Id
    private long id;

    @Column(name = "session_id", insertable = false, updatable = false)
    private long sessionId;

    @ManyToOne(optional = false)
    @JoinColumn(name="session_id")
    private Session session;

    @Column(name = "action_type_id", insertable = false, updatable = false)
    private long actionTypeId;

    @ManyToOne(optional = false)
    @JoinColumn(name="action_type_id")
    private ActionType actionType;

    @Column(name = "time_stamp")
    private Date timestamp;

    public long getId() {
        return id;
    }

    public long getSessionId() {
        return sessionId;
    }

    public Session getSession() {
        return session;
    }

    public long getActionTypeId() {
        return actionTypeId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
