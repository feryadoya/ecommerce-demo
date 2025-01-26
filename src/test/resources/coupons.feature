Feature: Coupons

  Scenario: Create and retrieve a coupon
    Given the application is running
    When I create a coupon with code "COUPON1" and discount 10.0
    Then I should be able to retrieve the coupon with code "COUPON1" and discount 10.0

  Scenario: Update and retrieve a coupon
    Given the application is running
    When I create a coupon with code "COUPON2" and discount 20.0
    And I update the coupon with code "COUPON2" to have discount 25.0
    Then I should be able to retrieve the coupon with code "COUPON2" and discount 25.0

  Scenario: Delete and try to retrieve a coupon
    Given the application is running
    When I create a coupon with code "COUPON3" and discount 30.0
    And I delete the coupon with code "COUPON3"
    Then I should not be able to retrieve the coupon with code "COUPON3"

  Scenario: Create multiple coupons and retrieve all
    Given the application is running
    When I create a coupon with code "COUPON4" and discount 40.0
    And I create a coupon with code "COUPON5" and discount 50.0
    Then I should be able to retrieve all coupons and see "COUPON4" and "COUPON5"

  Scenario: Update and delete a coupon
    Given the application is running
    When I create a coupon with code "COUPON6" and discount 60.0
    And I update the coupon with code "COUPON6" to have discount 65.0
    And I delete the coupon with code "COUPON6"
    Then I should not be able to retrieve the coupon with code "COUPON6"
