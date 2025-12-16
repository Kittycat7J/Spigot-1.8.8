#!/bin/bash
set -e

docker run --rm -it \
    -v "$(pwd)":/workspace -w /workspace \
    -p 5005:5005 \
    -p 6080:6080 \
    -p 5900:5900 \
    spigot-1.8.8 \
    sh -c "sudo chmod +x /home/spigot/dockerVNC.sh && /home/spigot/dockerVNC.sh && export DISPLAY=:99 && python runtime/startclient.py \"$@\""
