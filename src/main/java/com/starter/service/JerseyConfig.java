package com.starter.service;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

import com.starter.service.resource.GtgResource;
import com.starter.service.resource.RamlResource;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(GtgResource.class);
        register(RamlResource.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }

}
