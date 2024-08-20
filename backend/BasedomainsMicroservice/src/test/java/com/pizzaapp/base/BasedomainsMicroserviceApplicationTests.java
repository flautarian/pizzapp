package com.pizzaapp.base;

import com.pizzapp.base.BasedomainsMicroserviceApplication;
import com.pizzapp.base.dto.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = BasedomainsMicroserviceApplication.class)
class BasedomainsMicroserviceApplicationTests {

	@Test
	void contextLoads() {
		Order order = new Order("orderId", "name", 1, 1f);
		assertNotEquals(null, order);
	}

}
