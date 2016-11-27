package ch.bfh.projekt1.psyloginanalyzer.login;

import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;

/**
 * Created by othma on 27.11.2016.
 */
public interface ITrainable<Entity> {
    void train(Iterable<TrainingEntry<Entity>> trainingDataSet);
}
