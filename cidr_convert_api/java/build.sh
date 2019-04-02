#!/bin/bash

set -ex

cd cidr-api || exit 1
mvn package
java -cp target/cidr-api-1.0-SNAPSHOT-jar-with-dependencies.jar com.wizeline.App
