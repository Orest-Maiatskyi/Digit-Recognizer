# Digit  Recognizer

Handwritten digit recognition using k-nearest neighbors.  
It's used for Mnist dataset recognition.

### MNIST database
------------------

[MNIST handwritten digit image database](http://yann.lecun.com/exdb/mnist/).
Images in this database have a resolution of 28x28 and are stored as a set of grayscale values. The entire database is divided into two parts: a training one, consisting of 50 000 images, and a test one, consisting of 10 000 images.  

### K-nearest neighbors algorithm
---------------------------------

For recognition, the [k-nearest neighbors algorithm](https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm) is used.  
This is a metric algorithm for automatic classification of objects. The basic principle of the nearest neighbor method is that an object is assigned to the class that is the most common among the element's neighbors. Neighbors are taken based on the set of objects whose classes are already known, and based on the key value k for this method, it is calculated which class is the most numerous among them.

### Metrics
-----------

There are two types of metric to choose from.
> Actually there are more, I just implemented only two of them.  

1. [Euclidean distance](https://en.wikipedia.org/wiki/Euclidean_distance). In mathematics, the Euclidean distance between two points in Euclidean space is the length of a line segment between the two points.
2. [Taxicab Metric](https://en.wikipedia.org/wiki/Taxicab_geometry) is a form of geometry in which the usual distance function or metric of Euclidean geometry is replaced by a new metric in which the distance between two points is the sum of the absolute differences of their Cartesian coordinates.

### Implementation of recognition algorithm from scratch
--------------------------------------------------------

> I use the term 'image' for simplicity, but keep in mind that images are stored as a matrix of grayscale values of 28x28 pixels each.  

1. Load training and test images.
2. Compute the Euclidean distance or Taxicab geometry between the test images point and all the training images.
3. Sort the calculated distances in ascending order.
4. Get the k nearest neighbors by taking top k images from sorted array.
5. Find the majority image in trimmed array.
6. Return predicted image.
7. find error rate to understand whether the algorithm is usable or not.  

> In case you are wondering, when recognizing 10 000 images, the error rate of the algorithm is 5.68% at Euclidean distance and 6.82% at Taxicab Metric.  

### Simple usage
---------

To read MNIST data:
```
// Create an instance of the MnistDataReader class,
// it's need to load grayscale values as MnistMatrix from MNIST dataset.
// Parameters:
//      boolean showInfo - if true log dataset info when read.
MnistDataReader mnistDataReader = new MnistDataReader();

// load training and test matrix.
MnistMatrix[] trainMatrices = mnistDataReader.readData("MNIST/train-images.idx3-ubyte", "MNIST/train-labels.idx1-ubyte");
MnistMatrix[] testMatrices = mnistDataReader.readData("MNIST/t10k-images.idx3-ubyte", "MNIST/t10k-labels.idx1-ubyte");

```

To single matrix recognition:
```
// Create an instance of Recognizer class,
// it's need to recognize matrix or get algorithm error rate.
// Parameters:
//      int coefficient k - this parameter affects the accuracy of recognition.
// `    MetricTypes metricType - Euclidean distance or Taxicab Metric.
//      MnistMatrix[] trainMatrices - array of training matrices,
//      the number of training matrices will affect the accuracy of recognition.
Recognizer recognizer = new Recognizer(20, MetricTypes.EUCLIDEAN, trainMatrices);

// Just take first matrix from testMatrices.
MnistMatrix firstMnistMatrix = testMatrices[0];

// Log test digit.
System.out.println("Selected digit: " + firstMnistMatrix.getLabel());
// Recognize and log test digit
System.out.println("Recognized digit: " + recognizer.recognize(firstMnistMatrix));
```

To check error rate:
```
// After matrix recognition, the error rate will be displayed in the console.
// Parameters:
//      MnistMatrix[] testMatrices - array of test matrices
//      int numberOfThreads - number of threads that method will create.
recognizer.checkErrorRate(testMatrices, 4);
```

### Docs
--------

For a more detailed explanation, please read the documentation.
