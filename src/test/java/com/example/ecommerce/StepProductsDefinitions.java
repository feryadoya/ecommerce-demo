package com.example.ecommerce;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.ecommerce.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EcommerceApplication.class)
@AutoConfigureMockMvc
public class StepProductsDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long lastCreatedProductId;

    @When("I create a product with name {string} and price {double}")
    public void i_create_a_product_with_name_and_price(String name, double price) throws Exception {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        String productJson = objectMapper.writeValueAsString(product);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andReturn();

        Product createdProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        lastCreatedProductId = createdProduct.getId();
    }

    @Then("I should be able to retrieve the product with name {string} and price {double}")
    public void i_should_be_able_to_retrieve_the_product_with_name_and_price(String name, double price) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + lastCreatedProductId))
                .andExpect(status().isOk())
                .andReturn();

        Product retrievedProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        assertEquals(name, retrievedProduct.getName());
        assertEquals(price, retrievedProduct.getPrice());
    }

    @When("I update the product with name {string} to have price {double}")
    public void i_update_the_product_with_name_to_have_price(String name, double price) throws Exception {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/" + lastCreatedProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk());
    }

    @When("I delete the product with name {string}")
    public void i_delete_the_product_with_name(String name) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + lastCreatedProductId))
                .andExpect(status().isOk());
    }

    @Then("I should not be able to retrieve the product with name {string}")
    public void i_should_not_be_able_to_retrieve_the_product_with_name(String name) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + lastCreatedProductId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertNull(result.getResponse().getContentAsString());
    }

    @Then("I should be able to retrieve all products and see {string} and {string}")
    public void i_should_be_able_to_retrieve_all_products_and_see(String name1, String name2) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andReturn();

        List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        boolean foundName1 = products.stream().anyMatch(p -> name1.equals(p.getName()));
        boolean foundName2 = products.stream().anyMatch(p -> name2.equals(p.getName()));

        assertTrue(foundName1);
        assertTrue(foundName2);
    }
}
