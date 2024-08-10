package br.com.fsrocha.cctransproc.domain.error;

import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

@UtilityClass
public class ErrorResolver {

    public ErrorResponse resolveServiceException(Exception ex, HttpStatusCode httpStatusCode) {
        var errorResponse = init(ex, httpStatusCode);

        if (ex instanceof ServiceException serviceException) {
            errorResponse.setErrorCode(serviceException.getErrorCode());
            errorResponse.setCustomPayload(serviceException.getCustomPayload());
            return errorResponse;
        }

        return errorResponse;
    }

    public static HttpStatus resolveStatus(Exception ex) {
        HttpStatus status = null;
        if (ex instanceof ServiceException serviceException) {
            status = serviceException.getHttpStatus();
        }
        return status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ErrorResponse init(Exception ex, HttpStatusCode httpStatus) {
        String errorMessage;
        String reason = getReasonName(httpStatus);

        if (ex instanceof MethodArgumentNotValidException validException) {
            errorMessage = validException.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .findFirst()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .orElse("Invalid payload");
            return new ErrorResponse(reason, errorMessage);
        }

        errorMessage = Objects.requireNonNullElse(findErrorMessage(ex), "Internal server error");
        return new ErrorResponse(reason, errorMessage);
    }

    private String findErrorMessage(Exception ex) {
        if (ex.getCause() != null) {
            return getRootException(ex).getLocalizedMessage();
        }
        return ex.getMessage();
    }

    private Throwable getRootException(Throwable ex) {
        Throwable rootException = ex;

        while (rootException.getCause() != null) {
            rootException = rootException.getCause();
        }

        return rootException;
    }

    private String getReasonName(HttpStatusCode httpStatusCode) {
        var reason = HttpStatus.resolve(httpStatusCode.value());
        return reason != null ? reason.name() : HttpStatus.INTERNAL_SERVER_ERROR.name();
    }

}
