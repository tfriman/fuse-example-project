# Advanced Camel/Fuse 7.1 homework

This is a homework assignment submission to Advanced Camel development course.

Originally JBoss Fuse 6.3 was used to run the AMQ and test webservice. I ported everything to
OpenShift as Spring Boot micro services. Reasoning behind this was that this is the way new projects
are implemented using our tools.

Routes are implemented using Camel Java DSL because I found it easier for eyes.

Development was done using MacOS 10.14 so all shell scripts work for sure in MacOS Mojave.

## Building

First build artifacts used in all projects as models:

```mvn -f artifacts/pom.xml clean install```

Then make packages:

```mvn -f inbound/pom.xml clean package```

```mvn -f integrationtestserver/pom.xml clean package```

```mvn -f outbound/pom.xml clean package```

```mvn -f xlate/pom.xml clean package```

## Running things locally:

### Prerequisites:
Set up AMQ using openwire and 61616 port.

Each project can be run ```mvn spring-boot``` run in the project root directory. You can test the
application using ```inbound/curl*.sh``` scripts.

You should start projects in this order:

- integrationtestserver
- inbound
- xlate
- outbound

### Testing locally

First make sure you are using localhost:

``unset $inbound_url``

Run inbound ```curl.sh``` to see "1" on the testserver log or ```curl-no-match.sh``` to see "0".

## Deployment to OpenShift

Tested with OpenShift 3.11. Because of the shared com.customer.app.artifacts dependency is not found from
public Maven repositories I ended up using binary builds. So each component has openshift-deploy.sh
script in their root directory. AMQ should be installed, this was tested with AMQ 7.2 version.

Create project for deployment:

```export ocp_project=camel-assignment```

```oc new-project $ocp_project```

NOTE: All shell scripts assume you have logged on to OpenShift and you are in the correct project.

### AMQ installation

Install AMQ to your OpenShift cluster using [instructions](https://access.redhat.com/documentation/en-us/red_hat_amq/7.2/html/deploying_amq_broker_on_openshift_container_platform/install-deploy-ocp-broker-ocp#installing-broker-ocp_broker-ocp).

```
oc new-app --namespace $ocp_project --template=amq-broker-72-basic \
   -e AMQ_PROTOCOL=openwire,amqp \
   -e AMQ_USER=admin \
   -e AMQ_PASSWORD=admin \
   -e AMQ_ROLE=admin
```

Alternative way if your cluster already has AMQ streams for some other version (tested with 6.3):

```oc new-app --search amq```

And find the template name. Tested with amq63-basic and it works as well.

```
oc new-app --namespace $ocp_project --template=amq63-basic \
   -e AMQ_PROTOCOL=openwire,amqp \
   -e AMQ_USER=admin \
   -e AMQ_PASSWORD=admin \
   -e AMQ_ROLE=admin
```

### Prerequisite for running shell scripts found in each sub projects' root directory:

Edit in bin/env-settings.sh line ```export inbound_url=EDIT.ME.IN../BIN/ENV-SETTINGS.SH``` value to match ```oc get route $inbound_project``` and save file.
Run ```source ../bin/env-settings.sh``` to get each projects specific deployment name. You can change the
deployment names if you so wish.

### ```openshift-deploy.sh``` usage and documentation

#### First deployment

If ```openshift-deploy.sh``` is called with **any** parameter it will create first build configuration for component
(project). So when doing the first time deployment you **should** give a parameter to script. Script always runs ```mvn clean package -DskipTests```. After that it will copy the uberjar to openshift/deployments
directory and start the s2i build in openshift directory and deploy that to OpenShift. If project is "inbound", it will expose the service.

S2I image used should be present in OpenShift already:

```oc get imagestream -n openshift | grep openjdk```

should return: redhat-openjdk18-openshift

If it is not found, run
```oc import-image my-redhat-openjdk-18/openjdk18-openshift --from=registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift --confirm```

If ```openshift-deploy.sh``` is called without parameters it will run ```mvn clean package -DskipTests``` and start the
binary build using the uberjar created.

### Monitoring of the applications using ```openshift-logs.sh```

Each component has openshift-logs.sh in their root directory. Using that script gives you ```oc logs
-f $project``` and that relies on the fact that each has only one running pod.

### Testing deployment

First run

```source bin/env-settings.xml```

to get env vars pointing to projects on OpenShift

Then run inbound ```curl.sh``` to see "1" on the integration test server log or ```curl-no-match.sh``` to see "0".

## What to improve

- More unit tests
- OpenShift deployment using git repo as a source
- OpenShift deployment using templates and oc apply
- Maven structure, add parent pom and remove dependencies not used.
- Add monitoring using prometheus
- Use Istio and Kiali to visualize traffic
- Use Istio to break routes and show how error handling works
