package ch.bfh.projekt1.psyloginanalyzer.login;

/**
 * NN analyzer interface
 * @param <Entity>
 */
public interface IAnalyzer<Entity> {
    boolean analyze(Entity entity) throws AnalysisException;
}
