#!/bin/bash
(cd tasks-common && gradle clean build)
(cd tasks-web && gradle clean build)
