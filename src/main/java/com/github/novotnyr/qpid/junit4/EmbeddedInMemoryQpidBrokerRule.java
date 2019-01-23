package com.github.novotnyr.qpid.junit4;

import org.junit.rules.ExternalResource;

import java.net.URL;

public class EmbeddedInMemoryQpidBrokerRule extends ExternalResource {
    private EmbeddedInMemoryQpidBroker broker;

    private URL configurationLocation;

    private boolean startupLoggedToSystemOut = false;

    @Override
    protected void before() throws Throwable {
        this.broker = new EmbeddedInMemoryQpidBroker();
        this.broker.setStartupLoggedToSystemOut(false);
        this.broker.setInitialConfigurationLocation(this.configurationLocation);
        this.broker.start();
    }

    @Override
    protected void after() {
        this.broker.shutdown();
    }

    public void setConfigurationLocation(URL configurationLocation) {
        this.configurationLocation = configurationLocation;
    }

    public void setStartupLoggedToSystemOut(boolean startupLoggedToSystemOut) {
        this.startupLoggedToSystemOut = startupLoggedToSystemOut;
    }

    public URL configurationLocation() {
        return this.configurationLocation;
    }

    public boolean startupLoggedToSystemOut() {
        return this.startupLoggedToSystemOut;
    }

    public EmbeddedInMemoryQpidBrokerRule withConfigurationLocation(URL configurationLocation) {
        this.configurationLocation = configurationLocation;
        return this;
    }

    public EmbeddedInMemoryQpidBrokerRule withStartupLoggedToSystemOut(boolean startupLoggedToSystemOut) {
        this.startupLoggedToSystemOut = startupLoggedToSystemOut;
        return this;
    }
}
