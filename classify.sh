#!/usr/bin/env bash
BIN="java -jar target/bayes-classifier-0.0.1-SNAPSHOT-standalone.jar"
echo "# Experiment 1: US vs Russia"
$BIN -fn us -ft input/usCities100.txt -fe input/usCitiesNext50.txt -sn russia -st input/russiaCities100.txt -se input/russiaCitiesNext50.txt
echo ""
echo ""

echo "# Experiment 2: US vs Other"
$BIN -fn us -ft input/usCities100.txt -fe input/usCitiesNext50.txt -sn other -st input/otherCities100.txt -se input/otherCitiesNext50.txt
echo ""
echo ""

echo "# Experiment 3: Russia vs Other"
$BIN -fn russia -ft input/russiaCities100.txt -fe input/russiaCitiesNext50.txt -sn other -st input/otherCities100.txt -se input/otherCitiesNext50.txt
