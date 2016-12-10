package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.IWritableConvertible;

/**
 * Created by othma on 27.11.2016.
 */
public interface ITrainableAnalyzer<Entity extends IWritableConvertible> extends IAnalyzer<Entity>, ITrainable<Entity> {
}
