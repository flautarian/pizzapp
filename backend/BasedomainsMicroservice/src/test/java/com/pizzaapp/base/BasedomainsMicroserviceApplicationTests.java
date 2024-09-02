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
		PizzaDto pizzaDto = new PizzaDto("46f5da39-91fe-4b48-85df-0c818b38638a", "customer", "L", "delivery address", "PLACED", "email@gmail.com", "Margherita",new ArrayList<>(), 10.50f);
		assertNotEquals(null, pizzaDto);
	}

}
