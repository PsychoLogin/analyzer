package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.AnalysisException;
import ch.bfh.projekt1.psyloginanalyzer.login.ITrainableAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.login.TrainingException;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by othma on 27.11.2016.
 */
public class Dl4jLoginAnalyzer implements ITrainableAnalyzer<Login> {
    private static final int NUM_CATEGORIES = 2;
    private MultiLayerNetwork network;

    private static int getNumberOfInputFeatures(Collection<TrainingEntry<Login>> trainingDataSet) {
        final List<Date> timestamps = trainingDataSet.iterator().next().getEntity().getKeystrokeTimestamps();
        if (timestamps.isEmpty()) throw new IllegalArgumentException();
        return timestamps.size() - 1;
    }

    private static DataSet toDataSet(Collection<TrainingEntry<Login>> trainingDataSet) {
        final int numTimestampsDifferences = getNumberOfInputFeatures(trainingDataSet);
        final IterableRecordReader recordReader = new IterableRecordReader(trainingDataSet);
        final DataSetIterator dataSetIterator = new RecordReaderDataSetIterator(recordReader, trainingDataSet.size(), numTimestampsDifferences, NUM_CATEGORIES);
        return dataSetIterator.next();
    }

    public void train(Collection<TrainingEntry<Login>> trainingDataSet) throws TrainingException {
        if (trainingDataSet.isEmpty()) return;
        final DataSet dataSet = toDataSet(trainingDataSet);
        final int iterations = 1000;
        final long seed = 6;

        final MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(iterations)
                .activation("tanh")
                .weightInit(WeightInit.XAVIER)
                .learningRate(0.01)
                .regularization(true).l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(getNumberOfInputFeatures(trainingDataSet))
                        .nOut(9)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(9)
                        .nOut(9)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation("softmax")
                        .nIn(9)
                        .nOut(NUM_CATEGORIES).build())
                .backprop(true).pretrain(false)
                .build();
        network = new MultiLayerNetwork(configuration);
        network.init();
        network.fit(dataSet);
    }

    public boolean analyze(final Login login) throws AnalysisException {
        final DataSet testDataSet = toDataSet(Collections.singleton(new TrainingEntry<>(login, false)));
        final INDArray output = network.output(testDataSet.getFeatureMatrix());
        return output.getDouble(1) > output.getDouble(0);
    }
}
