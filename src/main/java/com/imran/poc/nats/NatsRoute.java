package com.imran.poc.nats;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kubernetes.KubernetesConstants;

@ApplicationScoped
public class NatsRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://ipc?repeatCount=1&delay=60000").routeId("timer-ipc")
                .setHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, simple("production"))
                .setHeader(KubernetesConstants.KUBERNETES_SECRET_NAME, simple("broker"))
                .to("kubernetes-secrets:///?kubernetesClient=#kubernetesClient&operation=getSecret")
                .to("log:com.aggreko.ipc?showAll=true&multiline=true").log("Route End");
    }
}