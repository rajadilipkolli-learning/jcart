
package com.sivalabs.jcart.admin.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;
import com.sivalabs.jcart.security.SecurityService;

/**
 * @author rajakolli
 *
 */
@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceTest {

	@MockBean
	private SecurityService securityService;

	private CustomUserDetailsService customUserDetailsService;

	private String email = "JUNIT_EMAIL";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		customUserDetailsService = new CustomUserDetailsService(securityService);
	}

	@Test
	void testLoadUserByUsername() {
		User user = new User();
		user.setEmail(email);
		user.setPassword("passwrd");
		Role role = new Role();
		role.setName("JUNIT_ROLE");
		Permission permission = new Permission();
		permission.setName("JUNIT_PERMISSION");
		List<Permission> permissions = Collections.singletonList(permission);
		role.setPermissions(permissions);
		List<Role> roles = Collections.singletonList(role);
		user.setRoles(roles);
		given(securityService.findUserByEmail(email)).willReturn(Optional.of(user));
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
		assertThat(userDetails.getUsername()).isEqualTo(email);
		assertThat(userDetails.getPassword()).isEqualTo("passwrd");
	}

	@Test
	void testLoadUserByEmail() {
		given(securityService.findUserByEmail(email)).willReturn(Optional.empty());
		assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(email))
				.hasMessage("Email JUNIT_EMAIL not found").isInstanceOf(UsernameNotFoundException.class);
	}

}
