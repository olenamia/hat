package com.mialyk.dbseeder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mialyk.dbseeder.configuration.DbseederConfig;

@SpringBootTest(classes = {DbseederApplication.class, DbseederConfig.class})
class DbseederApplicationTests {

	@Test
	void contextLoads() {
	}

}
