# Microservice starter project



## Prerequisites

Install and run docker and kubernetes. In case if you already have such environment, you can skip this step. For local environment we will use Ubuntu 16.04.3 LTS. 

Steps are following:

  * Install java 8 and maven
  * Install docker
  * Install kubectl and minikube
  * Start docker registry 

```
docker run -d -p 5000:5000 --restart=always --name registry registry:2
```

  * Check is registry running 

```
ubuntu@starter:~/msstarter$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
6d21e786f897        registry:2          "/entrypoint.sh /e..."   17 hours ago        Up 17 hours         0.0.0.0:5000->5000/tcp   registry
```

  * Start kubernetes via minikube, wait a minute and check is it running
```
ubuntu@starter:~/msstarter$ sudo minikube start --vm-driver=none
Starting local Kubernetes v1.7.5 cluster...
Starting VM...
Getting VM IP address...
....... skipped .............

ubuntu@starter:~/msstarter$ sudo chown -R $USER $HOME/.kube
ubuntu@starter:~/msstarter$ sudo chgrp -R $USER $HOME/.kube
ubuntu@starter:~/msstarter$ sudo chown -R $USER $HOME/.minikube
ubuntu@starter:~/msstarter$ sudo chgrp -R $USER $HOME/.minikube
```  

  * Check is kubernetis running ?
```  
ubuntu@starter:~$ kubectl get po --all-namespaces
NAMESPACE     NAME                         READY     STATUS    RESTARTS   AGE
kube-system   kube-addon-manager-starter   1/1       Running   0          3h
kube-system   kube-dns-6fc954457d-cfp28    3/3       Running   0          3h
kube-system   kubernetes-dashboard-rw8lv   1/1       Running   0          3h
```  

## Build

```
git clone https://github.com/iazarny/msstarter.git
cd msstarter
mvn package docker:build docker:push
```

## Run 

  The "uber" yaml will be created during build procedure. It shall be used to launch entire example

```
kubectl create -f k8s/deploy-all.yml
```
## Expose to external IP address

   Expose service(s) on some IP addresses. Actually **st-zuul-web** is enough.

```
kubectl expose deployment st-zuul-web          --port=8081  --target-port=8080 --name=ext-st-zuul-web  --external-ip=172.28.128.3
kubectl expose deployment st-rest              --port=8082  --target-port=8080 --name=ext-st-rest      --external-ip=172.28.128.3
kubectl expose deployment st-eureka            --port=8083  --target-port=8080 --name=ext-st-eureka    --external-ip=172.28.128.3
kubectl expose deployment st-kibana            --port=5601  --target-port=5601 --name=ext-st-kibana    --external-ip=172.28.128.3
kubectl expose deployment st-elasticsearch     --port=9200  --target-port=9200 --name=ext-st-elasticsearch    --external-ip=172.28.128.3

```

## Checks

  * Vue UI shall be available http://172.28.128.3:8081
  * Get all arcticles http://172.28.128.3:8081/api/articles and http://172.28.128.3:8082/api/articles
  * Swagger http://172.28.128.3:8082/swagger-ui.html http://172.28.128.3:8082/v2/api-docs
  * Elastic http://172.28.128.3:9200/_cat/indices?v  http://172.28.128.3:9200/server-logs-2017.12.22/_search?pretty=true&size=5000
  * Kibana http://172.28.128.3:5601




## Known issues

### Runing kubernetes via minikube hints

  * In case if you are facing with some issues running kubernetes via minikube try to specify additional configuration for k8s
```  
sudo minikube start --vm-driver=none --kubernetes-version=v1.7.5 --extra-config=apiserver.ServiceClusterIPRange=10.0.0.0/24
```

### Under virtual machine

  * In case if services not available outside of VM try to check firewall and allow message forwarding. 
  
```
sudo iptables -t filter --policy FORWARD ACCEPT
```
  * Default ubuntu image has 10Gb disk size. It is not enough to development. Solution - resize disk.

```
VBoxManage clonehd "ubuntu-xenial-16.04-cloudimg.vmdk" "cloned.vdi" --format vdi
VBoxManage modifyhd "cloned.vdi" --resize 51200
```
Open UI , delete source and attach cloned. More info here https://stackoverflow.com/questions/11659005/how-to-resize-a-virtualbox-vmdk-file
