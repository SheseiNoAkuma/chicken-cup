package eu.chicken.exception;

import io.quarkus.runtime.util.ExceptionUtil;
import lombok.Builder;
import lombok.Data;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericErrorHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorEntity.fromException(e))
                .build();
    }

    @Data
    @Builder
    public static class ErrorEntity {
        private String code;
        private String message;

        public static ErrorEntity fromException(Throwable throwable) {
            return new ErrorEntity(throwable.getClass().toGenericString(), ExceptionUtil.getRootCause(throwable).getMessage());
        }
    }
}
