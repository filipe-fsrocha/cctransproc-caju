package br.com.fsrocha.cctransproc.domain.error;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ErrorResolverTest {

    @Test
    void testResolveGenericException() {
        // Assemble
        HttpStatusCode httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResolver.resolveServiceException(new Exception("Internal server error"), httpStatusCode);

        // Assert
        assertNotNull(response);
        assertEquals("INTERNAL_SERVER_ERROR", response.getReason());
        assertEquals("Internal server error", response.getMessage());
        assertNull(response.getErrorCode());
        assertNull(response.getCustomPayload());
    }

    @Test
    void testResolveServiceException() {
        // Assemble
        HttpStatusCode httpStatusCode = HttpStatus.FORBIDDEN;
        ErrorResponse response = ErrorResolver
                .resolveServiceException(new ServiceException(HttpStatus.FORBIDDEN, "Service Exception"), httpStatusCode);

        // Assert
        assertNotNull(response);
        assertEquals("FORBIDDEN", response.getReason());
        assertEquals("Service Exception", response.getMessage());
        assertNull(response.getErrorCode());
        assertNull(response.getCustomPayload());
    }

    @Test
    void testResolveNestedServiceException() {
        // Assemble
        Exception nestedException = new Exception("Root cause");
        ServiceException exception = new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", nestedException);

        // Act
        ErrorResponse response = ErrorResolver.resolveServiceException(exception, HttpStatus.INTERNAL_SERVER_ERROR);

        // Assert
        assertNotNull(response);
        assertEquals("INTERNAL_SERVER_ERROR", response.getReason());
        assertEquals("Root cause", response.getMessage());
        assertNull(response.getErrorCode());
        assertNull(response.getCustomPayload());
    }

    @Test
    void testResolveServiceExceptionWithHttpStatusNull() {
        // Assemble
        try (MockedStatic<HttpStatus> mockedHttpStatus = mockStatic(HttpStatus.class)) {
            mockedHttpStatus.when(() -> HttpStatus.resolve(400)).thenReturn(null);

            // Atc
            ErrorResponse response = ErrorResolver
                    .resolveServiceException(new ServiceException(HttpStatus.FORBIDDEN, "Service Exception"), HttpStatusCode.valueOf(403));

            // Assert
            assertNotNull(response);
            assertEquals("INTERNAL_SERVER_ERROR", response.getReason());
            assertEquals("Service Exception", response.getMessage());
            assertNull(response.getErrorCode());
            assertNull(response.getCustomPayload());
        }
    }

    @Test
    void testResolveExceptionMethodArgumentValidation() {
        // Assemble
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.getAllErrors())
                .thenReturn(List.of(new ObjectError("object", "Message validation")));
        var methodArgumentNotValidException = new MethodArgumentNotValidException(null, mockBindingResult);

        // Act
        var response = ErrorResolver.resolveServiceException(methodArgumentNotValidException, HttpStatusCode.valueOf(400));

        // Assert
        assertNotNull(response);
        assertEquals("BAD_REQUEST", response.getReason());
        assertEquals("Message validation", response.getMessage());
        assertNull(response.getErrorCode());
        assertNull(response.getCustomPayload());
    }

    @Test
    void testResolveHttpStatusException() {
        // Assemble
        HttpStatus status = ErrorResolver
                .resolveStatus(new ServiceException(HttpStatus.FORBIDDEN, "Service Exception"));

        // Assert
        assertNotNull(status);
        assertEquals("FORBIDDEN", status.name());
    }

    @Test
    void testResolveHttpStatusExceptionWithNullStatus() {
        // Assemble
        HttpStatus status = ErrorResolver
                .resolveStatus(new ServiceException(null, "Service Exception"));

        // Assert
        assertNotNull(status);
        assertEquals("INTERNAL_SERVER_ERROR", status.name());
    }
}