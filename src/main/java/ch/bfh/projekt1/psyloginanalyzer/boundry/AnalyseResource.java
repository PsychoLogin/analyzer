package ch.bfh.projekt1.psyloginanalyzer.boundry;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.StaticDataAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.dl4j.Dl4jLoginAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.LoginParameter;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.AnalysisException;
import ch.bfh.projekt1.psyloginanalyzer.login.LoginDataProcessor;
import ch.bfh.projekt1.psyloginanalyzer.login.TrainingException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Jan on 17.12.2016.
 */
@Path("analyse")
public class AnalyseResource {

    @Inject
    StaticDataAnalyzer staticDataAnalyzer;

    Dl4jLoginAnalyzer loginAnalyzer = new Dl4jLoginAnalyzer();

    @Inject
    LoginDataProcessor loginDataProcessor;

    @POST
    public Response analyseLogin(LoginParameter loginParameter) {
        new Thread(() -> staticDataAnalyzer.analyseUser(loginParameter.getCurrentSessionId(), loginParameter.getBlogUserId())).start();
        new Thread(() -> {

            Collection<TrainingEntry<Login>> trainSet = loginDataProcessor.createTrainSet(loginParameter.getCurrentSessionId(), loginParameter.getBlogUserId());
            Login testSet = loginDataProcessor.getTestSet(loginParameter.getCurrentSessionId());


            try {
                loginAnalyzer.train(trainSet);
            } catch (TrainingException e) {
                e.printStackTrace();
            }
            try {
                loginAnalyzer.analyze(testSet);
            } catch (AnalysisException e) {
                e.printStackTrace();
            }
        }).start();
        return Response.accepted().build();
    }

}
