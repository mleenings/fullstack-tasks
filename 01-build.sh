#!/bin/bash
(cd tasks-backend && gradle build)
# (cd tasks-ui && eslint . --fix)
(cd tasks-ui && gradle build)
# or simpler:
# gradle build
