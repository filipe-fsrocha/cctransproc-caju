package br.com.fsrocha.cctransproc.infrastructure.repository.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class PaginateUtils {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public static PageRequest doPaginate(int page, int size) {
        page = page >= 0 ? page : DEFAULT_PAGE;
        size = size > 0 ? size : DEFAULT_SIZE;
        return PageRequest.of(page, size);
    }

}
