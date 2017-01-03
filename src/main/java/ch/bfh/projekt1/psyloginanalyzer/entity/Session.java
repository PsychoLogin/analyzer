package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by othma on 02.01.2017.
 */
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "start")
    private Date session_start;
    @Column(name = "stop")
    private Date session_stop;
    @ManyToOne(optional = false)
    @JoinColumn(name="blog_user_id",referencedColumnName="id")
    private BlogUser blogUser;
    @OneToMany(mappedBy="session",targetEntity=Action.class,
            fetch=FetchType.EAGER)
    private Collection actions;

}
