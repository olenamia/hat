package com.mialyk.dbseeder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mialyk.dbseeder.configuration.DbSeederConfig;

@SpringBootTest(classes = {DbSeederApplication.class, DbSeederConfig.class})
class DbSeederApplicationTests {

	@Test
	void contextLoads() {
	}

}
