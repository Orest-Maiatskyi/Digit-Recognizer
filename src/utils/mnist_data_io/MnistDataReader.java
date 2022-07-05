package utils.mnist_data_io;

import java.io.*;

/**
 * <h1>MNIST data reader class.</h1>
 * <p>
 * Class contains all necessary methods to read the MNIST dataset.
 *
 * @author  Orest Maiatskyi
 * @version 1.0
 * @since   04.07.2022
 */
public class MnistDataReader  {

    /** The show info trigger.
     * <p>
     * If set to true, print Mnist dataset info.
     * */
    private boolean showInfo = false;

    /** Default Constructor for the {@link MnistDataReader} class.
     * */
    public MnistDataReader() {}

    /** Second Constructor for the {@link MnistDataReader} class.
     *
     * @param showInfo {@link MnistDataReader#showInfo description}.
     * */
    public MnistDataReader(boolean showInfo) {
        this.showInfo = showInfo;
    }

    /** Returns {@link MnistMatrix[]}.
     * <p>
     * Read the MNIST dataset file and return {@link MnistMatrix[]}.
     *
     * @param dataFilePath path to the MNIST dataset file.
     * @param labelFilePath path to the MNIST label file.
     * @throws IOException if something wrong with files.
     * @return {@link MnistMatrix[]}.
     * */
    public MnistMatrix[] readData(String dataFilePath, String labelFilePath) throws IOException {

        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        int magicNumber = dataInputStream.readInt();
        int numberOfItems = dataInputStream.readInt();
        int nRows = dataInputStream.readInt();
        int nCols = dataInputStream.readInt();

        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
        int labelMagicNumber = labelInputStream.readInt();
        int numberOfLabels = labelInputStream.readInt();

        if (showInfo) {
            System.out.println("magic number is " + magicNumber);
            System.out.println("number of items is " + numberOfItems);
            System.out.println("number of rows is: " + nRows);
            System.out.println("number of cols is: " + nCols);
            System.out.println("labels magic number is: " + labelMagicNumber);
            System.out.println("number of labels is: " + numberOfLabels);
        }

        MnistMatrix[] data = new MnistMatrix[numberOfItems];

        assert numberOfItems == numberOfLabels;

        for(int i = 0; i < numberOfItems; i++) {
            MnistMatrix mnistMatrix = new MnistMatrix(nRows, nCols);
            mnistMatrix.setLabel(labelInputStream.readUnsignedByte());
            for (int r = 0; r < nRows; r++) {
                for (int c = 0; c < nCols; c++) {
                    mnistMatrix.setValue(r, c, dataInputStream.readUnsignedByte());
                }
            }
            data[i] = mnistMatrix;
        }
        dataInputStream.close();
        labelInputStream.close();
        return data;
    }
}
