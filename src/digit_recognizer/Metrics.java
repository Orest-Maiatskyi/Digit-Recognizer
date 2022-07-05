package digit_recognizer;

import utils.mnist_data_io.MnistMatrix;

/**
 * <h1>Class that contains two types of metrics.</h1>
 * <p>
 * Class contains metrics methods that uses to calculate the sum of greyscale differences between pixels.
 *
 * @author  Orest Maiatskyi
 * @version 1.0
 * @since   04.07.2022
 */
public class Metrics {

    /** Returns the sum of differences between pixels.
     *
     * @param matrixToBeRecognized {@link MnistMatrix}.
     * @param trainMatrix {@link MnistMatrix}.
     * @return Sum of differences between pixels.
     * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">Euclidean distance</a>
     * */
    public static double euclideanDistance(MnistMatrix matrixToBeRecognized, MnistMatrix trainMatrix) {
        int sum = 0;

        for (int r = 0; r < trainMatrix.getNumberOfRows(); r++) {
            for (int c = 0; c < trainMatrix.getNumberOfColumns(); c++) {
                sum += Math.pow(matrixToBeRecognized.getValue(r, c) - trainMatrix.getValue(r, c), 2);
            }
        }

        return Math.sqrt(sum);
    }

    /** Returns the sum of differences between pixels.
     *
     * @param matrixToBeRecognized {@link MnistMatrix}.
     * @param trainMatrix {@link MnistMatrix}.
     * @return Sum of differences between pixels.
     * @see <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Taxicab geometry</a>
     * */
    public static double taxicabGeometry(MnistMatrix matrixToBeRecognized, MnistMatrix trainMatrix) {
        int sum = 0;

        for (int r = 0; r < trainMatrix.getNumberOfRows(); r++) {
            for (int c = 0; c < trainMatrix.getNumberOfColumns(); c++) {
                sum += Math.abs(matrixToBeRecognized.getValue(r, c) - trainMatrix.getValue(r, c));
            }
        }

        return sum;
    }
}
