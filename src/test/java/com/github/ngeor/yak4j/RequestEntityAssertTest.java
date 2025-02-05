package com.github.ngeor.yak4j;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

/**
 * Unit tests for {@link RequestEntityAssert}.
 */
class RequestEntityAssertTest {
    @Test
    void hasMethod_pass() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        Assertions.assertThat(requestEntity).hasMethod(HttpMethod.POST);
    }

    @Test
    void hasMethod_fail() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasMethod(HttpMethod.GET))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity method to be GET but was POST");
    }

    @Test
    void hasUrl_pass() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        Assertions.assertThat(requestEntity).hasUrl(URI.create("http://localhost/"));
    }

    @Test
    void hasUrl_fail() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasUrl(URI.create("http://localhost:8080/")))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity url to be http://localhost:8080/ but was http://localhost/");
    }

    @Test
    void hasUrl_asString_pass() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        Assertions.assertThat(requestEntity).hasUrl("http://localhost/");
    }

    @Test
    void hasUrl_asString_fail() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasUrl("http://localhost:8080/"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity url to be http://localhost:8080/ but was http://localhost/");
    }

    @Test
    void hasBody_pass() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        Assertions.assertThat(requestEntity).hasBody("hello world");
    }

    @Test
    void hasBody_fail() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasBody("hello, world!"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity body to be hello, world! but was hello world");
    }

    @Test
    void hasAcceptHeader_pass() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_JSON)
                .body("hello world");
        Assertions.assertThat(requestEntity).hasAcceptHeader(MediaType.APPLICATION_JSON);
    }

    @Test
    void hasAcceptHeader_fail() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_JSON)
                .body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasAcceptHeader(MediaType.APPLICATION_PDF))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity Accept header to be application/pdf but was [application/json]");
    }

    @Test
    void hasAcceptHeader_fail_noHeader() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasAcceptHeader(MediaType.APPLICATION_PDF))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity Accept header to be application/pdf but was []");
    }

    @Test
    void hasAcceptHeader_fail_twoHeaderValues() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML)
                .body("hello world");
        String message =
                "Expecting request entity Accept header to be application/json but was [application/json, text/html]";
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasAcceptHeader(MediaType.APPLICATION_JSON))
                .isInstanceOf(AssertionError.class)
                .hasMessage(message);
    }

    @Test
    void hasJsonAcceptHeader_pass() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_JSON)
                .body("hello world");
        Assertions.assertThat(requestEntity).hasJsonAcceptHeader();
    }

    @Test
    void hasJsonAcceptHeader_fail() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_XML)
                .body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasJsonAcceptHeader())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity Accept header to be application/json but was [application/xml]");
    }

    @Test
    void hasContentTypeHeader_pass() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .contentType(MediaType.APPLICATION_JSON)
                .body("hello world");
        Assertions.assertThat(requestEntity).hasContentTypeHeader(MediaType.APPLICATION_JSON);
    }

    @Test
    void hasContentTypeHeader_fail() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .contentType(MediaType.APPLICATION_JSON)
                .body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasContentTypeHeader(MediaType.APPLICATION_PDF))
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "Expecting request entity Content-Type header to be application/pdf but was application/json");
    }

    @Test
    void hasJsonContentTypeHeader_pass() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .contentType(MediaType.APPLICATION_JSON)
                .body("hello world");
        Assertions.assertThat(requestEntity).hasJsonContentTypeHeader();
    }

    @Test
    void hasJsonContentTypeHeader_fail() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .contentType(MediaType.APPLICATION_XML)
                .body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasJsonContentTypeHeader())
                .isInstanceOf(AssertionError.class)
                .hasMessage(
                        "Expecting request entity Content-Type header to be application/json but was application/xml");
    }

    @Test
    void hasJsonAcceptAndContentTypeHeaders_pass() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body("hello world");
        Assertions.assertThat(requestEntity).hasJsonAcceptAndContentTypeHeaders();
    }

    @Test
    void hasJsonAcceptAndContentTypeHeaders_fail_wrongAccept() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_XML)
                .contentType(MediaType.APPLICATION_JSON)
                .body("hello world");
        String message = "Expecting request entity Accept header to be application/json but was [application/xml]";
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasJsonAcceptAndContentTypeHeaders())
                .isInstanceOf(AssertionError.class)
                .hasMessage(message);
    }

    @Test
    void hasJsonAcceptAndContentTypeHeaders_fail_wrongContentType() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_XML)
                .body("hello world");
        String message = "Expecting request entity Content-Type header to be application/json but was application/xml";
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasJsonAcceptAndContentTypeHeaders())
                .isInstanceOf(AssertionError.class)
                .hasMessage(message);
    }

    @Test
    void hasAuthorizationHeader_pass() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .header("Authorization", "Basic 1234")
                .body("hello world");
        Assertions.assertThat(requestEntity).hasAuthorizationHeader("Basic 1234");
    }

    @Test
    void hasAuthorizationHeader_fail() {
        RequestEntity<String> requestEntity = RequestEntity.post(URI.create("http://localhost/"))
                .header("Authorization", "Basic 1234")
                .body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasAuthorizationHeader("Basic abcd"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity Authorization header to be Basic abcd but was [Basic 1234]");
    }

    @Test
    void hasAuthorizationHeader_fail_noHeader() {
        RequestEntity<String> requestEntity =
                RequestEntity.post(URI.create("http://localhost/")).body("hello world");
        assertThatThrownBy(() -> Assertions.assertThat(requestEntity).hasAuthorizationHeader("Basic abcd"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Expecting request entity Authorization header to be Basic abcd but was null");
    }
}
