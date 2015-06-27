# Data Driven testing

Data Driven Testing is an extensible Java Testing Framework based on the idea 
that a test scenario should be very easy to create, to maintain and to extend 
regardless the type of test. 

## Design

As a modern software, it follows some of the most modern principles as: 

* fluent API for user-friendliness
* functional design with high order functions for extensibility
* builder pattern for construction clarity and immutability
* independent modules as key concept

## Principle

A test scenario is a sequence of actions that describes the scenario itself. Actions
are primitive operations such as:
 
* loading input data from resource file whatever the format and the result as the input value
* applying function to the input value
* mapping the input value to something else, for instance pass it to a method we want to test
* saving the result to file
* comparing saved output file to output resource file

You can also create your own script as a function or even your own scenario for all
input resource files. The only thing you have to do is describe your test and create input data.

## Practice

To do
