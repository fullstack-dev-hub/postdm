package com.postdm.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class }) //직접 DB 구축 시, 제거 고려
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
