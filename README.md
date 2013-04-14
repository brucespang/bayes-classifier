# Naive Bayes Classifier

## Running

Running the classifier requires a JVM.

To run the three trials and return accuracy and classification data:

	./classify.sh

If you'd like to run other classifications, run:

	java -jar ./target/bayes-classifier-0.0.1-SNAPSHOT-standalone.jar -fn FIRST-CLASS-NAME -ft FIRST-CLASS-INPUT -fe FIRST-CLASS-EVAL -sn SECOND-CLASS-NAME -st SECOND-CLASS-INPUT -se SECOND-CLASS-EVAL

## About

The classifier is implemented using Clojure.

The code is structured as follows:

	- src/classifier.clj contains a library for naive bayes classification
	- src/app.clj wraps the classifier library to make it usable from the command line
	- src/common.clj contains general functions

## Compiling

To rebuild the jar, you'll need clojure (http://clojure.org/downloads) and leiningen (https://github.com/technomancy/leiningen). Then run:

	 lein uberjar

Or if you don't feel like rebuilding the jar:

	lein run -fn FIRST-CLASS-NAME -ft FIRST-CLASS-INPUT -fe FIRST-CLASS-EVAL -sn SECOND-CLASS-NAME -st SECOND-CLASS-INPUT -se SECOND-CLASS-EVAL
