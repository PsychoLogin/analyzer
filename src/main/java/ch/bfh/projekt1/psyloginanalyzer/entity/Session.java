package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Entity class representing web blog session
 */
@Entity
@Table(name = "sessions")
public class Session {

    @Id
    private long id;


    @Column(name = "start")
    private Date session_start;
    @Column(name = "stop")
    private Date session_stop;

    @Column(name = "blog_user_id", insertable = false, updatable = false)
    private long blogUserId;

    @ManyToOne(optional = false)
    @JoinColumn(name="blog_user_id")
    private BlogUser blogUser;

    @OneToMany(mappedBy="session")
    private List<Action> actions;

}
