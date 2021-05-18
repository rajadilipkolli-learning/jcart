package com.sivalabs.jcart.admin.web.controllers;

import com.sivalabs.jcart.admin.web.utils.HeaderTitleConstants;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.security.SecurityService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author rajakolli
 *
 */
@WebMvcTest(PermissionController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PermissionControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private SecurityService securityService;

	private PermissionController permissionController;

	private final String name = "JUNIT Name";

	@BeforeAll
	public void setUp() {
		Permission permission = new Permission();
		permission.setName(name);
		String description = "JUNIT Description";
		permission.setDescription(description);
		List<Permission> permissionList = Collections.singletonList(permission);
		when(securityService.getAllPermissions()).thenReturn(permissionList);
		permissionController = new PermissionController(securityService);
	}

	/**
	 * Test method for
	 * {@link com.sivalabs.jcart.admin.web.controllers.PermissionController#getHeaderTitle()}.
	 */
	@Test
	public void testGetHeaderTitle() {
		String headerTitle = permissionController.getHeaderTitle();
		assertThat(headerTitle).isNotNull();
		assertThat(HeaderTitleConstants.PERMISSIONTITLE).isEqualTo(headerTitle);
	}

	/**
	 * Test method for
	 * {@link com.sivalabs.jcart.admin.web.controllers.PermissionController#listPermissions(org.springframework.ui.Model)}.
	 */
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "MANAGE_PERMISSIONS" })
	public void testListPermissions() throws Exception {
		this.mockMvc.perform(get("/permissions")).andExpect(status().isOk())
				.andExpect(content().string(containsString(name)));
	}

	@Test
	@WithAnonymousUser
	public void testListPermissionsWithAnonymousUser() throws Exception {
		this.mockMvc.perform(get("/permissions")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "MANAGE_PERMISSIONS" })
	public void testIsLoggedIn() {
		assertThat(AbstractJCartAdminController.isLoggedIn()).isFalse();
	}

}
