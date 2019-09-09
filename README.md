## Introduction
This repository contains ongoing work on software to facilitate in-class code analysis activities in programming courses. While the current state represents a (mostly) working application, expect frequent changes and feature additions.

## Features
##### Programming problem selection
Arbitrary Java files can be selected at run-time using the `File -> Load -> Code` menu option. The software then compiles and captures the output of the selected Java file, then generates a question of the form "What is the _nth_ line of output?" for each line of output.

##### Syntax Highlighting
The selected Java file is displayed in an IDE-like text area that supports syntax highlighting and font size changing (the latter of which is currently buggy).

##### Team selection
Team names can be read in from text files using the `File -> Load -> Teams` menu option.

##### Response Scoring
For each line of output, each team can enter their answer. Pressing the `Check` button will grade the responses for the current output line and provide feedback (check-mark for correct, x-mark for incorrect). The `Next` and `Prev` buttons can be used to cycle through the program output line questions. Overall scores are also kept track of and displayed.

##### Answer viewing
The answer to the current line of output can be viewed by using the `View -> Answers -> Current Answer` menu option. To view answers for all lines output by the current Java file, the `View -> Answers -> All Answers` menu option can be used.

## Sample Problems

The [programming-mystery-questions](https://github.com/andersonda/programming-mystery-questions) repository contains sample Java files to use with this application. For testing purposes, this is included as a submodule of this repository. If you are running a jar from the releases page, this repository needs to be cloned separately in order to be used. Once cloned, you can use the `Load -> Code` menu option and navigate to the repo location.

## Installation

First, since this software uses the `javac` and `java` commands to compile and run Java files, these programs need to have environment variable entries.

Next, you have two options to run this software:
1. Import this project into IntelliJ IDEA and build and run it within the IDE
2. Download an executable jar from the releases page of this repository
