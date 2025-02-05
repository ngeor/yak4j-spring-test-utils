package com.github.ngeor.yak4j;

import java.net.URI;
import java.util.List;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

/**
 * Assertions for {@link RequestEntity}.
 *
 * @param <T> The body type.
 * @since 0.12.0
 */
public class RequestEntityAssert<T> extends AbstractAssert<RequestEntityAssert<T>, RequestEntity<T>> {
    RequestEntityAssert(RequestEntity<T> requestEntity) {
        super(requestEntity, RequestEntityAssert.class);
    }

    /**
     * Verifies that the request entity's actual HTTP method is equal to the given one.
     */
    public RequestEntityAssert<T> hasMethod(HttpMethod httpMethod) {
        isNotNull();
        HttpMethod actualMethod = actual.getMethod();
        Assertions.assertThat(actualMethod)
                .withFailMessage("Expecting request entity method to be %s but was %s", httpMethod, actualMethod)
                .isNotNull()
                .isEqualTo(httpMethod);
        return this;
    }

    /**
     * Verifies that the request entity's actual url is equal to the given one.
     */
    public RequestEntityAssert<T> hasUrl(URI url) {
        isNotNull();
        URI actualUrl = actual.getUrl();
        Assertions.assertThat(actualUrl)
                .withFailMessage("Expecting request entity url to be %s but was %s", url, actualUrl)
                .isNotNull()
                .isEqualTo(url);
        return this;
    }

    /**
     * Verifies that the request entity's actual url is equal to the given one.
     */
    public RequestEntityAssert<T> hasUrl(String url) {
        isNotNull();
        URI actualUrl = actual.getUrl();
        Assertions.assertThat(actualUrl)
                .withFailMessage("Expecting request entity url to be %s but was %s", url, actualUrl)
                .isNotNull()
                .isEqualTo(URI.create(url));
        return this;
    }

    /**
     * Verifies that the request entity's actual body is equal to the given one.
     */
    public RequestEntityAssert<T> hasBody(T body) {
        isNotNull();
        T actualBody = actual.getBody();
        Assertions.assertThat(actualBody)
                .withFailMessage("Expecting request entity body to be %s but was %s", body, actualBody)
                .isNotNull()
                .isEqualTo(body);
        return this;
    }

    /**
     * Verifies that the request entity's actual Accept header is equal to the given one.
     */
    public RequestEntityAssert<T> hasAcceptHeader(MediaType mediaType) {
        isNotNull();
        HttpHeaders httpHeaders = actual.getHeaders();
        List<MediaType> actualAccept = httpHeaders.getAccept();
        Assertions.assertThat(actualAccept)
                .withFailMessage("Expecting request entity Accept header to be %s but was %s", mediaType, actualAccept)
                .containsExactly(mediaType);
        return this;
    }

    /**
     * Verifies that the request entity's Accept header is equal to {@code application/json}.
     *
     * @see MediaType#APPLICATION_JSON
     */
    public RequestEntityAssert<T> hasJsonAcceptHeader() {
        return hasAcceptHeader(MediaType.APPLICATION_JSON);
    }

    /**
     * Verifies that the request entity's Content-Type header is equal to the given one.
     */
    public RequestEntityAssert<T> hasContentTypeHeader(MediaType mediaType) {
        isNotNull();
        HttpHeaders httpHeaders = actual.getHeaders();
        MediaType actualContentType = httpHeaders.getContentType();
        Assertions.assertThat(actualContentType)
                .withFailMessage(
                        "Expecting request entity Content-Type header to be %s but was %s",
                        mediaType, actualContentType)
                .isEqualTo(mediaType);
        return this;
    }

    /**
     * Verifies that the request entity's Content-Type header is equal to {@code application/json}.
     *
     * @see MediaType#APPLICATION_JSON
     */
    public RequestEntityAssert<T> hasJsonContentTypeHeader() {
        return hasContentTypeHeader(MediaType.APPLICATION_JSON);
    }

    /**
     * Verifies that the request entity's Accept and and Content-Type headers are both equal to
     * {@code application/json}.
     *
     * @see MediaType#APPLICATION_JSON
     */
    public RequestEntityAssert<T> hasJsonAcceptAndContentTypeHeaders() {
        return hasJsonAcceptHeader().hasJsonContentTypeHeader();
    }

    /**
     * Verifies that the request entity's Authorization header is equal to the given one.
     */
    public RequestEntityAssert<T> hasAuthorizationHeader(String value) {
        isNotNull();
        HttpHeaders httpHeaders = actual.getHeaders();
        List<String> actualAuthorization = httpHeaders.get("Authorization");
        Assertions.assertThat(actualAuthorization)
                .withFailMessage(
                        "Expecting request entity Authorization header to be %s but was %s", value, actualAuthorization)
                .containsExactly(value);
        return this;
    }
}
