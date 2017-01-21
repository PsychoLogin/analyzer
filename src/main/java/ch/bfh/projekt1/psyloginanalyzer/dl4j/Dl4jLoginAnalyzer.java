package ch.bfh.projekt1.psyloginanalyzer.dl4j;

import ch.bfh.projekt1.psyloginanalyzer.entity.Login;
import ch.bfh.projekt1.psyloginanalyzer.entity.TrainingEntry;
import ch.bfh.projekt1.psyloginanalyzer.login.AnalysisException;
import ch.bfh.projekt1.psyloginanalyzer.login.ITrainableAnalyzer;
import ch.bfh.projekt1.psyloginanalyzer.login.TrainingException;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.datasets.iterator.ExistingDataSetIterator;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.saver.InMemoryModelSaver;
import org.deeplearning4j.earlystopping.saver.LocalFileModelSaver;
import org.deeplearning4j.earlystopping.scorecalc.DataSetLossCalculator;
import org.deeplearning4j.earlystopping.scorecalc.ScoreCalculator;
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition;
import org.deeplearning4j.earlystopping.termination.MaxTimeIterationTerminationCondition;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import javax.ejb.Stateless;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Neuronal Network class for training and analyzing datasets
 */
public class Dl4jLoginAnalyzer implements ITrainableAnalyzer<Login> {
    private static final int NUM_CATEGORIES = 2;
    private MultiLayerNetwork network;
    private DataNormalization normalizer;

    /**
     * Get size of trainingset for training
     * @param trainingDataSet
     * @return
     */
    private int getNumberOfInputFeatures(final Collection<TrainingEntry<Login>> trainingDataSet) {
        return trainingDataSet.iterator().next().getEntity().getKeystrokeTimestamps().size();
    }

    /**
     * Convert collection of training entries of logins to a trainable dataset
     * @param trainingDataSet
     * @return
     */
    private DataSet toDataSet(final Collection<TrainingEntry<Login>> trainingDataSet) {
        final int numTimestampsDifferences = getNumberOfInputFeatures(trainingDataSet);
        final IterableRecordReader recordReader = new IterableRecordReader(trainingDataSet);
        return new RecordReaderDataSetIterator(recordReader, trainingDataSet.size(), numTimestampsDifferences, NUM_CATEGORIES).next();
    }

    /**
     * Train neural network with training dataset
     * @param trainingDataSet
     * @throws TrainingException
     */
    public void train(Collection<TrainingEntry<Login>> trainingDataSet) throws TrainingException {
        if (trainingDataSet.isEmpty()) return;
        try {
            final DataSet dataSet = toDataSet(trainingDataSet);
            normalizer = new NormalizerStandardize();
            normalizer.fit(dataSet);
            normalizer.transform(dataSet);
            final DataSetIterator dataSetIterator = new ExistingDataSetIterator(dataSet);
            final MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                    .seed(6)
                    .iterations(1)
                    .activation("tanh")
                    .weightInit(WeightInit.XAVIER)
                    .learningRate(0.1)
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
            final EarlyStoppingConfiguration esConf = new EarlyStoppingConfiguration.Builder()
                    .epochTerminationConditions(new MaxEpochsTerminationCondition(20))
                    .iterationTerminationConditions(new MaxTimeIterationTerminationCondition(20, TimeUnit.MINUTES))
                    .scoreCalculator(new DataSetLossCalculator(dataSetIterator, true))
                    .evaluateEveryNEpochs(1)
                    .modelSaver(new InMemoryModelSaver())
                    .build();
            final EarlyStoppingTrainer trainer = new EarlyStoppingTrainer(esConf, network, dataSetIterator);
            final EarlyStoppingResult<MultiLayerNetwork> result = trainer.fit();
            network = result.getBestModel();
        } catch (final Exception e) {
            throw new TrainingException(e);
        }
    }

    /**
     * Analyze single login for true or false
     * @param login
     * @return
     * @throws AnalysisException
     */
    public boolean analyze(final Login login) throws AnalysisException {
        try {
            final DataSet testDataSet = toDataSet(Collections.singleton(new TrainingEntry<>(login, false)));
            normalizer.transform(testDataSet);
            final INDArray output = network.output(testDataSet.getFeatureMatrix());
            return output.getDouble(1) > output.getDouble(0);
        } catch (final Exception e) {
            throw new AnalysisException(e);
        }
    }
}
