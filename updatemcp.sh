#!/bin/bash
./docker_run.sh
exec docker exec -it spigot-1.8.8-dev python runtime/updatemcp.py "$@"
