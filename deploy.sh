#!/usr/bin/env bash
## Steps to deploy
## TODO: parameterize some of this or pull it into the maven build
gcloud config set compute/zone us-central1-b
gcloud config set project positive-karma-844
## publishes our container to GCR
mvn deploy

## create the cluster if you haven't already
gcloud container clusters create demo-app --scopes=https://www.googleapis.com/auth/cloud_debugger,https://www.googleapis.com/auth/datastore
## initialize your kubectl creds/context
gcloud container clusters get-credentials demo-app
## you'll probably have to do this for dumb reasons
gcloud auth application-default login

## start the app
kubectl run demo-app --image=gcr.io/gcp-next-java/simple-spring:{version} --port=8080
## expose it to the world
kubectl expose deployment demo-app --type="LoadBalancer"
## checkout your public IP (may have to wait a few minutes)
kubectl get service demo-app

## run a local proxy to the kubectl API. Find it here:http://localhost:8081/ui
kubectl proxy

## to update your deployment after a new push.
## There's some policy stuff I need to look at to make it do this automatically.  In the mean time
## as long as you've published a new version of the app you just have to edit the file to use the
## latest version tag, save, and then exit.  Check the UI to see it update like magic.
kubectl edit deployment demo-app

## to cleanup (don't waste the earth's computer resources!(and money))
gcloud container clusters delete demo-app

## You also need to delete the load balancers!! they cost money too. (I used the UI)

#note: how I debugged the cdb agent
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 -agentpath:/usr/local/google/home/paflynn/hack/kplay/cloud-debug-java/bin/cdbg_java_agent.so=--logtostderr=1 -Dcom.google.cdbg.module=sprint-boot-simple -Dcom.google.cdbg.version=v5 spring-boot-simple-0.1.0.jar -cp .
#IJ VM options for test cdb agent
-agentpath:/usr/local/google/home/paflynn/hack/kplay/cloud-debug-java/bin/cdbg_java_agent.so=--logtostderr=1