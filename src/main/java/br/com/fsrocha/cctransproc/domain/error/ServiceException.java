package br.com.fsrocha.cctransproc.domain.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final transient Object customPayload;

    public ServiceException(HttpStatus httpStatus, String message, String errorCode, Throwable cause, Object customPayload) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.customPayload = customPayload;
    }

    public ServiceException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null, null, null);
    }

    public ServiceException(HttpStatus httpStatus, String message, String errorCode) {
        this(httpStatus, message, errorCode, null, null);
    }

    public ServiceException(HttpStatus httpStatus, String message, Object customPayload) {
        this(httpStatus, message, null, null, customPayload);
    }

    public ServiceException(HttpStatus httpStatus, String message, Throwable cause) {
        this(httpStatus, message, null, cause, null);
    }

}
