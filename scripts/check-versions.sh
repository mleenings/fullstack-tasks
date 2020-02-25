#!/bin/bash
# TODO grep version number and compare (but grepping is not stable for different versions...)
JAVA_VERSION = "11.0.6"
GRADLE_VERSION = "6.1.1"
NPM_VERSION = "6.13.7"
NODE_VERSION = "10.19.0"
ANGULAR_CLI_VERSION = "9.0.2"
ANGULAR_VERSION = "1.7.4"

echo "Testet with Java Version = $JAVA_VERSION and your Version is"
java -v

echo "Testet with Gradle Version = $GRADLE_VERSION and your Version is"
gradle -v

echo "Testet with node Version = $NODE_VERSION and your Version is"
node -v

echo "Testet with npm Version = $NPM_VERSION and your Version is"
npm -v

echo "Testet with Angular CLI Version = $ANGULAR_CLI_VERSION and your Version is"
ng --version
