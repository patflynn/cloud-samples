#!/usr/bin/env bash
mvn install
mvn docker:build
docker tag gct/spring-boot-simple gcr.io/positive-karma-844/simple-spring
gcloud config set compute/zone us-central1-b
gcloud config set project positive-karma-844
gcloud docker push gcr.io/positive-karma-844/simple-spring
gcloud container clusters create simple-spring --scopes=https://www.googleapis.com/auth/cloud_debugger
gcloud container clusters get-credentials simple-spring
kubectl run simple-spring --image=gcr.io/positive-karma-844/simple-spring --port=8080
kubectl expose deployment simple-spring --type="LoadBalancer"

## to cleanup
## gcloud container clusters delete simple-spring