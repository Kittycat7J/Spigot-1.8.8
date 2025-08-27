# Dockerfile for Spigot-1.8.8 development
FROM openjdk:8-jdk

# Set non-interactive frontend for apt
ENV DEBIAN_FRONTEND=noninteractive

# Install Python 2.7, Java 8, and other dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        python2.7 python2.7-dev \
        x11-apps \
	    scala \
        git \
	    patch \
	    wine \
        wget \
	    sudo \
        unzip \
        ca-certificates \
        libxext6 libxrender1 libxtst6 libxi6 libgl1-mesa-glx mesa-utils x11-xserver-utils && \
    ln -sf /usr/bin/python2.7 /usr/bin/python && \
    rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME
# ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
# ENV PATH="$JAVA_HOME/bin:$PATH"

# Create a user to avoid running as root
RUN useradd -ms /bin/bash spigot && echo "spigot ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
USER spigot
WORKDIR /home/spigot

# Entrypoint for interactive use
CMD ["/bin/bash"]

# Usage:
#   docker build -t spigot-1.8.8 .
#   docker run -it --rm -v $(pwd):/workspace -w /workspace spigot-1.8.8
# For GUI: add -e DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix
# For server: add -p 25565:25565
