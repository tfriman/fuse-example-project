#! /bin/sh

# broker amqp url

oc set env deployment/inbound amq.brokerURL=tcp://amqbroker-amqp-0-svc:5672

oc set env deployment/outbound amq.brokerURL=tcp://amqbroker-amqp-0-svc:5672

oc set env deployment/integrationtestserver amq.brokerURL=tcp://amqbroker-amqp-0-svc:5672

oc set env deployment/xlate amq.brokerURL=tcp://amqbroker-amqp-0-svc:5672

oc set env deployment/outbound nextgate.host=integrationtestserver

# jolokia fix: https://access.redhat.com/solutions/5600781

oc set env deployment/inbound AB_JOLOKIA_OPTS=caCert=/var/run/secrets/kubernetes.io/serviceaccount/service-ca.crt,clientPrincipal.1=cn=system:master-proxy,clientPrincipal.2=cn=hawtio-online.hawtio.svc,clientPrincipal.3=cn=fuse-console.fuse.svc

oc set env deployment/outbound AB_JOLOKIA_OPTS=caCert=/var/run/secrets/kubernetes.io/serviceaccount/service-ca.crt,clientPrincipal.1=cn=system:master-proxy,clientPrincipal.2=cn=hawtio-online.hawtio.svc,clientPrincipal.3=cn=fuse-console.fuse.svc

oc set env deployment/xlate AB_JOLOKIA_OPTS=caCert=/var/run/secrets/kubernetes.io/serviceaccount/service-ca.crt,clientPrincipal.1=cn=system:master-proxy,clientPrincipal.2=cn=hawtio-online.hawtio.svc,clientPrincipal.3=cn=fuse-console.fuse.svc

oc set env deployment/integrationtestserver AB_JOLOKIA_OPTS=caCert=/var/run/secrets/kubernetes.io/serviceaccount/service-ca.crt,clientPrincipal.1=cn=system:master-proxy,clientPrincipal.2=cn=hawtio-online.hawtio.svc,clientPrincipal.3=cn=fuse-console.fuse.svc
