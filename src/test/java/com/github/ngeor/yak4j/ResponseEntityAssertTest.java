package com.github.ngeor.yak4j;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test for {@link ResponseEntityAssert}.
 */
class ResponseEntityAssertTest {

    @Test
    void hasBody() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("hello world", HttpStatus.OK);
        Assertions.assertThat(responseEntity).hasBody("hello world");
    }

    @Test
    void hasNotNullBody_pass() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("hello world", HttpStatus.OK);
        Assertions.assertThat(responseEntity).hasNotNullBody();
    }

    @Test
    void hasNotNullBody_fail() {
        ResponseEntity<?> responseEntity = ResponseEntity.ok().build();
        assertThatThrownBy(() -> Assertions.assertThat(responseEntity).hasNotNullBody())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting response entity body to not be null");
    }

    @Test
    void hasBody_consumer_pass() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("hello world", HttpStatus.OK);
        Assertions.assertThat(responseEntity).hasBody(body -> assertThat(body).isEqualTo("hello world"));
    }

    @Test
    void hasBody_consumer_fail() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("hello world", HttpStatus.OK);
        assertThatThrownBy(() -> Assertions.assertThat(responseEntity)
                        .hasBody(body -> assertThat(body).isEqualTo("goodbye world")))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void hasStatus() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        Assertions.assertThat(responseEntity).hasStatus(HttpStatus.I_AM_A_TEAPOT);
    }

    @Test
    void isBadRequest() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity).isBadRequest();
    }

    @Test
    void isConflict() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        Assertions.assertThat(responseEntity).isConflict();
    }

    @Test
    void isCreated() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        Assertions.assertThat(responseEntity).isCreated();
    }

    @Test
    void isForbidden() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Assertions.assertThat(responseEntity).isForbidden();
    }

    @Test
    void isInternalServerError() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(responseEntity).isInternalServerError();
    }

    @Test
    void isNotFound() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity).isNotFound();
    }

    @Test
    void isOk() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Assertions.assertThat(responseEntity).isOk();
    }

    @Test
    void isUnauthorized() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Assertions.assertThat(responseEntity).isUnauthorized();
    }
}
