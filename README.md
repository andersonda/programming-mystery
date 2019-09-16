## Introduction
This repository contains ongoing work on software to facilitate in-class code analysis activities in programming courses. While the current state represents a (mostly) working application, expect frequent changes and feature additions.

## Features
##### Programming problem selection
Arbitrary Java files can be selected at run-time using the `File -> Load -> Code` menu option. The software then compiles and captures the output of the selected Java file, then generates a question of the form "What is the _nth_ line of output?" for each line of output.

##### Syntax Highlighting
The selected Java file is displayed in an IDE-like text area that supports syntax highlighting and font size changing (the latter of which is currently buggy).

##### Team Management
Team names and scores are stored in a SQLite database. Multiple sets of teams can be switched between by using the `File -> Load -> Teams` menu option.

The `Edit -> New Teams` menu option can be used to create a new set of teams.

Currently, team and score editing and team removal are not supported (but are planned features). In the mean time, the database created by Programming Mystery can be edited manually using a database editing tool such as [DB Browser for SQLite](https://sqlitebrowser.org/). The database is located at `<user.home>/programming-mystery/data.db` and is created automatically upon first execution.

##### Response Scoring
For each line of output, each team can enter their answer. Pressing the `Check` button will grade the responses for the current output line and provide feedback (check-mark for correct, x-mark for incorrect). The `Next` and `Prev` buttons can be used to cycle through the program output line questions. Overall scores are also kept track of and displayed.

##### Answer viewing
The answer to the current line of output can be viewed by using the `View -> Answers -> Current` menu option. To view answers for all lines output by the current Java file, the `View -> Answers -> All` menu option can be used.

## Sample Problems

The [programming-mystery-questions](https://github.com/andersonda/programming-mystery-questions) repository contains sample Java files to use with this application. For testing purposes, this is included as a submodule of this repository. If you are running a jar from the releases page, this repository needs to be cloned separately in order to be used. Once cloned, you can use the `File -> Load -> Code` menu option and navigate to the repo location.

## Installation

First, since this software uses the `javac` and `java` commands to compile and run Java files, these programs need to have environment variable entries.

Next, you have two options to run this software:
1. Import this project into IntelliJ IDEA and build and run it within the IDE
2. Download an executable jar from the [releases](https://github.com/andersonda/programming-mystery/releases) page of this repository

## Acknowledgements
Special thanks to Alex Anderson for contributing image editing work!
