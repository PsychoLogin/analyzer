package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Entity class representing blog users
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {
    @Id
    private long id;

    @Column(name = "username")
    private String userName;

    @OneToMany(mappedBy = "blogUser")
    private List<Session> sessions;


}
