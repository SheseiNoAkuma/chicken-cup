package eu.chicken.exception;

import io.quarkus.arc.ArcUndeclaredThrowableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ArcUndeclaredThrowableExceptionHandler implements ExceptionMapper<ArcUndeclaredThrowableException> {
    @Override
    public Response toResponse(ArcUndeclaredThrowableException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(GenericErrorHandler.ErrorEntity.fromException(e))
                .build();
    }
}
