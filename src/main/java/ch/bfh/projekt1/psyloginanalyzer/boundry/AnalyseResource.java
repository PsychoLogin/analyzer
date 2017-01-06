package ch.bfh.projekt1.psyloginanalyzer.boundry;

import ch.bfh.projekt1.psyloginanalyzer.analyzer.StaticDataAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.dl4j.Dl4jLoginAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.LoginParameter;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.AnalysisException;
import ch.bfh.projekt1.psyloginanalyzer.login.LoginDataProcessor;
import ch.bfh.projekt1.psyloginanalyzer.login.TrainingException;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private ExecutorService threadpool = Executors.newCachedThreadPool();
    private static final Logger logger = Logger.getLogger(AnalyseResource.class);

    @POST
    public Response analyseLogin(LoginParameter loginParameter) {
        threadpool.execute(() -> staticDataAnalyzer.analyseUser(loginParameter.getCurrentSessionId(), loginParameter.getBlogUserId()));
        threadpool.execute(() -> {
            Collection<TrainingEntry<Login>> trainSet = loginDataProcessor.createTrainSet(loginParameter.getCurrentSessionId(), loginParameter.getBlogUserId());
            Login testSet = loginDataProcessor.getTestSet(loginParameter.getCurrentSessionId());

            try {
                loginAnalyzer.train(trainSet);
            } catch (final TrainingException e) {
                logger.error("training-error", e);
            }
            try {
                boolean test = loginAnalyzer.analyze(testSet);
                logger.debug(test);
            } catch (final AnalysisException e) {
                logger.error("analysis-error", e);
            }
        });
        return Response.accepted().build();
    }

}
