/**
 *
 */
package com.sivalabs.jcart;

import com.sivalabs.jcart.admin.web.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Siva
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JCartAdminApplicationTest {

	@Autowired
	private HomeController homeController;

	@Test
	void contextLoads() {
		assertThat(homeController).isNotNull();
	}

}
