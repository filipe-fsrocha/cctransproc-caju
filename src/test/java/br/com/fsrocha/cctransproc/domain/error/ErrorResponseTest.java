package br.com.fsrocha.cctransproc.domain.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void testDefaultConstructor() {
        // Act
        ErrorResponse errorResponse = new ErrorResponse();

        // Assert
        assertThat(errorResponse.getReason()).isNull();
        assertThat(errorResponse.getErrorCode()).isNull();
        assertThat(errorResponse.getMessage()).isNull();
        assertThat(errorResponse.getCustomPayload()).isNull();
    }

    @Test
    void testConstructorWithMessage() {
        // Act
        ErrorResponse errorResponse = new ErrorResponse("Message");

        // Assert
        assertThat(errorResponse.getReason()).isNull();
        assertThat(errorResponse.getErrorCode()).isNull();
        assertThat(errorResponse.getMessage()).isEqualTo("Message");
        assertThat(errorResponse.getCustomPayload()).isNull();
    }

    @Test
    void testConstructorWithReasonAndMessage() {
        // Act
        ErrorResponse errorResponse = new ErrorResponse("REASON_123", "Error message");

        // Assert
        assertThat(errorResponse.getReason()).isEqualTo("REASON_123");
        assertThat(errorResponse.getMessage()).isEqualTo("Error message");
        assertThat(errorResponse.getErrorCode()).isNull();
        assertThat(errorResponse.getCustomPayload()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        // Act
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setReason("REASON_123");
        errorResponse.setErrorCode("ERROR_CODE_456");
        errorResponse.setMessage("Error message");
        errorResponse.setCustomPayload("Some custom payload");

        // Assert
        assertThat(errorResponse.getReason()).isEqualTo("REASON_123");
        assertThat(errorResponse.getErrorCode()).isEqualTo("ERROR_CODE_456");
        assertThat(errorResponse.getMessage()).isEqualTo("Error message");
        assertThat(errorResponse.getCustomPayload()).isEqualTo("Some custom payload");
    }
}
