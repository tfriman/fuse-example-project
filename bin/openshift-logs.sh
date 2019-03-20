#!/usr/bin/env bash

if [ -z "$project" ];
then
    echo "No project set, run 'source ../bin/env-settings.sh'"
    exit -1
fi

echo "Logs for project: $project"

# Gets logs for some pod

oc logs -f `oc get pods --selector app=$project -o jsonpath="{.items[0].metadata.name}"`
