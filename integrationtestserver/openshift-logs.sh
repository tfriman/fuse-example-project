#! /usr/bin/env bash

cd $(dirname $0)

source ../bin/env-settings.sh

export project=$testserver_project

../bin/openshift-logs.sh
