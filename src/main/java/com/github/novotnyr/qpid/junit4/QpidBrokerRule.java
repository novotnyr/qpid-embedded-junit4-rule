package com.github.novotnyr.qpid.junit4;

import org.junit.rules.ExternalResource;

public class QpidBrokerRule extends ExternalResource {
    private EmbeddedInMemoryQpidBroker broker;

    @Override
    protected void before() throws Throwable {
        this.broker = new EmbeddedInMemoryQpidBroker();
        this.broker.setStartupLoggedToSystemOut(false);
        this.broker.start();
    }

    @Override
    protected void after() {
        this.broker.shutdown();
    }
}
