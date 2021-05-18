package com.sivalabs.jcart.admin.web.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.sivalabs.jcart.admin.web.models.ProductForm;
import com.sivalabs.jcart.admin.web.utils.HeaderTitleConstants;
import com.sivalabs.jcart.admin.web.validators.ProductFormValidator;
import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = { ProductController.class })
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CatalogService catalogService;

	@MockBean
	private ProductFormValidator productFormValidator;

	ProductController productController;

	private final String name = "Product Name";

	Product product = new Product();

	@BeforeAll
	public void setUp() {

		Category category = new Category();
		category.setDisplayOrder(1);
		List<Category> categoryList = Collections.singletonList(category);
		given(catalogService.getAllCategories()).willReturn(categoryList);
		product.setName(name);
		product.setSku("P101");
		String description = "Product Description";
		product.setDescription(description);
		product.setDisabled(false);
		List<Product> productList = Collections.singletonList(product);
		given(catalogService.getAllProducts()).willReturn(productList);
		productController = new ProductController(catalogService, productFormValidator);
	}

	@Test
	public void testGetHeaderTitle() {
		String headerTitle = productController.getHeaderTitle();
		Assertions.assertNotNull(headerTitle);
		Assertions.assertEquals(HeaderTitleConstants.PRODUCTTITLE, headerTitle);
	}

	/**
	 * Test method for
	 * {@link com.sivalabs.jcart.admin.web.controllers.ProductController#categoriesList()}.
	 * @throws Exception
	 */
	@Test
	@WithAnonymousUser
	public void testListProductsWithOutSecurity() throws Exception {
		this.mockMvc.perform(get("/products")).andExpect(status().isFound());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
	public void testListProducts() throws Exception {
		this.mockMvc.perform(get("/products")).andExpect(status().isOk());
//				.andExpect(content().string(containsString(name)));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
	public void testCreateProductForm() throws Exception {
		this.mockMvc.perform(get("/products/new"))
				.andExpect(view().name("products/create_product"))
				.andExpect(status().isOk());
	}

	@Test
	@Disabled
	@WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
	public void testCreateProductValidationFails() throws Exception {
		this.mockMvc.perform(post("/products")).andExpect(status().isOk())
				.andExpect(view().name("products/create_product"))
				.andExpect(model().attributeHasFieldErrors("product",
						new String[] { "categoryId", "sku", "name", "price" }));
	}

	@Test
	@Disabled
	@WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
	public void testCreateProductValidationPass() throws Exception {
		product.setId(1);
		when(catalogService.createProduct(any())).thenReturn(product);
		this.mockMvc
				.perform(post("/products").param("categoryId", "1").param("sku", "P0101")
						.param("name", "product").param("price", "250.00"))
				.andExpect(flash().attribute("info",
						equalTo("Product created successfully")))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/products"))
				.andExpect(redirectedUrl("/products"));
	}

	@Test
	public void testSaveProductImageToDisk() throws IOException {
		String filePath = this.getClass().getClassLoader().getResource("quilcart.png")
				.getPath();
		FileInputStream content = new FileInputStream(filePath);
		MockMultipartFile image = new MockMultipartFile("quilcart", "quilcart.png",
				"multipart/form-data", content);
		ProductForm productForm = new ProductForm();
		productForm.setId(10);
		productForm.setImage(image);
		productController.saveProductImageToDisk(productForm);
		HttpServletResponse response = new MockHttpServletResponse();
		productController.showProductImage(String.valueOf(productForm.getId()), null,
				response);
		Assertions.assertEquals("image/jpg", response.getContentType());
	}

}
