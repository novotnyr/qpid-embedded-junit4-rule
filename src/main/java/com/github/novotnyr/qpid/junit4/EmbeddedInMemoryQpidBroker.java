package com.github.novotnyr.qpid.junit4;

import org.apache.qpid.server.SystemLauncher;
import org.apache.qpid.server.configuration.IllegalConfigurationException;
import org.apache.qpid.server.model.SystemConfig;
import org.slf4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class EmbeddedInMemoryQpidBroker implements AutoCloseable {
    public static final Logger logger = getLogger(EmbeddedInMemoryQpidBroker.class);

    private static final String DEFAULT_INITIAL_CONFIGURATION_LOCATION = "qpid-configuration.json";

    private boolean startupLoggedToSystemOut = true;

    private String initialConfigurationLocation = DEFAULT_INITIAL_CONFIGURATION_LOCATION;

    private SystemLauncher systemLauncher;

    public EmbeddedInMemoryQpidBroker() {
        this.systemLauncher = new SystemLauncher();
    }

    public void start() throws Exception {
        this.systemLauncher.startup(createSystemConfig());
    }

    public void shutdown() {
        this.systemLauncher.shutdown();
    }

    @Override
    public void close() throws Exception {
        shutdown();
    }

    private Map<String, Object> createSystemConfig() throws IllegalConfigurationException {
        Map<String, Object> attributes = new HashMap<>();
        URL initialConfig = EmbeddedInMemoryQpidBroker.class.getClassLoader().getResource(this.initialConfigurationLocation);
        if (initialConfig == null) {
            throw new IllegalConfigurationException("Configuration location '" + this.initialConfigurationLocation + "' not found");
        }
        attributes.put(SystemConfig.TYPE, "Memory");
        attributes.put(SystemConfig.INITIAL_CONFIGURATION_LOCATION, initialConfig.toExternalForm());
        attributes.put(SystemConfig.STARTUP_LOGGED_TO_SYSTEM_OUT, this.startupLoggedToSystemOut);
        return attributes;
    }

    public void setInitialConfigurationLocation(String initialConfigurationLocation) {
        this.initialConfigurationLocation = initialConfigurationLocation;
    }

    public void setStartupLoggedToSystemOut(boolean startupLoggedToSystemOut) {
        this.startupLoggedToSystemOut = startupLoggedToSystemOut;
    }

    public EmbeddedInMemoryQpidBroker withInitialConfigurationLocation(String initialConfigurationLocation) {
        setInitialConfigurationLocation(initialConfigurationLocation);
        return this;
    }

    public EmbeddedInMemoryQpidBroker withStartupLoggedToSystemOut(boolean enabled) {
        setStartupLoggedToSystemOut(enabled);
        return this;
    }

    public static void main(String[] args) {
        try(EmbeddedInMemoryQpidBroker broker = new EmbeddedInMemoryQpidBroker()) {
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}