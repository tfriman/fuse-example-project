#!/usr/bin/env bash

inbound_url=$(oc get route inbound --template '{{.spec.host}}')

if [ -z ${inbound_url+x} ];
then
export inbound_url="localhost:8080"
fi

echo "Using URL: $inbound_url"

curl -v -v -H "content-type: application/xml" \
    -X POST \
    -d @src/test/resources/data/SimplePatientNotFirst.xml \
    http://$inbound_url/demos/match
