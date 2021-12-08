#! /bin/sh

# installs all

cd "$(dirname "$0")"
source env-settings.sh

cd ../$inbound_project
#project=$inbound_project ./openshift-deploy.sh $1

cd ../$outbound_project
project=$outbound_project ./openshift-deploy.sh $1

cd ../$xlate_project
project=$xlate_project ./openshift-deploy.sh $1

cd ../$testserver_project
project=$testserver_project ./openshift-deploy.sh $1

cd "$(dirname "$0")"
./configure-openshift.sh
