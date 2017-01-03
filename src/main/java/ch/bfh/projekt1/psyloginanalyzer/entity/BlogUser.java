package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by othma on 02.01.2017.
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {
    @Id
    @Column(name = "username")
    private String userName;
    @OneToMany(mappedBy="bloguser",targetEntity=Session.class,
            fetch=FetchType.EAGER)
    private Collection sessions;


}
