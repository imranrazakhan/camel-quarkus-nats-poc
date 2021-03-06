package com.imran.poc.nats;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kubernetes.KubernetesConstants;

@ApplicationScoped
public class NatsPublisherRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://ipc?repeatCount=1&delay=60000").routeId("nats-pub")
                .setHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, simple("yq-dev"))
                .setHeader(KubernetesConstants.KUBERNETES_SECRET_NAME, simple("broker"))
                .to("kubernetes-secrets:///?kubernetesClient=#kubernetesClient&operation=getSecret")
                .setHeader("nats_password")
                .groovy("new String( request.getBody().getData().get('amq-password').decodeBase64() )")
                .to("nats:testtopic?servers=10.149.10.221:30222,10.149.10.222:30222,10.149.10.223:30222")
                .to("log:DEBUG?showAll=true&multiline=true").log("Route End");
    }
}
