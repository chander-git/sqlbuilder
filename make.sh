#!/bin/bash

mvn clean install
cp -rf ./target/RDPMS-API-1.0.jar ./bin/
cp -rf ./resources ./bin
