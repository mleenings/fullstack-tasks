#!/bin/bash
((gradle bootRun) && (cd tasks-ui && ng serve))
