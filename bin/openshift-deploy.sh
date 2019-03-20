#! /usr/bin/env bash

set -e

if [ -z "$project" ];
then
    echo "No project set, run 'source ../bin/env-settings.sh'"
    exit -1
fi

echo "Project: $project"

if [ $# -gt 0 ];
then
  echo "Creating all from scratch, continue with enter or terminate this."
  read

  oc new-build redhat-openjdk18-openshift:1.4 --binary=true --name=$project
  mvn clean package -DskipTests
  mkdir -p openshift/deployments
  rm -rf openshift/deployments/*
  cp target/*.jar openshift/deployments/

  oc start-build $project --from-dir=openshift --follow
  oc new-app $project

  # Create route only for inbound project
  if [ "$project" = "$inbound_project" ];
  then
      echo "Exposing inbound route"
	 oc expose svc/$project
  fi
else
  echo "Triggering new build"
  mvn clean package -DskipTests
  mkdir -p openshift/deployments
  rm -rf openshift/deployments/*
  cp target/*.jar openshift/deployments/
  oc start-build $project --from-dir=openshift --follow
fi
