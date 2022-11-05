#!/bin/bash
# shellcheck disable=SC1105
((gradle bootRun &) && (cd tasks-ui && ng serve))