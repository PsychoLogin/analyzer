package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.IWritableConvertible;

/**
 * NN trainable and analyze interface
 * @param <Entity>
 */
public interface ITrainableAnalyzer<Entity extends IWritableConvertible> extends IAnalyzer<Entity>, ITrainable<Entity> {
}
