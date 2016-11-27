package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;

import java.util.Collection;

/**
 * Created by othma on 27.11.2016.
 */
public interface ITrainable<Entity> {
    void train(Collection<TrainingEntry<Entity>> trainingDataSet) throws TrainingException;
}
