package br.com.fsrocha.cctransproc.domain.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceExceptionTest {

    @Test
    void testShouldCreateServiceExceptionWithHttpStatusAndMessage() {
        // Assemble
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "OBJECT_NOT_FOUND";

        // Act
        ServiceException exception = new ServiceException(status, message);

        // Assert
        assertThat(exception.getHttpStatus()).isEqualTo(status);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrorCode()).isNull();
        assertThat(exception.getCustomPayload()).isNull();
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void testShouldCreateServiceExceptionWithHttpStatusAndMessageAndErrorCode() {
        // Assemble
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "OBJECT_NOT_FOUND";

        // Act
        ServiceException exception = new ServiceException(status, message, "404");

        // Assert
        assertThat(exception.getHttpStatus()).isEqualTo(status);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrorCode()).isEqualTo("404");
        assertThat(exception.getCustomPayload()).isNull();
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void testShouldCreateServiceExceptionWithHttpStatusMessageAndCustomPayload() {
        // Assemble
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "INTERNAL_SERVER_ERROR";
        Object payload = new Object();

        // Act
        ServiceException exception = new ServiceException(status, message, payload);

        // Assert
        assertThat(exception.getHttpStatus()).isEqualTo(status);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrorCode()).isNull();
        assertThat(exception.getCustomPayload()).isEqualTo(payload);
        assertThat(exception.getCause()).isNull();
    }

    @Test
    void testShouldCreateServiceExceptionWithHttpStatusMessageAndCause() {
        // Assemble
        HttpStatus status = HttpStatus.FORBIDDEN;
        String message = "FORBIDDEN";
        Throwable cause = new RuntimeException("Underlying cause");

        // Act
        ServiceException exception = new ServiceException(status, message, cause);

        // Assert
        assertThat(exception.getHttpStatus()).isEqualTo(status);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrorCode()).isNull();
        assertThat(exception.getCustomPayload()).isNull();
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void shouldCreateServiceExceptionWithAllParameters() {
        // Assemble
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        String message = "SERVICE_UNAVAILABLE";
        String errorCode = "ERR503";
        Throwable cause = new RuntimeException("Service downtime");
        Object payload = "Some custom payload";

        // Act
        ServiceException exception = new ServiceException(status, message, errorCode, cause, payload);

        // Assert
        assertThat(exception.getHttpStatus()).isEqualTo(status);
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrorCode()).isEqualTo(errorCode);
        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getCustomPayload()).isEqualTo(payload);
    }
}
