package utils.mnist_data_io;

/**
 * <h1>MNIST matrix class.</h1>
 * <p>
 * Class contains all info about MNIST image.
 *
 * @author  Orest Maiatskyi
 * @version 1.0
 * @since   04.07.2022
 */
public class MnistMatrix implements Comparable<MnistMatrix> {
    /** Two-dimensional array "matrix".
     * <p>
     * Contains the pixels of a grayscale image.
     * */
    private final int [][] data;

    /** Number of {@link MnistMatrix#data} rows.
     * */
    private final int nRows;

    /** Number of {@link MnistMatrix#data} columns.
     * */
    private final int nCols;

    /** The label stored by the {@link MnistMatrix#data}.
     * */
    private int label;

    /** Color difference between grayscale pixels
     * <p>
     * This property is used to sort an array of {@link MnistMatrix}.
     * */
    private double distance;

    /** Constructor for the {@link MnistMatrix} class.
     *
     * @param nRows {@link MnistMatrix#nRows description}.
     * @param nCols {@link MnistMatrix#nCols description}.
     * */
    public MnistMatrix(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.data = new int[nRows][nCols];
    }

    /**
     * @param r - number of row.
     * @param c - number of column.
     * @return grayscale value of pixel in {@link MnistMatrix#data}.
     * */
    public int getValue(int r, int c) {
        return data[r][c];
    }

    /**
     * @return {@link MnistMatrix#label}.
     * */
    public int getLabel() {
        return label;
    }

    /**
     * @return {@link MnistMatrix#nRows}.
     * */
    public int getNumberOfRows() {
        return nRows;
    }

    /**
     * @return {@link MnistMatrix#nCols}.
     * */
    public int getNumberOfColumns() {
        return nCols;
    }

    /**
     * @return {@link MnistMatrix#distance}.
     * */
    public double getDistance() {
        return distance;
    }

    /** Set the {@link MnistMatrix#data}[r][c] property.
     *
     * @param r number of row.
     * @param c number of column.
     * @param value value of grayscale pixel.
     * */
    public void setValue(int r, int c, int value) {
        data[r][c] = value;
    }

    /** Set the {@link MnistMatrix#label} property.
     *
     * @param label {@link MnistMatrix#label description}.
     * */
    public void setLabel(int label) {
        this.label = label;
    }

    /** Set the {@link MnistMatrix#distance} property.
     *
     * @param distance {@link MnistMatrix#distance description}.
     * */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(MnistMatrix o) {
        return Double.compare(this.getDistance(), o.getDistance());
    }
}
