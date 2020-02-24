#!/bin/bash
(cd tasks-backend && gradle clean build)
# (cd tasks-ui && eslint . --fix)
(cd tasks-ui && gradle clean build)
# or simpler:
# gradle build
