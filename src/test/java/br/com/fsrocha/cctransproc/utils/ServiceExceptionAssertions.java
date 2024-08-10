package br.com.fsrocha.cctransproc.utils;

import br.com.fsrocha.cctransproc.domain.error.ServiceException;
import lombok.experimental.UtilityClass;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@UtilityClass
public class ServiceExceptionAssertions {
    public ServiceException catchThrowableServiceException(ThrowableAssert.ThrowingCallable shouldRaiseThrowable) {
        return ThrowableAssert.catchThrowableOfType(shouldRaiseThrowable, ServiceException.class);
    }

    public ServiceExceptionAssert assertThat(ServiceException actual) {
        return new ServiceExceptionAssert(actual);
    }

    public static class ServiceExceptionAssert extends AbstractThrowableAssert<ServiceExceptionAssert, ServiceException> {
        ServiceExceptionAssert(ServiceException actual) {
            super(actual, ServiceExceptionAssert.class);
        }

        public ServiceExceptionAssert hasCategory(HttpStatus httpStatus) {
            org.assertj.core.internal.Objects.instance().assertNotNull(info, actual);
            if (!Objects.equals(httpStatus, actual.getHttpStatus())) {
                failWithMessage("Excepted category tho be <%s>", httpStatus, actual.getHttpStatus());
            }
            return this;
        }
    }
}
