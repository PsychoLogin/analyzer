package ch.bfh.projekt1.psyloginanalyzer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jan on 03.12.16.
 */

@Entity
@Table(name = "static_session_datas")
public class StaticSessionData {

    @Id
    @Column(name = "session_id")
    private String sessionId;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
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
