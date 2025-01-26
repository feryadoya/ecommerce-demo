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

import com.example.ecommerce.model.Coupon;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EcommerceApplication.class)
@AutoConfigureMockMvc
public class StepCouponsDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long lastCreatedCouponId;

    @When("I create a coupon with code {string} and discount {double}")
    public void i_create_a_coupon_with_code_and_discount(String code, double discount) throws Exception {
        Coupon coupon = new Coupon();
        coupon.setCode(code);
        coupon.setDiscount(discount);
        String couponJson = objectMapper.writeValueAsString(coupon);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(couponJson))
                .andExpect(status().isCreated())
                .andReturn();

        Coupon createdCoupon = objectMapper.readValue(result.getResponse().getContentAsString(), Coupon.class);
        lastCreatedCouponId = createdCoupon.getId();
    }

    @Then("I should be able to retrieve the coupon with code {string} and discount {double}")
    public void i_should_be_able_to_retrieve_the_coupon_with_code_and_discount(String code, double discount) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/coupons/" + lastCreatedCouponId))
                .andExpect(status().isOk())
                .andReturn();

        Coupon retrievedCoupon = objectMapper.readValue(result.getResponse().getContentAsString(), Coupon.class);
        assertEquals(code, retrievedCoupon.getCode());
        assertEquals(discount, retrievedCoupon.getDiscount());
    }

    @When("I update the coupon with code {string} to have discount {double}")
    public void i_update_the_coupon_with_code_to_have_discount(String code, double discount) throws Exception {
        Coupon coupon = new Coupon();
        coupon.setCode(code);
        coupon.setDiscount(discount);
        String couponJson = objectMapper.writeValueAsString(coupon);

        mockMvc.perform(MockMvcRequestBuilders.put("/coupons/" + lastCreatedCouponId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(couponJson))
                .andExpect(status().isOk());
    }

    @When("I delete the coupon with code {string}")
    public void i_delete_the_coupon_with_code(String code) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/coupons/" + lastCreatedCouponId))
                .andExpect(status().isOk());
    }

    @Then("I should not be able to retrieve the coupon with code {string}")
    public void i_should_not_be_able_to_retrieve_the_coupon_with_code(String code) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/coupons/" + lastCreatedCouponId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertEquals("", result.getResponse().getContentAsString());
    }

    @Then("I should be able to retrieve all coupons and see {string} and {string}")
    public void i_should_be_able_to_retrieve_all_coupons_and_see(String code1, String code2) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/coupons"))
                .andExpect(status().isOk())
                .andReturn();

        List<Coupon> coupons = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Coupon[].class));
        boolean foundCode1 = coupons.stream().anyMatch(c -> code1.equals(c.getCode()));
        boolean foundCode2 = coupons.stream().anyMatch(c -> code2.equals(c.getCode()));

        assertTrue(foundCode1);
        assertTrue(foundCode2);
    }
}
