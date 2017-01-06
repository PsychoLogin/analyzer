package ch.bfh.projekt1.psyloginanalyzer.login;

/**
 * Created by othma on 27.11.2016.
 */
public interface IAnalyzer<Entity> {
    boolean analyze(Entity entity) throws AnalysisException;
}
