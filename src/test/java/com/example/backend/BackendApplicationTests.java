package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CatMashApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void dummyTest() {
		assertEquals(2, 2, "Test pipeline");
	}

}
