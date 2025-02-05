package com.github.ngeor.yak4j;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Assertion DSL for {@link ResultActions}.
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public final class ResultActionsAssert extends AbstractAssert<ResultActionsAssert, ResultActions> {

    ResultActionsAssert(ResultActions resultActions) {
        super(resultActions, ResultActionsAssert.class);
    }

    private static boolean hasCode(String[] codes, String code) {
        if (codes == null) {
            return false;
        }

        return Arrays.asList(codes).contains(code);
    }

    /**
     * Checks that the MVC response contains the given validation errors and only these.
     *
     * @param invalidFieldExpectations A collection of expected invalid fields.
     * @return This instance.
     */
    public ResultActionsAssert containsValidationErrorsExactly(InvalidFieldExpectation... invalidFieldExpectations) {
        resolvedExceptionIsInstanceOf(MethodArgumentNotValidException.class);

        MvcResult mvcResult = actual.andReturn();
        Exception resolvedException = mvcResult.getResolvedException();
        MethodArgumentNotValidException e = (MethodArgumentNotValidException) resolvedException;

        BindingResult bindingResult = e.getBindingResult();
        Assertions.assertThat(bindingResult)
                .withFailMessage("Expecting not null binding result in resolved exception")
                .isNotNull();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        Assertions.assertThat(allErrors)
                .withFailMessage("Expecting exactly %d errors in: %s", invalidFieldExpectations.length, allErrors)
                .isNotNull()
                .hasSize(invalidFieldExpectations.length);

        for (InvalidFieldExpectation invalidFieldExpectation : invalidFieldExpectations) {
            String field = invalidFieldExpectation.getField();
            String code = invalidFieldExpectation.getCode();

            Assertions.assertThat(allErrors)
                    .withFailMessage("Expecting field %s with code %s in: %s", field, code, allErrors)
                    .anyMatch(err -> isExpectedField(err, field, code));
        }

        return this;
    }

    public ResultActionsAssert hasStatus(HttpStatus httpStatus) throws Exception {
        actual.andExpect(status().is(httpStatus.value()));
        return this;
    }

    public ResultActionsAssert isBadRequest() throws Exception {
        actual.andExpect(status().isBadRequest());
        return this;
    }

    public ResultActionsAssert isBadRequest(InvalidFieldExpectation... invalidFieldExpectations) throws Exception {
        return isBadRequest().containsValidationErrorsExactly(invalidFieldExpectations);
    }

    public ResultActionsAssert isConflict() throws Exception {
        actual.andExpect(status().isConflict());
        return this;
    }

    public ResultActionsAssert isCreated() throws Exception {
        actual.andExpect(status().isCreated());
        return this;
    }

    public ResultActionsAssert isForbidden() throws Exception {
        actual.andExpect(status().isForbidden());
        return this;
    }

    public ResultActionsAssert isInternalServerError() throws Exception {
        actual.andExpect(status().isInternalServerError());
        return this;
    }

    public ResultActionsAssert isNotFound() throws Exception {
        actual.andExpect(status().isNotFound());
        return this;
    }

    public ResultActionsAssert isOk() throws Exception {
        actual.andExpect(status().isOk());
        return this;
    }

    public ResultActionsAssert isUnauthorized() throws Exception {
        actual.andExpect(status().isUnauthorized());
        return this;
    }

    /**
     * Checks that the result has a resolved exception of the given class.
     *
     * @param exceptionClass The expected class of the resolved exception.
     * @return This instance.
     * @see MvcResult#getResolvedException
     */
    public ResultActionsAssert resolvedExceptionIsInstanceOf(Class<? extends Exception> exceptionClass) {
        Exception resolvedException = actual.andReturn().getResolvedException();
        Assertions.assertThat(resolvedException)
                .withFailMessage(
                        "Expecting resolved exception to be instance of %s: %s", exceptionClass, resolvedException)
                .isNotNull()
                .isInstanceOf(exceptionClass);
        return this;
    }

    private boolean isExpectedField(ObjectError objectError, String field, String code) {
        if (!(objectError instanceof FieldError)) {
            return false;
        }

        FieldError fieldError = (FieldError) objectError;
        if (!fieldError.getField().equals(field)) {
            return false;
        }

        return hasCode(fieldError.getCodes(), code);
    }
}
