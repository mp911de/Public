#!/bin/bash

pwd=$(pwd)


java -cp @artifactId@-@version@.jar:RXTXcomm.jar "-Djava.library.path=$pwd" de.paluch.powerflare.Server $1