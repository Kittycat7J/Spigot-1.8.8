#!/bin/bash
# Ensure Docker container is running
./docker_run.sh
# Run the server with port 25565 exposed
exec docker exec -it \
    spigot-1.8.8-dev \
    python runtime/startserver.py "$@"
