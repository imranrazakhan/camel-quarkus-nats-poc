package com.imran.poc.nats;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class NatsSubscriberRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("nats:mytopic?servers=10.149.10.221:30222,10.149.10.222:30222,10.149.10.223:30222&maxMessages=5&queueName=myqueue")
                .routeId("nats-sub").to("log:com.imran.poc?showAll=true&multiline=true").log("Route End");
    }
}
