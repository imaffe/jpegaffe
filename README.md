## JPEG Processor using JavaFX framework

author : @imaffe @jason_y

#### How to run using Maven

1. Download ```Maven``` and configure it properly, can go to [JavaFX 11 Tutorial](https://openjfx.io/openjfx-docs/)
2. run ```mvn compile exec:java``` in command line

#### Design Manual

- what is Observable List ?
- how to set the logger module ? https://openjfx.io/openjfx-docs/
    - how to organize the logger module ? 
- invalidation listener? What does it mean ?
- javadocs support ! 
- it's better to create a "controller" to controll the view
  the current controller is coupled with view 
- looks like the observer pattern is not used yet.
- My MVC still got some problem
- Can use an Image View to show the image
- why immutable objects must use a builder?
- **How to use base class(or interface) reference to access child class method ? **

#### Tasks
1. Use image view to present a basic imported image

#### BufferedImage

General info is on javadoc. Google it and you can find Chinese or English version.

##### ColorModel

#### Finally
There are countless representations of an Image, but our teacher only want us to interpret
one type of image. So to be simple, we just need to assume the data input is the same as the demo
code. That is all I needed.

