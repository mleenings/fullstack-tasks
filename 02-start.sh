#!/bin/bash
((cd tasks-backend && gradle bootRun) && (cd tasks-ui && ng serve))
