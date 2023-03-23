package org.acme.resources.exceptionMapper;

import org.acme.dto.response.ExceptionResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException badReqException) {
        ExceptionResponse nossaExcecao = new ExceptionResponse();
        nossaExcecao.setMensagem(badReqException.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(nossaExcecao).build();
    }
}
