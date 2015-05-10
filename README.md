#Eigenfaces

Eigenfaces for Facial Recognition implementation in Java to following Turk's thesis.


## Table of Contents

- [Features](#features)
- [Abstract](#abstract)
- [Dependencies](#install)
- [Usage with Examples](#usage)
- [Improvements](#improvements)
- [License](#license)


##Features<a name="features"></a>
- [Loads a set](https://github.com/danrichards/eigen-faces-project/blob/master/src/assignment04/EigenFaces.java#L52) of [images provided](https://github.com/danrichards/eigen-faces-project/tree/master/images) into [Images.java](https://github.com/danrichards/eigen-faces-project/blob/master/src/assignment04/Images.java).
-  Performs [Turk's Algorithm](https://github.com/danrichards/eigen-faces-project/blob/master/src/assignment04/Images.java#L208) on images. Storing components as [globals](https://github.com/danrichards/eigen-faces-project/blob/master/src/assignment04/Images.java#L12).
- Outputs helpful information on various transformations
  - Mean average Image derived from set.
  - Select Probe image  we're going to search
  - Probe minus mean (1-bit)
  - Absolute value of probe minus mean.
  - Inversion of probe minus mean.
- Outputs library of images with error ratings with respect to the probe.

##Abstract<a name="abstract"></a>
This project implements [Turk's thesis](https://raw.githubusercontent.com/danrichards/eigen-faces-project/master/TurkP_1991b.pdf) in Java for my CSC 450, Scientific Computing final project / assignment 04. The references below will provide a better explanation than I will. So here is the Java implementation in a nut shell (further details is provided in the comments).

1. Run EigenFaces.java
1. Images.java bootstraps MathLink ([JLink.jar](http://reference.wolfram.com/language/JLink/tutorial/Overview.html) required in your build path)
1. Images.java loads images from `src/images` folder.
1. Images.java will load run Turk's algorithm [(step-by-step details in the comments)](https://github.com/danrichards/eigen-faces-project/blob/master/src/assignment04/Images.java#L208)
1. Images.java selects which probe to try. Adjust this accordingly for your testing.
1. Images.java Run our recognition accessing the weights of our training set with respect the weight built for the probe image.
1. EigenFaces.java draws our JFrame, reporting everything that happened.


### References
[Eigenfaces for Recognition, Turk & Pentland, MIT](https://raw.githubusercontent.com/danrichards/eigen-faces-project/master/TurkP_1991b.pdf)
[Onionesque Reality does a great job breaking Turk's and Pentland's Paper down into step-by-step instructions](https://onionesquereality.wordpress.com/2009/02/11/face-recognition-using-eigenfaces-and-distance-classifiers-a-tutorial/)

##Dependencies<a name="install"></a>

- Aside from MathLink, which you need to add [JLink](http://reference.wolfram.com/language/JLink/tutorial/Overview.html)  to your build path for, all dependencies and resources are included in this repo.
    
##Usage<a name="usage"></a>

### Image Library
1. All images should be identical in width and height (included set is 150 x 150 px).
1. All images should be 8-bit/Grayscale (preferably BITMAP)
1. Images should be cropped in such a way that eyes appear in the same general area in every picture. 
1. Probes should be different than the images in your set.
1. Items 1~3 apply to your probes as well. 
1. Multiple images of the same person will enhance your training set for recognition.

	Please note how the probe images differ from the images in the training set.

### Screenshot of App running Probe 0
<img src="https://raw.githubusercontent.com/danrichards/eigen-faces-project/master/test0.png" alt="Screenshot of App running Probe 0">

##### Close, but Clive Owen stole 1st place somehow. Possible the large amount of white space.

### Screenshot of App running Probe 1
<img src="https://raw.githubusercontent.com/danrichards/eigen-faces-project/master/test1.png" alt="Screenshot of App running Probe 0">

##### Not very good, we have several others who placed better. Adding more images of Natalie would help build a stronger training set.

### Screenshot of App running Probe 2
<img src="https://raw.githubusercontent.com/danrichards/eigen-faces-project/master/test2.png" alt="Screenshot of App running Probe 0">

##### Our best result, the hair must have helped, haha. Unfortuntely, Jennifer's neighbor has very similar hair to the probe, scoring slightly better.

## Improvements<a name="improvements"></a>
- The easiest way to improve this implementation is with a better training set. These are head shots I took off of Google Images.
- You would also benefit from adding multiple images for each person in your training set. This will allow your vectors to account for more variations and come closer when scoring weights.

## License<a name="license"></a>

`eigen-faces-project` is free software distributed under the terms of the [MIT license](http://opensource.org/licenses/MIT).