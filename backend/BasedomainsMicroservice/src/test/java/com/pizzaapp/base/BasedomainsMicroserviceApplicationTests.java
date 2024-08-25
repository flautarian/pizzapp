package com.pizzaapp.base;

import com.pizzapp.base.BasedomainsMicroserviceApplication;
import com.pizzapp.base.dto.PizzaDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = BasedomainsMicroserviceApplication.class)
class BasedomainsMicroserviceApplicationTests {

	@Test
	void contextLoads() {
		PizzaDto pizzaDto = new PizzaDto("customer", "delivery address", "placed", "email@gmail.com",new ArrayList<>(), 10.50f);
		assertNotEquals(null, pizzaDto);
	}

}
