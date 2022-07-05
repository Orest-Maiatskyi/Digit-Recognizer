package digit_recognizer;

/**
 * <h1>Class that print error rate of recognition algorithm.</h1>
 * <p>
 * Prints information to the console about the state of {@link StreamingRecognition} instances,
 * at the end of {@link ErrorRateLogger#run()} method displays the total amount of the error.
 *
 * @author  Orest Maiatskyi
 * @version 1.0
 * @since   04.07.2022
 */
public class ErrorRateLogger extends Thread {

    /** {@link StreamingRecognition} array.
     * */
    private final StreamingRecognition[] streamingRecognitions;

    /** The number of test matrices.
     * */
    private final int testMatricesNum;

    /** Constructor of the {@link ErrorRateLogger} class.
     *
     * @param streamingRecognitions {@link ErrorRateLogger#streamingRecognitions description}
     * @param testMatricesNum {@link ErrorRateLogger#testMatricesNum description}
     * */
    public ErrorRateLogger(StreamingRecognition[] streamingRecognitions, int testMatricesNum) {
        this.streamingRecognitions = streamingRecognitions;
        this.testMatricesNum = testMatricesNum;
    }

    /** Starts main thread loop
     * <p>
     * Method starts a loop and outputs information about recognition streams.
     * */
    @Override
    public void run() {

        int runningCounter = streamingRecognitions.length;

        while (runningCounter != 0) {
            runningCounter = streamingRecognitions.length;

            for (StreamingRecognition streamingRecognition : streamingRecognitions) {
                if (!streamingRecognition.isAlive()) runningCounter --;
            }

            String infoString = "";
            for (int i = 0; i < streamingRecognitions.length; i++) {
                infoString += "Thread-" + i + " errors: " + streamingRecognitions[i].getErrorCounter()
                        + " Remaining time: " + String.format("%.2f", streamingRecognitions[i].getRemainingTime()) + "min ";
            }

            System.out.print(infoString + "\r");

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        int finalErrorCounter = 0;
        for (StreamingRecognition streamingRecognition : streamingRecognitions)
            finalErrorCounter += streamingRecognition.getErrorCounter();

        System.out.println("Error rate is: " + ( 100f * finalErrorCounter / testMatricesNum) + "%");
    }

}
