## Jpeg Encoder And Decoder

[TOC]

#### UI : JavaFX

We are using JavaFX 11 (OpenFX) as our GUI framework. JavaFX is a light weight , new generation Java GUI framework in order to replace Swing. We use FXML files to define the layouts, and a ```FXMLController.java``` to ```control``` 

- **global layout and button specifications**
- TODO : add a picture and button descriptions here

#### MVC Hierarchy

This project is using **MVC** design pattern. 

##### View Layer

The ```view``` layer is defined in FXML files, in our case, the file ```~/resources/fxml/Scene.fxml```, part of the fxml is written using a (TODO : the GUI interface Jason used here ), every element is instantiated either through a child label or an attribute. And the element have ```id``` values which can be accessed in the ``` controller``` module. For event systems, JavaFX use ```onAction``` attribute to notify corresponding method in the ```controller```. The view layer is decoupled from the ```controll layer```

##### Controller Layer

The ```FXMLController.java``` can have access to the FXML file elements, using ```@FXML``` annotation. We have ```handleButtonAction()``` callback methods listening on a FXML elment, like operations **import, compress, save, quality select, luminations select**. And ```controller``` are also responsible for create, manage, modify , I/O of```model``` instances.

##### Model Layer

```model``` is the core components of this application. We defined a ```class JpegModelObservable``` as our main model, and as its name suggests, it can be listened by a ```controller```, so the ```model``` is decoupled from ```view``` in our case. The ```JpegModelObservable``` maintains some data structures used for transformations of a image.  This model has member methods doing the actual work of tranformations including *color model conversion, 4:2:0 subsampling, DCT, quantization*,  each intermediate operation has a specific data structure storing information, which will be discussed in next chapter. Theoretically, each transformation would call the ```notify()``` method to the listeners, but this will cost too much computing resources, thus make the application extremely slow, so we only notify when everything is done ( except for the  matrix after DCT used for displaying our algorithm correctness) .  The design is quite simple.

#### Data Structures

Data structure are the key part understanding our work. Instead of using third-party libraries, we defined our own data structures, and used the most simple (but slow) implementation of matrix multiplication. Considering that we are not so scientific , we don't need a high speed processing images. The loss of speed is completely acceptable.

##### YuvColor 

This class has 3 integer array, storing Y, U ,V pixels respectively. Notice they are all one dimension arrays, and can be created using 1 pixel value array. This class has ```subsampling()``` and do the 4:2:0 YCrCb subsampling. It also support paddling if the width or height is not multiples of 8.

##### Matrix8f

A 8 * 8 double float matrix data structure, it supports matrix multiplication, matrix copy, matrix add, matrix multiply a scalar, elementwise division (for quantization). Some basic matrix are derived from this class, like the transform matrix used in DCT, the quantization matrix for each 8 * 8 block. 

##### TransformMatrix

A subclass of **Matrix8f** singleton, it has a ```transpose()``` which return the transpose matrix of the DCT transform Matrix. It is immutable.

##### QuantizationMatrix

Given a quality number from 0 ~ 1, it can generate a quantization matrix of 8 * 8. Also a subclass of **Matrix8f**.  It used in the quantization steps.

##### BufferedImage

A java built-in image representation, we save the data after quantization to a file, then read it , do the reverse processing, then we get a RGB map (1-d array), then we can use this 1-d array to feed the BufferedImage to display on our JavaFX GUI. Our application has 3 picture in total, the initial picture, the picture after compression, the DCT matrix represented as RGB value output.

#### Algorithms : Encoder

In this chapter we are going through some basic implementations of the Jpeg Compression Process.

##### step1ColorTransform

First when we create a ```JpegModelObservable``` instance , we get a BufferImage instance of the file we want. Then we transfrom the BufferedImage to a pixel 1-d array. Next we fill the YuvColor instance using this pixel 1-d array. So far we've done the color transformation.

##### step2Subsampling

The 4:2:0 subsampling is a little bit harder to understand at first. What subsampling means : for three 4 width, 2 height block, one such 4 * 2 block for a chroma (Y, U, V). For the Y block, we make them stay the same. for U block,  we set the first row all the same U value of the first pixel (0,0) 's U value, and for Y block we set the second row all the same V value for the first pixel in second line (1,0)'s V value. Resulting in U, V blocks, some adjacent pixel have the same value (and before subsampling they dont). This is the first place where information were lost.

##### step3DctAndQuantization

The Purpose of DCT, is to transform from color space to frequency space, and frequency space can store more information with certain loss. The trick is simple, divide the whole picture to 8 * 8 matrix, for every Y, U V values (range from 0 ~ 255) we minus then with 128, then apply the DCT transformation, get the dct matrix. However in the low frequency area there are a lot of values close to 0 but no zero. In order to get more zeros so we can encode them later using cross entropy coding, we use quantization matrix to round the elements in the matrix to a nearest integer (to generate more 0s in low frequency). Since human eyes are not sensitive to low frequency areas, so loss in low frequency areas is totally acceptable. Quantization is the second place where we lost information.

So far our encoder has completed. The data structure we obtained is not a standart Jpeg file format. But it can be understand by the decoder we are going to talk about in next chapter.

#### Algorithms : Decoder

#### Examples

#### References





