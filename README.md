# yak4j-spring-test-utils

Testing utilities for Spring

[![Maven Central](https://img.shields.io/maven-central/v/com.github.ngeor/yak4j-spring-test-utils.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.github.ngeor/yak4j-spring-test-utils)
[![build](https://github.com/ngeor/yak4j-spring-test-utils/actions/workflows/build.yml/badge.svg)](https://github.com/ngeor/yak4j-spring-test-utils/actions/workflows/build.yml)
[![javadoc](https://javadoc.io/badge2/com.github.ngeor/yak4j-spring-test-utils/javadoc.svg)](https://javadoc.io/doc/com.github.ngeor/yak4j-spring-test-utils)

## Overview

yak shaving for Java: Testing utilities for Spring

The package offers custom assertions for easier testing with Spring. Assertions
are built on top of [assertJ](https://joel-costigliola.github.io/assertj/).

### RequestEntity

```java
import static com.github.ngeor.yak4j.Assertions.assertThat;

RequestEntity<String> requestEntity = RequestEntity
    .post(URI.create("http://localhost/"))
    .accept(MediaType.APPLICATION_JSON)
    .contentType(MediaType.APPLICATION_JSON)
    .header("Authorization", "Basic 1234")
    .body("hello world");

assertThat(requestEntity)
    .hasMethod(HttpMethod.POST)
    .hasUrl("http://localhost/")
    .hasJsonAcceptAndContentTypeHeaders()
    .hasAuthorizationHeader("Basic 1234")
    .hasBody("hello world");
```

### ResponseEntity

Checking of status and body:

```java
import static com.github.ngeor.yak4j.Assertions.assertThat;

ResponseEntity<String> responseEntity = ResponseEntity.ok("hello world");

assertThat(responseEntity)
    .isOk() // check status = 200
    .hasBody("hello world"); // check body
```

### ResultActions

Checking status:

```java
import static com.github.ngeor.yak4j.Assertions.assertThat;

ResultActions resultActions = mockMvc.perform(/* do the mock MVC call */);

assertThat(resultActions)
    .isOk() // check status = 200
```

Checking for validation errors:

```java
import static com.github.ngeor.yak4j.InvalidFieldExpectationBuilder.invalidField;

assertThat(resultActions)
    .isBadRequest( // check status = 400
        invalidField("name", "NotNull") // check these validation errors
    );
```
