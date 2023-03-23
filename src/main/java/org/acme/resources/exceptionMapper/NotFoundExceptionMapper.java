package org.acme.resources.exceptionMapper;

import org.acme.dto.response.ExceptionResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException notFoundException) {
        ExceptionResponse exception = new ExceptionResponse();
        exception.setMensagem(notFoundException.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(exception).build();
    }
}
