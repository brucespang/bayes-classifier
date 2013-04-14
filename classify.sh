#!/usr/bin/env bash
INPUT="`dirname $0`/input"
BIN="java -jar `dirname $0`/target/bayes-classifier-0.0.1-SNAPSHOT-standalone.jar"
echo "# Experiment 1: US vs Russia"
$BIN -fn us -ft $INPUT/usCities100.txt -fe $INPUT/usCitiesNext50.txt -sn russia -st $INPUT/russiaCities100.txt -se $INPUT/russiaCitiesNext50.txt
echo ""
echo ""

echo "# Experiment 2: US vs Other"
$BIN -fn us -ft $INPUT/usCities100.txt -fe $INPUT/usCitiesNext50.txt -sn other -st $INPUT/otherCities100.txt -se $INPUT/otherCitiesNext50.txt
echo ""
echo ""

echo "# Experiment 3: Russia vs Other"
$BIN -fn russia -ft $INPUT/russiaCities100.txt -fe $INPUT/russiaCitiesNext50.txt -sn other -st $INPUT/otherCities100.txt -se $INPUT/otherCitiesNext50.txt
