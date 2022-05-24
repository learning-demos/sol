package com.github.dieselniu.wxshop.Test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthTest extends IntegrationTest {
	@Test
	@Disabled
	void test() {
		TestResponse testResponse = get("/api/niu");
		assertThat(testResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

}
