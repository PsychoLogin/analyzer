package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.IWritableConvertible;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;

import java.util.Collection;

/**
 * NN trainable interface
 * @param <Entity>
 */
public interface ITrainable<Entity extends IWritableConvertible> {
    void train(Collection<TrainingEntry<Entity>> trainingDataSet) throws TrainingException;
}
