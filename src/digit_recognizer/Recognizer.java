package digit_recognizer;

import utils.mnist_data_io.MnistMatrix;

import java.util.*;

/**
 * <h1>Main recognizer class.</h1>
 * <p>
 * Class contains all necessary methods to recognize the MNIST images.
 *
 * @author  Orest Maiatskyi
 * @version 1.0
 * @since   04.07.2022
 */
public class Recognizer {
    /** The number of neighbors.
     * <p>
     * Also known as the coefficient k.
     *
     * @see <a href="https://www.ibm.com/topics/knn">What is the k-nearest neighbors algorithm?</a>
     */
    private  int k;
    /** The type of metric to use.
     * <p>
     * The selected metric will be responsible for the discrepancy in the color of the pixels in the image.
     * <br>
     * Metric types: {@link MetricTypes}.
     * */
    private MetricTypes metricType;
    /** Array of mnist images in grayscale value set format.
     * <p>
     * The MnistMatrix is used to conveniently store images in this format.
     * <br>
     * MnistMatrix: {@link MnistMatrix}.
     * */
    private MnistMatrix[] trainMatrices;

    /** Constructor fot the {@link Recognizer} class
     *
     * @param k {@link Recognizer#k description}.
     * @param metricType {@link Recognizer#metricType description}.
     * @param trainMatrix {@link Recognizer#trainMatrices description}.
     * */
    public Recognizer(int k, MetricTypes metricType, MnistMatrix[] trainMatrix) {
        this.k = k;
        this.metricType = metricType;
        this.trainMatrices = trainMatrix;
    }

    /** Returns the value of majority of the same {@link MnistMatrix}.
     *
     * @param suitableMatrix the simple array that contains first {@link Recognizer#k} {@link MnistMatrix}.
     * @return {@link MnistMatrix}.
     * */
    private int findMajority(MnistMatrix[] suitableMatrix) {
        HashMap<Integer, Integer> occurrencesNum = new HashMap<>();

        for (MnistMatrix mnistMatrix : suitableMatrix) {
            if (!occurrencesNum.containsKey(mnistMatrix.getLabel()))
                occurrencesNum.put(mnistMatrix.getLabel(), mnistMatrix.getLabel());
             else occurrencesNum.put(mnistMatrix.getLabel(), occurrencesNum.get(mnistMatrix.getLabel()) + 1);
        }

        return Collections.max(occurrencesNum.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /** Returns the recognized digit.
     *
     * @param matrixToBeRecognized a simple {@link MnistMatrix} that needs to be recognized.
     * @return recognized digit.
     * */
    private synchronized int predict(MnistMatrix matrixToBeRecognized) {

        TreeSet<MnistMatrix> mnistMatrixHashSet = new TreeSet<>();

        for (MnistMatrix trainMatrix : trainMatrices) {
            if (this.metricType == MetricTypes.EUCLIDEAN)
                trainMatrix.setDistance(Metrics.euclideanDistance(matrixToBeRecognized, trainMatrix));
            else if (this.metricType == MetricTypes.TAXICAB)
                trainMatrix.setDistance(Metrics.taxicabGeometry(matrixToBeRecognized, trainMatrix));

            mnistMatrixHashSet.add(trainMatrix);
        }

        MnistMatrix[] suitableMatrix = new MnistMatrix[this.k];
        int counter = 0;
        for (MnistMatrix mnistMatrix : mnistMatrixHashSet) {
            if (counter < k) suitableMatrix[counter] = mnistMatrix;
            else break;
            counter ++;
        }

        return findMajority(suitableMatrix);
    }

    /** Returns the same value as {@link Recognizer#predict(MnistMatrix)}.
     * <p>
     * A simple wrapper of {@link Recognizer#predict} method.
     *
     * @param matrixToBeRecognized a simple {@link MnistMatrix} that needs to be recognized.
     * @return recognized digit.
     * */
    public int recognize(MnistMatrix matrixToBeRecognized) {
        return predict(matrixToBeRecognized);
    }

    /** Print in console error rate.
     * <p>
     * This method used for detection error rate of recognition.
     * This method creates instances of {@link StreamingRecognition} class to be able to use multithreading,
     * after that, the recognition result will be displayed in the console
     *
     * @param testMatrices an array of {@link MnistMatrix}.
     * @param numberOfThreads that will be used for {@link MnistMatrix} recognition.
     * */
    public void checkErrorRate(MnistMatrix[] testMatrices, int numberOfThreads) {

        StreamingRecognition[] streamingRecognitions = new StreamingRecognition[numberOfThreads];
        int partLength = (testMatrices.length) / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            MnistMatrix[] tempTestMatrices;

            if (i == numberOfThreads-1)
                tempTestMatrices = Arrays.copyOfRange(testMatrices, i * partLength, testMatrices.length);
            else tempTestMatrices = Arrays.copyOfRange(testMatrices, i * partLength, (i + 1) * partLength);

            streamingRecognitions[i] = new StreamingRecognition(new Recognizer(k, metricType, trainMatrices), tempTestMatrices);
        }

        for (StreamingRecognition streamingRecognition : streamingRecognitions) streamingRecognition.start();

        ErrorRateLogger errorRateLogger = new ErrorRateLogger(streamingRecognitions, testMatrices.length);
        errorRateLogger.start();
    }


    /**
     * @return {@link Recognizer#k}
     * */
    public int getK() {
        return k;
    }

    /**
     * @return {@link Recognizer#trainMatrices}
     * */
    public MnistMatrix[] getTrainMatrices() {
        return trainMatrices;
    }

    /**
     * @return {@link Recognizer#metricType}
     * */
    public MetricTypes getMetricType() {
        return metricType;
    }

    /** set the {@link Recognizer#k} property.
     * <p>
     * The value of {@link Recognizer#k} <strong>can affect the recognition error rate</strong>.
     *
     * @param k {@link Recognizer#k description}.
     * */
    public void setK(int k) {
        this.k = k;
    }

    /** Set the {@link Recognizer#trainMatrices} property.
     * <p>
     * The value of {@link Recognizer#trainMatrices} <strong>can affect the recognition error rate</strong>.
     *
     * @param trainMatrices {@link Recognizer#trainMatrices description}.
     * */
    public void setTrainMatrices(MnistMatrix[] trainMatrices) {
        this.trainMatrices = trainMatrices;
    }

    /** Set the {@link Recognizer#metricType} property.
     * <p>
     * The type of {@link Recognizer#metricType} <strong>can't affect the recognition error rate</strong>.
     *
     * @param metricType {@link Recognizer#metricType description}.
     * */
    public void setMetricType(MetricTypes metricType) {
        this.metricType = metricType;
    }
}
