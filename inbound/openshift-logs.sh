#!/usr/bin/env bash

export project=inbound

# Gets logs for some pod

oc logs -f `oc get pods --selector app=$project -o jsonpath="{.items[0].metadata.name}"`
