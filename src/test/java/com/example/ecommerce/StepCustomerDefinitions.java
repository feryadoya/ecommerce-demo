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

import com.example.ecommerce.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EcommerceApplication.class)
@AutoConfigureMockMvc
public class StepCustomerDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long lastCreatedCustomerId;

    @When("I create a customer with name {string} and email {string}")
    public void i_create_a_customer_with_name_and_email(String name, String email) throws Exception {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        String customerJson = objectMapper.writeValueAsString(customer);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isCreated())
                .andReturn();

        Customer createdCustomer = objectMapper.readValue(result.getResponse().getContentAsString(), Customer.class);
        lastCreatedCustomerId = createdCustomer.getId();
    }

    @Then("I should be able to retrieve the customer with name {string} and email {string}")
    public void i_should_be_able_to_retrieve_the_customer_with_name_and_email(String name, String email) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + lastCreatedCustomerId))
                .andExpect(status().isOk())
                .andReturn();

        Customer retrievedCustomer = objectMapper.readValue(result.getResponse().getContentAsString(), Customer.class);
        assertEquals(name, retrievedCustomer.getName());
        assertEquals(email, retrievedCustomer.getEmail());
    }

    @When("I update the customer with name {string} to have email {string}")
    public void i_update_the_customer_with_name_to_have_email(String name, String email) throws Exception {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        String customerJson = objectMapper.writeValueAsString(customer);

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/" + lastCreatedCustomerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isOk());
    }

    @When("I delete the customer with name {string}")
    public void i_delete_the_customer_with_name(String name) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + lastCreatedCustomerId))
                .andExpect(status().isOk());
    }

    @Then("I should not be able to retrieve the customer with name {string}")
    public void i_should_not_be_able_to_retrieve_the_customer_with_name(String name) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + lastCreatedCustomerId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertEquals("", result.getResponse().getContentAsString());
    }

    @Then("I should be able to retrieve all customers and see {string} and {string}")
    public void i_should_be_able_to_retrieve_all_customers_and_see(String name1, String name2) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(status().isOk())
                .andReturn();

        List<Customer> customers = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Customer[].class));
        boolean foundName1 = customers.stream().anyMatch(c -> name1.equals(c.getName()));
        boolean foundName2 = customers.stream().anyMatch(c -> name2.equals(c.getName()));

        assertTrue(foundName1);
        assertTrue(foundName2);
    }
}
