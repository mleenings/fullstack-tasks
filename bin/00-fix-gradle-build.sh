#!/bin/bash
# This fixes the following error which appears at 'gradle build'
#
# Execution failed for task ':tasks-ui:nodeSetup'.
# > Could not resolve all files for configuration ':tasks-ui:detachedConfiguration1'.
#    > Could not find org.nodejs:node:10.19.0.
#      Searched in the following locations:
#        - https://nodejs.org/dist/v10.19.0/ivy.xml
#      Required by:
#         project :tasks-ui
#
# the cli not always saved all previos installation
npm install gulp
