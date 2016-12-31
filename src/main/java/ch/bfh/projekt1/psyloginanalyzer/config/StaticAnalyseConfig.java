package ch.bfh.projekt1.psyloginanalyzer.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Jan on 30.12.2016.
 */

@NamedQuery(name= StaticAnalyseConfig.GET_CONFIG, query="SELECT s from StaticAnalyseConfig s")
@Entity
@Table(name = "static_analyse_config")
public class StaticAnalyseConfig implements Serializable {

    public static final String GET_CONFIG = "getConfig";

    @Id
    private int id;

    @Column(name = "penalty_error_level")
    private int penaltyErrorLevel;

    @Column(name = "penalty_warning_level")
    private int penaltyWarningLevel;

    @Column(name = "minimum_number_logins")
    private int minimumNumberLogins;

    public int getPenaltyErrorLevel() {
        return penaltyErrorLevel;
    }

    public void setPenaltyErrorLevel(int penaltyErrorLevel) {
        this.penaltyErrorLevel = penaltyErrorLevel;
    }

    public int getPenaltyWarningLevel() {
        return penaltyWarningLevel;
    }

    public void setPenaltyWarningLevel(int penaltyWarningLevel) {
        this.penaltyWarningLevel = penaltyWarningLevel;
    }

    public int getMinimumNumberLogins() {
        return minimumNumberLogins;
    }

    public void setMinimumNumberLogins(int minimumNumberLogins) {
        this.minimumNumberLogins = minimumNumberLogins;
    }
}
