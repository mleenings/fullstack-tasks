#!/bin/bash
(cd tasks-ui/src && eslint "**/*.ts" --fix)
(cd tasks-ui && gradle build)
