# Documentation

The Documentation of the application.

## Table of contents

* [General info](#general-info)
* [Installation](#installation)
* [Build the Documentation](#build-the-documentation)

## General info

The following is included:
* architecture documentation (arc42)
* development guide
* presentation

## Installation

To run this project, install it locally using maven:

```shell
> gem install asciidoctor
```
and/or
```shell
> brew install asciidoctor
```
and/or
```shell
> sudo gem install -n /usr/local/bin asciidoctor-pdf --pre
> asciidoctor -a toc architecture.adoc
```
to build use:
```shell
asciidoctor-pdf architecture.adoc
```
or in the appropriate folder:
```shell
./build.sh
```
(and/or development.adoc)