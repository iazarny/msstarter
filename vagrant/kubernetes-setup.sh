#!/bin/sh
#
# 04-Jul-2017 Igor Azarny (iazarny@yahoo.com)

if which kubectl  >/dev/null; then
   	echo "skip kubernetes installation"
else

    sudo iptables -t filter --policy FORWARD ACCEPT

    curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.23.0/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

    curl -sSL  http://storage.googleapis.com/kubernetes-release/release/v1.8.0/bin/linux/amd64/kubectl > kubectl

    chmod +x kubectl

    sudo mv ./kubectl /usr/local/bin/kubectl


    curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.23.0/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

    echo "\n\n\nReady ..."

fi    