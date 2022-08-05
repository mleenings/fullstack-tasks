#!/bin/bash
(cd tasks-domain && gradle clean build)
(cd tasks-web && gradle clean build)
