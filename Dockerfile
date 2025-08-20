FROM openjdk:8-jre

# Install bash and python3
RUN apt-get update && apt-get install -y bash python3 && rm -rf /var/lib/apt/lists/*

# Set working directory to project root
WORKDIR /workspace

# Ensure all shell scripts are executable
# RUN chmod +x /workspace/*.sh

# Default command: keep container alive for exec
CMD ["/bin/bash"]
