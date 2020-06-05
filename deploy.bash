#!/bin/bash
# Create folder includes code
mkdir -p /deploy/server&& cp docker-compose.yml /deploy/ && cd server && mvn clean install