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
For each line of output, each team can enter their answer. Pressing the `Check` button will grade the responses for the current output line and provide feedback (check-mark for correct, x-mark for incorrect). Overall scores are also kept track of and displayed.

##### Answer viewing
The answer to the current line of output can be viewed by using the `View -> Answers -> Current Answer` menu option. To view answers for all lines output by the current Java file, the `View -> Answers -> All Answers` menu option can be used.

## Sample Problems

The [programming-mystery-questions](https://github.com/andersonda/programming-mystery-questions) contains sample Java files to use with this application and is included as a submodule of this repository.

## Installation

This section is under construction.
