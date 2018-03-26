package com.spothero.challenge.exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper < BadRequestException > {
    @Override
    public Response toResponse(BadRequestException exception) {
        return Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(exception.getMessage()).build();
    }
}