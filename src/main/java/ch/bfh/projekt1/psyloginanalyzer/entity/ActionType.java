package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by othma on 02.01.2017.
 */
@Entity
@Table(name = "action_types")
public class ActionType {
    @Id
    @Column(name = "title")
    private String actionTypeTitle;
    @Column(name = "description")
    private String actionTypeDescription;
    @OneToMany(mappedBy="actionType",targetEntity=Action.class,
            fetch=FetchType.EAGER)
    private Collection actions;
}
