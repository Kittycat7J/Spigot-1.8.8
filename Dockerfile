# Dockerfile for Spigot 1.8.8 GUI dev with VNC
FROM openjdk:8-jdk

ENV DEBIAN_FRONTEND=noninteractive

# Install required packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        python2.7 python2.7-dev \
        x11-apps mesa-utils \
        libgl1-mesa-dri libgl1-mesa-glx libglu1-mesa \
        libxext6 libxrender1 libxtst6 libxi6 libxrandr2 \
        x11vnc xvfb wget sudo unzip git patch scala wine \
        x11-xserver-utils && \
    wget -qO- https://github.com/novnc/noVNC/archive/refs/tags/v1.4.0.tar.gz | tar xz -C /opt && \
    ln -s /opt/noVNC-1.4.0 /opt/novnc && \
    wget -qO- https://github.com/novnc/websockify/archive/refs/tags/v0.10.0.tar.gz | tar xz -C /opt && \
    ln -s /opt/websockify-0.10.0 /opt/novnc/utils/websockify && \
    ln -sf /usr/bin/python2.7 /usr/bin/python && \
    rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN useradd -ms /bin/bash spigot && echo "spigot ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
USER spigot
WORKDIR /home/spigot

COPY dockerVNC.sh /home/spigot/dockerVNC.sh
RUN sudo chmod +x /home/spigot/dockerVNC.sh

CMD ["/bin/bash"]
