#!/bin/bash
# shellcheck disable=SC1105
((cd .. && gradle bootRun &) && (cd ../tasks-ui && ng serve))