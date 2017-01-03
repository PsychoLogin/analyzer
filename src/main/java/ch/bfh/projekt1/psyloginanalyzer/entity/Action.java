package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by othma on 02.01.2017.
 */
@Entity
@Table(name = "actions")
public class Action {
    @Id
    @Column(name = "time_stamp")
    private Date actionTimeStamp;
    @ManyToOne(optional = false)
    @JoinColumn(name="session_id",referencedColumnName="id")
    private Session session;

    @ManyToOne(optional = false)
    @JoinColumn(name="action_type_id",referencedColumnName="id")
    private ActionType actionType;

}
