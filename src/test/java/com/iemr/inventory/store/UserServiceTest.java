package com.iemr.inventory.store;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserServiceTest {

	// @Autowired
	// private UserService userService;
	//
	// @Autowired
	// private NameService nameService;
	//

	@Before
	public void before() {
		System.out.println("Before");
	}

	@After
	public void after() {
		System.out.println("After");
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("Before Class");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("After Class");
	}

	@Test
	public void sum_with3numbers() {
		System.out.println("Test1");
		assertEquals(6, 6);
	}

}