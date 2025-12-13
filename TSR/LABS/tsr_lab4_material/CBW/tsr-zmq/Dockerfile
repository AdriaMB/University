# Take an LTS ubuntu distribution as a base
# In this case, ubuntu 22.04
FROM ubuntu:22.04
# Install the latest Node.js on that distribution
# First of all, preparing initial directory and repo packages synch.
WORKDIR /root
RUN apt-get update -y
# Second: install prerrequisites
RUN apt-get install curl ufw gcc g++ make gnupg -y
# Next: install NodeJS v20
RUN curl -sL https://deb.nodesource.com/setup_20.x | bash -
RUN apt-get update -y
RUN apt-get install nodejs -y
# Fourth: system up-to-date
RUN apt-get upgrade -y
# Last: initialize npm, add ZeroMQ for NodeJS apps
RUN npm init -y
RUN npm install zeromq@5

# We now generate an image from this dockerfile using:
# docker build -t tsr-zmq .
