package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by othma on 02.01.2017.
 */
@Entity
@Table(name = "action_types")
public class ActionType {

    @Id
    private long id;

    @Column(name = "title")
    private String actionTypeTitle;

    @Column(name = "description")
    private String actionTypeDescription;

    @OneToMany(mappedBy="actionType",
            fetch=FetchType.EAGER)
    private List<Action> actions;
}
