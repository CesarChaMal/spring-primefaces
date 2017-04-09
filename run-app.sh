#!/usr/bin/env bash

#
# command line runner for the Credit Suisse Trial app
#

# mvn -DskipTests compile
mvn compile test

mvn jetty:run
