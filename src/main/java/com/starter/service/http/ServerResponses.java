package com.starter.service.http;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

public class ServerResponses {
    private static final String GENERIC_ERROR_MESSAGE = "server_error";
    private static final String CONFLICT_ERROR_MESSAGE = "resource_already_exist";
    private static final String NOT_FOUND_ERROR_MESSAGE = "resource_not_found";

    public static Response NOT_FOUND() {
        return Response.status(HttpServletResponse.SC_NOT_FOUND).entity(new ErrorResponse(NOT_FOUND_ERROR_MESSAGE)).build();
    }

    public static Response INTERNAL_SERVER_ERROR() {
        return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).entity(new ErrorResponse(GENERIC_ERROR_MESSAGE)).build();
    }

    public static Response INTERNAL_SERVER_ERROR(String errorMessage) {
        return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).entity(new ErrorResponse(errorMessage)).build();
    }

    public static Response OK() {
        return Response.ok().build();
    }

    public static Response OK(Object entity) {
        return Response.ok().entity(entity).build();
    }

    public static Response CONFLICT() {
        return Response.status(HttpServletResponse.SC_CONFLICT).entity(new ErrorResponse(CONFLICT_ERROR_MESSAGE)).build();
    }

    public static Response CREATED(Object entity) {
        return Response.status(HttpServletResponse.SC_CREATED).entity(entity).build();
    }

    public static Response BAD_REQUEST(Set<ValidationMessage> errorMessages) {
        return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity(new ValidationErrorResponse(errorMessages)).build();
    }
}
