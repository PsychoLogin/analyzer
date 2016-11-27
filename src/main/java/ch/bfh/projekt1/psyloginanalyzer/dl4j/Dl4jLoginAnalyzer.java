package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.ITrainableAnalyzer;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.IOException;

/**
 * Created by othma on 27.11.2016.
 */
public class Dl4jLoginAnalyzer implements ITrainableAnalyzer<Login> {
    public void train(String csvFilePath) {
        // TODO: Train
    }

    public boolean analyze(Login login) {
        // TODO: analyze
        return false;
    }

    private static DataSet readCSVDataset(String csvFileClasspath, int batchSize, int labelIndex, int numClasses) throws IOException, InterruptedException {

        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new ClassPathResource(csvFileClasspath).getFile()));
        DataSetIterator iterator = new RecordReaderDataSetIterator(rr, batchSize, labelIndex, numClasses);
        return iterator.next();
    }

    public void train(Iterable<TrainingEntry<Login>> trainingDataSet) {
        // TODO: Convert to CSV file
        final String csvFile = "";
        train(csvFile);
    }
}
