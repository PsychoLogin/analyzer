package ch.bfh.projekt1.psyloginanalyzer.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity class representing static session data
 */

@NamedQueries(
        {
                @NamedQuery(name = StaticSessionData.GET_CURRENT_SESSION, query = "Select s from StaticSessionData s where s.sessionId = :currentSessionId"),
                @NamedQuery(name = StaticSessionData.GET_OLD_SESSIONS, query = "Select s from StaticSessionData s where s.session.blogUser.id = :blogUserId AND s.sessionId <> :currentSessionId")
        }
)

@Entity
@Table(name = "static_session_datas")
public class StaticSessionData {

    public static final String GET_CURRENT_SESSION = "getCurrentSession";
    public static final String GET_OLD_SESSIONS = "getOldSessions";
    @Id
    @Column(name = "session_id")
    private long sessionId;
    @Column(name = "os")
    private String operationSystem;
    @Column(name = "lang")
    private String language;
    @Column(name = "browser")
    private String browser;
    @Column(name = "location")
    private String location;
    @Column(name = "referrer")
    private String referrer;

    @OneToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "session_id")
    private Session session;

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public static class Builder {

        private String operatingSystem;
        private String language;
        private String browser;
        private String referrer;
        private String location;

        public StaticSessionData build() {
            StaticSessionData staticSessionData = new StaticSessionData();
            staticSessionData.setOperationSystem(this.operatingSystem);
            staticSessionData.setLanguage(this.language);
            staticSessionData.setBrowser(this.browser);
            staticSessionData.setReferrer(this.referrer);
            staticSessionData.setLocation(this.location);
            return staticSessionData;
        }

        public Builder() {

        }

        public Builder withOperatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder withBrowser(String browser) {
            this.browser = browser;
            return this;
        }

        public Builder withReferrer(String referrer) {
            this.referrer = referrer;
            return this;
        }

        public Builder withLocation(String location) {
            this.location = location;
            return this;
        }
    }

}
