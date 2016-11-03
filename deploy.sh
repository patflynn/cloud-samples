#!/usr/bin/env bash
## Steps to deploy
## TODO: parameterize some of this or pull it into the maven build
gcloud config set compute/zone us-central1-b
gcloud config set project positive-karma-844
## publishes our container to GCR
mvn deploy

## create the cluster if you haven't already
gcloud container clusters create simple-spring --scopes=https://www.googleapis.com/auth/cloud_debugger
## initialize your kubectl creds/context
gcloud container clusters get-credentials simple-spring
## you'll probably have to do this for dumb reasons
gcloud auth application-default login

## start the app
kubectl run simple-spring --image=gcr.io/positive-karma-844/simple-spring --port=8080
## expose it to the world
kubectl expose deployment simple-spring --type="LoadBalancer"
## checkout your public IP (may have to wait a few minutes)
kubectl get service simple-spring

## run a local proxy to the kubectl API. Find it here:http://localhost:8081/ui
kubectl proxy

## to update your deployment after a new push.
## There's some policy stuff I need to look at to make it do this automatically.  In the mean time
## as long as you've published a new version of the app you just have to edit the file to use the
## latest version tag, save, and then exit.  Check the UI to see it update like magic.
kubectl edit deployment simple-spring

## to cleanup (don't waste the earth's computer resources!(and money))
gcloud container clusters delete simple-spring