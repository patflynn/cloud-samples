#!/bin/bash

SRC_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cat $SRC_DIR/target/cloud-debugger-demo-v1/WEB-INF/appengine-web.xml

$( boot2docker shellinit | grep '^ *export' )
gcloud config set project cloud-debugger-demo
echo | gcloud preview app setup-managed-vms
gcloud preview app deploy $SRC_DIR/target/cloud-debugger-demo-v1/

