# Naive Bayes Classifier

## Running

To run the three trials and see the accuracy and classification data for each, run `./classify.sh`

If you'd like to run other classifications, run:

	java -jar ./target/bayes-classifier-0.0.1-SNAPSHOT-standalone.jar -fn FIRST-CLASS-NAME -ft FIRST-CLASS-INPUT-FILE -fe FIRST-CLASS-EVAL-FILE -sn SECOND-CLASS-NAME -st SECOND-CLASS-INPUT-FILE -se SECOND-CLASS-EVAL-FILE

## About

The classifier is implemented using Clojure.

The code is structured as follows:

- `src/classifier.clj` contains a library for naive bayes classification
- `src/app.clj` wraps the classifier library to make it usable from the command line
- `src/common.clj` contains general functions

## Compiling

To rebuild the jar, you'll need [clojure](http://clojure.org/downloads) and [leiningen](https://github.com/technomancy/leiningen). Then run `lein uberjar`

Or if you don't feel like rebuilding the jar:

	lein run -fn FIRST-CLASS-NAME -ft FIRST-CLASS-INPUT-FILE -fe FIRST-CLASS-EVAL-FILE -sn SECOND-CLASS-NAME -st SECOND-CLASS-INPUT-FILE -se SECOND-CLASS-EVAL-FILE
