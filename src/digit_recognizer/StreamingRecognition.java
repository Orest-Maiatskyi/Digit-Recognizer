package digit_recognizer;

import utils.mnist_data_io.MnistMatrix;

/**
 * <h1>Class implements multi-thread capability.</h1>
 * <p>
 * Class takes {@link Recognizer} instance and performs {@link MnistMatrix} recognition in a separate thread.
 *
 * @author  Orest Maiatskyi
 * @version 1.0
 * @since   05.07.2022
 */
public class StreamingRecognition extends Thread {

    /** The {@link Recognizer} instance.
     * */
    private final Recognizer recognizer;

    /** The {@link MnistMatrix} array.
     * <p>
     * Array contains test matrices.
     * */
    private final MnistMatrix[] testMatrices;

    /** Error counter.
     * <p>
     * Show how many matrices were recognized incorrectly.
     * */
    private int errorCounter;

    /** Remaining time property
     * <p>
     * Shows how much time in minutes is left before the end of recognition.
     * */
    private double remainingTime;

    /** Constructor fot the {@link StreamingRecognition} class
     *
     * @param recognizer {@link StreamingRecognition#recognizer description}
     * @param testMatrices {@link StreamingRecognition#testMatrices description}
     * */
    public StreamingRecognition(Recognizer recognizer, MnistMatrix[] testMatrices) {
        this.recognizer = recognizer;
        this.testMatrices = testMatrices;
    }

    /** Starts the recognition loop.
     * <p>
     * The number of loop iterations is proportional to the size of {@link StreamingRecognition#testMatrices} array.
     * */
    private void recognizeLoop() {
        errorCounter = 0;

        int counter = 0;
        for (MnistMatrix testMatrix : testMatrices) {
            long startTime = System.currentTimeMillis();
            if (recognizer.recognize(testMatrix) != testMatrix.getLabel()) errorCounter ++;
            long endTime = System.currentTimeMillis();

            remainingTime = ((endTime - startTime) / 60000f) * (testMatrices.length - counter);

            counter ++;
        }
    }

    @Override
    public void run() {
        recognizeLoop();
    }

    /**
     * @return {@link StreamingRecognition#errorCounter}
     * */
    public int getErrorCounter() {
        return errorCounter;
    }

    /**
     * @return {@link StreamingRecognition#errorCounter}
     * */
    public double getRemainingTime() {
        return remainingTime;
    }

}
