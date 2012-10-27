#!/bin/bash

pwd=$(pwd)

cp target/powerflare-server.jar target/dependency/
cd target/dependency
java -cp powerflare-server.jar:RXTXcomm.jar "-Djava.library.path=$pwd/lib" de.paluch.powerflare.Server $1