#!/bin/bash

pwd=$(pwd)


java -cp @artifactId@-@version@.jar "-Djiracompare.data.dir=$pwd" de.paluch.jira.compare.Server