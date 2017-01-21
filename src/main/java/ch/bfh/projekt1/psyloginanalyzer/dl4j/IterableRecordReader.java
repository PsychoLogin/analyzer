package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.entity.IWritableConvertible;
import org.datavec.api.conf.Configuration;
import org.datavec.api.records.Record;
import org.datavec.api.records.metadata.RecordMetaData;
import org.datavec.api.records.reader.BaseRecordReader;
import org.datavec.api.split.InputSplit;
import org.datavec.api.writable.Writable;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Record Reader class for NN datasets
 *
 */
public class IterableRecordReader extends BaseRecordReader {
    private Iterator<? extends IWritableConvertible> iterator;

    /**
     * Constructor takes iterable for NN datasets
     *
     * @param entities
     */
    public IterableRecordReader(final Iterable<? extends IWritableConvertible> entities) {
        iterator = entities.iterator();
    }

    @Override
    public void initialize(InputSplit inputSplit)  {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initialize(Configuration configuration, InputSplit inputSplit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Writable> next() {
        return iterator.next().toWritable();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public List<String> getLabels() {
        return null;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Writable> record(URI uri, DataInputStream dataInputStream) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Record nextRecord() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Record loadFromMetaData(RecordMetaData recordMetaData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Record> loadFromMetaData(List<RecordMetaData> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
    }

    @Override
    public void setConf(final Configuration configuration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Configuration getConf() {
        throw new UnsupportedOperationException();
    }
}
