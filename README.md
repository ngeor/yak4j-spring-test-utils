# yak4j-spring-test-utils

Testing utilities for Spring

[![Maven Central](https://img.shields.io/maven-central/v/com.github.ngeor/yak4j-spring-test-utils.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.ngeor%22%20AND%20a:%22yak4j-spring-test-utils%22)
[![Java CI with Maven](https://github.com/ngeor/yak4j-spring-test-utils/actions/workflows/maven.yml/badge.svg)](https://github.com/ngeor/yak4j-spring-test-utils/actions/workflows/maven.yml)
[![javadoc](https://javadoc.io/badge2/com.github.ngeor/yak4j-spring-test-utils/javadoc.svg)](https://javadoc.io/doc/com.github.ngeor/yak4j-spring-test-utils)

**Archived project!**
This project is **archived** and will be **removed**.
It has moved [here](https://github.com/ngeor/kamino/tree/master/libs/yak4j-spring-test-utils).

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
