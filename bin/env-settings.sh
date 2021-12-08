# Used to set env

export inbound_project=inbound
# Edit me to match "oc get route $inbound_project"

export outbound_project=outbound

export xlate_project=xlate

export testserver_project=integrationtestserver

export inbound_url=$(oc get route inbound --template '{{.spec.host}}')
