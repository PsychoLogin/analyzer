package ch.bfh.projekt1.psyloginanalyzer.entity;

import org.datavec.api.writable.BooleanWritable;
import org.datavec.api.writable.Writable;

import java.util.List;

/**
 * Entity class representing one NN training entry
 * Training entry consist of a entity (timestamp latencies) to train and a result of NN analyze process
 * @param <T>
 */
public class TrainingEntry<T extends IWritableConvertible> implements IWritableConvertible {
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

    @Override
    public List<Writable> toWritable() {
        final List<Writable> entity = getEntity().toWritable();
        entity.add(new BooleanWritable(result));
        return entity;
    }

    @Override
    public String toString() {
        return new StringBuilder("(").append(entity).append(',').append(result).append(')').toString();
    }
}
