import digit_recognizer.MetricTypes;
import digit_recognizer.Recognizer;
import utils.mnist_data_io.MnistDataReader;
import utils.mnist_data_io.MnistMatrix;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        // Create Mnist data reader.
        MnistDataReader mnistDataReader = new MnistDataReader();

        // Load training and test images.
        MnistMatrix[] trainMatrices = mnistDataReader.readData("./MNIST/train-images.idx3-ubyte", "./MNIST/train-labels.idx1-ubyte");
        MnistMatrix[] testMatrices = mnistDataReader.readData("./MNIST/t10k-images.idx3-ubyte", "./MNIST/t10k-labels.idx1-ubyte");

        // Create recognizer instance.
        Recognizer recognizer = new Recognizer(20, MetricTypes.TAXICAB, trainMatrices);

        // Get one matrix for recognition.
        MnistMatrix firstMnistMatrix = testMatrices[0];

        // Log selected matrix and then log recognized matrix.
        System.out.println("Selected digit: " + firstMnistMatrix.getLabel());
        System.out.println("Recognized digit: " + recognizer.recognize(firstMnistMatrix));

        // To check error rate.
        recognizer.checkErrorRate(testMatrices, 4);
    }
}