package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Jan on 07.01.2017.
 */
@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    private long id;

    @Column
    private String producer;

    @Column
    private String severity;

    @Column(name = "blog_user_id")
    @JoinColumn
    private long blogUserId;

    @Column(name = "time_stamp")
    private Date timeStamp;

    public static class Builder {
        private String producer;
        private String severity;
        private long blogUserId;

        public Alert build() {
            Alert alert = new Alert();
            alert.producer = this.producer;
            alert.severity = this.severity;
            alert.blogUserId = this.blogUserId;
            alert.timeStamp = new Date();
            return alert;
        }

        public Builder() {

        }

        public Builder withProducer(String producer) {
            this.producer = producer;
            return this;
        }

        public Builder withSeverity(String severity) {
            this.severity = severity;
            return this;
        }

        public Builder withBlogUserId(long blogUserId) {
            this.blogUserId = blogUserId;
            return this;
        }

    }

}
