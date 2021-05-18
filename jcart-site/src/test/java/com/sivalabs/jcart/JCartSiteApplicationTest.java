/**
 *
 */
package com.sivalabs.jcart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sivalabs.jcart.site.web.controllers.HomeController;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@SpringBootTest(classes = JCartSiteApplication.class)
public class JCartSiteApplicationTest {

	@Autowired
	HomeController homeController;

	@Test
	public void contextLoads() {
		assertThat(homeController).isNotNull();
	}

}
