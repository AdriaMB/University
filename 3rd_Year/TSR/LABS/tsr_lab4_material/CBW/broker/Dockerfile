# Using as a base the tsr-zmq image
FROM tsr-zmq
# The COPY command assumes that the broker program is in the same directory than the Dockerfile.
# First we copy the file tsr-js(containing all the methods that will be used) that we find in the ./ directory in base directory of THE IMAGE
COPY ./tsr.js tsr.js
# Create a new directory broker in the image
RUN mkdir broker
# Change to that directory
WORKDIR broker
# Copy the broker.js file we find in ./ into the broker directory of the image as mybroker.js
COPY ./broker.js mybroker.js
# Port 9998 is published for clients and 9999 for workers
EXPOSE 9998 9999
# Provide the default values in the execution of the container
CMD node mybroker 9998 9999


# The image will be generated with this command:
# docker build -t broker .


# We can start the docker CONTAINER by executing:
# docker run -name mybroker broker
# or
# docker run broker
