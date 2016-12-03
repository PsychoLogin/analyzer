package ch.bfh.projekt1.psyloginanalyzer.entity;

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
    private String sessionId;
    private String operationSystem;
    private String language;
    private String browser;
    private String location;
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

        public StaticSessionData build() {
            StaticSessionData staticSessionData = new StaticSessionData();
            staticSessionData.setOperationSystem(this.operatingSystem);
            staticSessionData.setLanguage(this.language);
            staticSessionData.setBrowser(this.browser);
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
    }

}
