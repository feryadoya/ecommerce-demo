package com.example.ecommerce;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.ecommerce.model.Cart;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EcommerceApplication.class)
@AutoConfigureMockMvc
public class StepCartDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @When("I add product with id {long} to the cart for customer {long} with quantity {int}")
    public void i_add_product_to_the_cart(Long productId, Long customerId, int quantity) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                .param("customerId", customerId.toString())
                .param("productId", productId.toString())
                .param("quantity", String.valueOf(quantity))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @When("I remove product with id {long} from the cart for customer {long}")
    public void i_remove_product_from_the_cart(Long productId, Long customerId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/remove")
                .param("customerId", customerId.toString())
                .param("productId", productId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @When("I clear the cart for customer {long}")
    public void i_clear_the_cart(Long customerId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/clear/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @When("I update product with id {long} in the cart for customer {long} to quantity {int}")
    public void i_update_product_in_the_cart(Long productId, Long customerId, int quantity) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                .param("customerId", customerId.toString())
                .param("productId", productId.toString())
                .param("quantity", String.valueOf(quantity))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Then("the cart for customer {long} should contain product with id {long} and quantity {int}")
    public void the_cart_should_contain_product(Long customerId, Long productId, int quantity) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId) && item.getQuantity() == quantity));
    }

    @Then("the cart for customer {long} should not contain product with id {long}")
    public void the_cart_should_not_contain_product(Long customerId, Long productId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getItems().stream()
                .noneMatch(item -> item.getProduct().getId().equals(productId)));
    }

    @Then("the cart for customer {long} should be empty")
    public void the_cart_should_be_empty(Long customerId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getItems().isEmpty());
    }

    @Then("the total cost of the cart for customer {long} should be calculated")
    public void the_total_cost_should_be_calculated(Long customerId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/total/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        double total = Double.parseDouble(result.getResponse().getContentAsString());
        assertTrue(total > 0);
    }

    @When("I apply discount with coupon code {string} to the cart for customer {long}")
    public void i_apply_discount_to_the_cart(String couponCode, Long customerId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/apply-discount")
                .param("customerId", customerId.toString())
                .param("couponCode", couponCode)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Then("the cart for customer {long} should have the discount applied")
    public void the_cart_should_have_discount_applied(Long customerId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getCoupon() != null);
    }

    @Then("I should be able to retrieve the cart for customer {long} and see product with id {long} and quantity {int}")
    public void i_should_be_able_to_retrieve_cart(Long customerId, Long productId, int quantity) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId) && item.getQuantity() == quantity));
    }

    @Then("I should be able to retrieve the cart for customer {long} and see the discount applied")
    public void i_should_be_able_to_retrieve_cart_with_discount(Long customerId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getCoupon() != null);
    }

    @Then("I should be able to retrieve the cart for customer {long} and see it is empty")
    public void i_should_be_able_to_retrieve_empty_cart(Long customerId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getItems().isEmpty());
    }

    @Then("I should be able to retrieve the cart for customer {long} and see it does not contain product with id {long}")
    public void i_should_be_able_to_retrieve_cart_and_see_it_does_not_contain_product(Long customerId, Long productId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Cart cart = objectMapper.readValue(result.getResponse().getContentAsString(), Cart.class);
        assertTrue(cart.getItems().stream()
                .noneMatch(item -> item.getProduct().getId().equals(productId)));
    }

    @Then("the total cost of the cart for customer {long} should be calculated with discount")
    public void the_total_cost_should_be_calculated_with_discount(Long customerId) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/total/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        double total = Double.parseDouble(result.getResponse().getContentAsString());
        assertTrue(total > 0); // Assuming the total should be greater than 0 after discount
    }
}
