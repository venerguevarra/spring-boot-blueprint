package com.starter.service.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.starter.service.ApplicationConfig;

@Component
@Path("/__gtg")
public class GtgResource {
    @Autowired
    private ApplicationConfig config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response gtg() {
        final GtgResponse gtgResponse = new GtgResponse(config.getMode(), GtgResponse.OK, config.getVersion());
        return Response.ok(gtgResponse).status(Response.Status.OK).build();
    }
}
