# Kubernetes engine

## Create cluster

1. [Create cluster]:

```
gcloud container clusters create --enable-ip-alias --enable-autoupgrade --zone "us-central1-c" --addons HorizontalPodAutoscaling,HttpLoadBalancing --enable-autoupgrade --enable-autorepair [CLUSTER_NAME] 
```

## Setup credentials

1. [Setup credentials] for `gcloud` and `kubectl`: 

```powershell
gcloud container clusters get-credentials CLUSTER_NAME --zone "us-central1-c"
```

## Download example application

```
git clone https://github.com/kubernetes/examples
cd examples/guestbook
```

## Configure proxy

1. We must configure proxy before use `kubectl`:

```
SET HTTPS_PROXY=
SET HTTP_PROXY=
SET NO_PROXY=
```

## Deploy redis

1. Deploy redis master: 

```
kubectl create --insecure-skip-tls-verify=true -f redis-master-deployment.yaml
```

2. View running pods:

```
kubectl get pods
```

3. Deploy redis service:

```
kubectl create -f redis-master-service.yaml
```

4. Verify that services is created:

```
kubectl get service
```

5. Deploy redis slave:

```
kubectl create -f redis-slave-deployment.yaml
```

6. View running pods:

Check two Redis workers are running as slaves

```
kubectl get pods
```

7. Deploy redis slave service: 

```
kubectl create -f redis-slave-service.yaml
```

8. Verify that services is created:

```
kubectl get service
```

## Deploy front

1. Create frontend deployment:

```
kubectl create -f frontend-deployment.yaml
```

2. Expose frontend as external IP address:

```
sed -i -e 's/NodePort/LoadBalancer/g' frontend-service.yaml
```

3. Create frontend service:

```
kubectl create -f frontend-service.yaml
```

## Test

1. Find external IP address for application:

```
kubectl get service --watch
```

2. Copy `EXTERNAL-IP` and open it in a browser

3. You can analyze: 
    * GCP -> Kubernetes Engine -> Clusters
    * GCP -> Kubernetes Engine -> Workloads
    * GCP -> Kubernetes Engine -> Services & Ingress

## Clean

1. [Delete cluster]:

```
gcloud container clusters delete CLUSTER_NAME
```

[Create cluster]: https://cloud.google.com/sdk/gcloud/reference/container/clusters/create
[Setup credentials]: https://cloud.google.com/sdk/gcloud/reference/container/clusters/get-credentials
[Delete cluster]: https://cloud.google.com/sdk/gcloud/reference/container/clusters/delete
