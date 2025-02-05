package com.github.ngeor.yak4j;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Main entry point for the custom assertions of this package.
 */
@SuppressWarnings("WeakerAccess")
public final class Assertions {
    private Assertions() {}

    /**
     * Starts building assertions for the given result actions.
     */
    public static ResultActionsAssert assertThat(ResultActions resultActions) {
        return new ResultActionsAssert(resultActions);
    }

    /**
     * Starts building assertions for the given response entity.
     */
    public static <T> ResponseEntityAssert<T> assertThat(ResponseEntity<T> responseEntity) {
        return new ResponseEntityAssert<>(responseEntity);
    }

    /**
     * Starts building assertions for the given request entity.
     */
    public static <T> RequestEntityAssert<T> assertThat(RequestEntity<T> requestEntity) {
        return new RequestEntityAssert<T>(requestEntity);
    }
}
