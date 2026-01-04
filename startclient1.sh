#!/bin/bash

IMAGE=spigot-1.8.8

# Detect host OS and set up GUI forwarding
OS=$(uname -s)
DOCKER_ARGS="-v $(pwd):/workspace -w /workspace"

if [[ "$OS" == "Linux" ]]; then
    if [[ -n "$DISPLAY" && -S /tmp/.X11-unix/X0 ]]; then
        echo "Using X11 forwarding"
        DOCKER_ARGS="$DOCKER_ARGS -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix"
    elif [[ -n "$WAYLAND_DISPLAY" && -S /run/user/$(id -u)/$WAYLAND_DISPLAY ]]; then
        echo "Using Wayland forwarding (XWayland may still be required)"
        DOCKER_ARGS="$DOCKER_ARGS -e WAYLAND_DISPLAY=$WAYLAND_DISPLAY -v /run/user/$(id -u)/$WAYLAND_DISPLAY:/run/user/$(id -u)/$WAYLAND_DISPLAY"
    else
        echo "Warning: no display detected, client may not render"
    fi
elif [[ "$OS" == "Darwin" ]]; then
    echo "Detected macOS (requires XQuartz)"
    DOCKER_ARGS="$DOCKER_ARGS -e DISPLAY=host.docker.internal:0"
elif [[ "$OS" == MINGW* || "$OS" == CYGWIN* || "$OS" == MSYS* ]]; then
    echo "Detected Windows (requires VcXsrv)"
    DOCKER_ARGS="$DOCKER_ARGS -e DISPLAY=host.docker.internal:0.0"
fi

# Run client in a disposable container
exec docker run --rm -it \
    $DOCKER_ARGS \
    -p 5005:5005 \
    $IMAGE \
    bash -c 'cd runtime && java -Xincgc -Xms1024M -Xmx1024M -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -cp "../bin/minecraft:../src/minecraft:../jars/versions/1.8.8/1.8.8.jar:../lib:../lib/*:../jars/libraries/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar:../jars/libraries/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-nightly-20150209/lwjgl-platform-2.9.4-nightly-20150209-natives-linux.jar:../jars/libraries/com/ibm/icu/icu4j-core-mojang/51.2/icu4j-core-mojang-51.2.jar:../jars/libraries/org/apache/httpcomponents/httpcore/4.3.2/httpcore-4.3.2.jar:../jars/libraries/org/apache/logging/log4j/log4j-api/2.0-beta9/log4j-api-2.0-beta9.jar:../jars/libraries/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar:../jars/libraries/net/java/dev/jna/jna/3.4.0/jna-3.4.0.jar:../jars/libraries/com/paulscode/libraryjavasound/20101123/libraryjavasound-20101123.jar:../jars/libraries/net/sf/jopt-simple/jopt-simple/4.6/jopt-simple-4.6.jar:../jars/libraries/com/google/guava/guava/17.0/guava-17.0.jar:../jars/libraries/oshi-project/oshi-core/1.1/oshi-core-1.1.jar:../jars/libraries/org/apache/httpcomponents/httpclient/4.3.3/httpclient-4.3.3.jar:../jars/libraries/org/apache/commons/commons-compress/1.8.1/commons-compress-1.8.1.jar:../jars/libraries/net/java/dev/jna/platform/3.4.0/platform-3.4.0.jar:../jars/libraries/com/paulscode/codecjorbis/20101023/codecjorbis-20101023.jar:../jars/libraries/com/paulscode/soundsystem/20120107/soundsystem-20120107.jar:../jars/libraries/com/paulscode/librarylwjglopenal/20100824/librarylwjglopenal-20100824.jar:../jars/libraries/org/lwjgl/lwjgl/lwjgl_util/2.9.4-nightly-20150209/lwjgl_util-2.9.4-nightly-20150209.jar:../jars/libraries/commons-codec/commons-codec/1.9/commons-codec-1.9.jar:../jars/libraries/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar:../jars/libraries/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:../jars/libraries/org/lwjgl/lwjgl/lwjgl/2.9.4-nightly-20150209/lwjgl-2.9.4-nightly-20150209.jar:../jars/libraries/commons-io/commons-io/2.4/commons-io-2.4.jar:../jars/libraries/com/mojang/realms/1.7.39/realms-1.7.39.jar:../jars/libraries/com/mojang/authlib/1.5.21/authlib-1.5.21.jar:../jars/libraries/com/google/code/gson/gson/2.2.4/gson-2.2.4.jar:../jars/libraries/com/mojang/netty/1.8.8/netty-1.8.8.jar:../jars/libraries/tv/twitch/twitch/6.5/twitch-6.5.jar:../jars/libraries/com/paulscode/codecwav/20101023/codecwav-20101023.jar:../jars/libraries/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-linux.jar:../jars/libraries/org/apache/logging/log4j/log4j-core/2.0-beta9/log4j-core-2.0-beta9.jar:../jars/libraries/io/netty/netty-all/4.0.23.Final/netty-all-4.0.23.Final.jar" -Djava.library.path=../jars/versions/1.8.8/1.8.8-natives Start'

