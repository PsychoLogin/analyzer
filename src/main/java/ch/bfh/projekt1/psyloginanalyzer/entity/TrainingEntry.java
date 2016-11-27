package ch.bfh.projekt1.psyloginanalyzer.entity;

/**
 * Created by othma on 27.11.2016.
 */
public class TrainingEntry<T> {
    private final T entity;
    private final boolean result;

    public TrainingEntry(T entity, boolean result) {
        this.entity = entity;
        this.result = result;
    }

    public T getEntity() {
        return entity;
    }

    public boolean isResult() {
        return result;
    }
}
