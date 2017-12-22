#!/bin/sh
#
# 04-Jul-2017 Igor Azarny (iazarny@yahoo.com)

if which docker >/dev/null; then
   	echo "skip docker installation"
else
	echo "docker installation"

apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"

apt-get update

apt-get install -y docker-ce

usermod -aG docker ubuntu

newgrp docker

docker run hello-world
        
fi

