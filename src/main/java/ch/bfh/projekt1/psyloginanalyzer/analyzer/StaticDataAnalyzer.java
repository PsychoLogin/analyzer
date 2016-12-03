package ch.bfh.projekt1.psyloginanalyzer.analyzer;

import ch.bfh.projekt1.psyloginanalyzer.entity.StaticSessionData;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jan on 03.12.16.
 */
public class StaticDataAnalyzer {


    @PersistenceUnit
    @Inject
    EntityManager em;

    private List<StaticSessionData> getExistingData() {
        // TODO: get List from Database
        List<StaticSessionData> staticSessionDatas = new ArrayList<>();
        staticSessionDatas.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10").build());
        staticSessionDatas.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10").build());
        staticSessionDatas.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10").build());
        staticSessionDatas.add(new StaticSessionData.Builder()
                .withOperatingSystem("Win10").build());

        return staticSessionDatas;
    }

    public boolean analyze(StaticSessionData staticSessionData) {

        // Mobile oder PC => UserAnalyse PC
        UserAnalyser userAnalyser = new UserAnalyser();
        UserBehavior user = userAnalyser.getUser("stinrg", "sitnrg");


        List<StaticSessionData> existingData = getExistingData();

        int mismatchCounter = 0;

        for (StaticSessionData existing : existingData) {
            if (!staticSessionData.getOperationSystem().equals(existing.getOperationSystem())) {
                mismatchCounter++;
            }
        }

        double wrongRate = 100.0/existingData.size()*mismatchCounter;

        return !(wrongRate > 10);
    }


}
