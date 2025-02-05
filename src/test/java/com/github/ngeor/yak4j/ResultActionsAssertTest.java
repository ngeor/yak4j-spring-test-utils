package com.github.ngeor.yak4j;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Unit tests for {@link ResultActionsAssert}.
 */
class ResultActionsAssertTest {

    private static ResultActions withStatus(HttpStatus httpStatus) throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(httpStatus.value());

        MvcResult mvcResult = mock(MvcResult.class);
        when(mvcResult.getResponse()).thenReturn(response);

        return getResultActions(mvcResult);
    }

    private static ResultActions getResultActions(MvcResult mvcResult) throws Exception {
        ResultActions resultActions = mock(ResultActions.class);
        when(resultActions.andExpect(any(ResultMatcher.class))).thenAnswer((Answer<Void>) invocation -> {
            ((ResultMatcher) invocation.getArgument(0)).match(mvcResult);
            return null;
        });

        when(resultActions.andReturn()).thenReturn(mvcResult);
        return resultActions;
    }

    private static FieldError createFieldError(String field, String code) {
        return new FieldError("test", field, "", true, new String[] {code}, new Object[0], "cannot be " + code);
    }

    @Test
    void containsValidationErrorsExactly_singleField() throws Exception {
        MvcResult mvcResult = mock(MvcResult.class);
        mockMethodArgumentNotValidException(mvcResult, createFieldError("name", "Null.name"));

        ResultActions resultActions = getResultActions(mvcResult);

        Assertions.assertThat(resultActions)
                .containsValidationErrorsExactly(
                        InvalidFieldExpectationBuilder.invalidField("name").withCode("Null.name"));
    }

    @Test
    void containsValidationErrorsExactly_twoFields() throws Exception {
        MvcResult mvcResult = mock(MvcResult.class);
        mockMethodArgumentNotValidException(
                mvcResult, createFieldError("name", "Null.name"), createFieldError("age", "Min.age"));

        ResultActions resultActions = getResultActions(mvcResult);

        Assertions.assertThat(resultActions)
                .containsValidationErrorsExactly(
                        InvalidFieldExpectationBuilder.invalidField("name").withCode("Null.name"),
                        InvalidFieldExpectationBuilder.invalidField("age").withCode("Min.age"));
    }

    @Test
    void hasStatus() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.I_AM_A_TEAPOT);
        Assertions.assertThat(resultActions).hasStatus(HttpStatus.I_AM_A_TEAPOT);
    }

    @Test
    void isBadRequest() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(resultActions).isBadRequest();
    }

    @Test
    void isConflict() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.CONFLICT);
        Assertions.assertThat(resultActions).isConflict();
    }

    @Test
    void isCreated() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.CREATED);
        Assertions.assertThat(resultActions).isCreated();
    }

    @Test
    void isForbidden() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.FORBIDDEN);
        Assertions.assertThat(resultActions).isForbidden();
    }

    @Test
    void isInternalServerError() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(resultActions).isInternalServerError();
    }

    @Test
    void isNotFound() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.NOT_FOUND);
        Assertions.assertThat(resultActions).isNotFound();
    }

    @Test
    void isOk() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.OK);
        Assertions.assertThat(resultActions).isOk();
    }

    @Test
    void isUnauthorized() throws Exception {
        ResultActions resultActions = withStatus(HttpStatus.UNAUTHORIZED);
        Assertions.assertThat(resultActions).isUnauthorized();
    }

    private MethodArgumentNotValidException createMethodArgumentNotValidException(FieldError... fieldErrors) {
        MethodParameter parameter = new MethodParameter(getClass().getMethods()[0], -1);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(fieldErrors));

        return new MethodArgumentNotValidException(parameter, bindingResult);
    }

    @SuppressWarnings("UnusedReturnValue")
    private OngoingStubbing<Exception> mockMethodArgumentNotValidException(
            MvcResult mvcResult, FieldError... fieldErrors) {
        MethodArgumentNotValidException exception = createMethodArgumentNotValidException(fieldErrors);
        return when(mvcResult.getResolvedException()).thenReturn(exception);
    }
}
