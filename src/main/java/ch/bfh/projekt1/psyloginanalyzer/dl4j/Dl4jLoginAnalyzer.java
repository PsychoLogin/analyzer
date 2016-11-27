package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.csv.CsvConverter;
import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.AnalysisException;
import ch.bfh.projekt1.psyloginanalyzer.login.ITrainableAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.login.TrainingException;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by othma on 27.11.2016.
 */
public class Dl4jLoginAnalyzer implements ITrainableAnalyzer<Login> {
    private static final int NUM_CATEGORIES = 2;
    private MultiLayerNetwork network;

    private void train(final String csvFilePath, final int size, final int numTimestamps) throws TrainingException {
        final DataSet dataSet;
        try {
            dataSet = getCsvDataSet(csvFilePath, size, numTimestamps);
        } catch (final IOException | InterruptedException e) {
            throw new TrainingException(e);
        }

        final int iterations = 1000;
        final long seed = 6;

        final MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(iterations)
                .activation("tanh")
                .weightInit(WeightInit.XAVIER)
                .learningRate(0.1)
                .regularization(true).l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numTimestamps - 1).nOut(9)
                        .build())
                .layer(1, new DenseLayer.Builder().nIn(9).nOut(9)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation("softmax")
                        .nIn(9).nOut(NUM_CATEGORIES).build())
                .backprop(true).pretrain(false)
                .build();
        network = new MultiLayerNetwork(configuration);
        network.init();
        network.fit(dataSet);
    }

    private static DataSet getCsvDataSet(final String csvFilePath, final int size, final int numTimestamps) throws IOException, InterruptedException {
        final RecordReader recordReader = new CSVRecordReader();
        recordReader.initialize(new FileSplit(new File(csvFilePath)));
        final int numTimestampsDifferences = numTimestamps - 1;
        final DataSetIterator dataSetIterator = new RecordReaderDataSetIterator(recordReader, size, numTimestampsDifferences, NUM_CATEGORIES);
        return dataSetIterator.next();
    }

    public boolean analyze(final Login login) throws AnalysisException {
        final CsvConverter converter = new CsvConverter();
        converter.output(new TrainingEntry<>(login, false));
        final File file;
        try {
            file = converter.writeToTempFile();
        } catch (final IOException e) {
            throw new AnalysisException(e);
        }
        final DataSet testDataSet;
        try {
            testDataSet = getCsvDataSet(file.getAbsolutePath(), 1, login.getKeystrokeTimestamps().size());
        } catch (final IOException | InterruptedException e) {
            throw new AnalysisException(e);
        }
        final INDArray output = network.output(testDataSet.getFeatureMatrix());
        return output.getDouble(0 ) > output.getDouble(1);
    }

    public void train(Collection<TrainingEntry<Login>> trainingDataSet) throws TrainingException {
        if (trainingDataSet.isEmpty()) return;
        final List<Date> timestamps = trainingDataSet.iterator().next().getEntity().getKeystrokeTimestamps();
        if (timestamps.isEmpty()) throw new IllegalArgumentException();
        final int numTimestamps = timestamps.size();
        final CsvConverter converter = new CsvConverter();
        converter.outputLoginTrainingData(trainingDataSet);
        final File file;
        try {
            file = converter.writeToTempFile();
        } catch (final IOException e) {
            throw new TrainingException(e);
        }
        try {
            train(file.getAbsolutePath(), trainingDataSet.size(), numTimestamps);
        } finally {
            file.delete();
        }
    }
}
