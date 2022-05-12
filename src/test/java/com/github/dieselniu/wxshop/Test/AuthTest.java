package com.github.dieselniu.wxshop.Test;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthTest extends IntegrationTest {


	@Test
	void test() {
		TestResponse testResponse = get("/api/niu");
		assertThat(testResponse.statusCode()).isEqualTo(HttpStatus.OK);
		assertThat(testResponse.<String>value("$")).isEqualTo("ok");
	}
}
