Feature: Carts

  Background:
    Given the application is running
    And I create a customer with name "Customer1" and email "customer1@example.com"
    And I create a product with name "Product1" and price 10.0
    And I create a product with name "Product2" and price 20.0
    And I create a coupon with code "DISCOUNT10" and discount 10.0

  Scenario: Add a product to the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    Then the cart for customer 1 should contain product with id 1 and quantity 2

  Scenario: Remove a product from the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I remove product with id 1 from the cart for customer 1
    Then the cart for customer 1 should not contain product with id 1

  Scenario: Clear the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I clear the cart for customer 1
    Then the cart for customer 1 should be empty

  Scenario: Calculate total cost of the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I add product with id 2 to the cart for customer 1 with quantity 3
    Then the total cost of the cart for customer 1 should be calculated

  Scenario: Apply discount to the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I apply discount with coupon code "DISCOUNT10" to the cart for customer 1
    Then the cart for customer 1 should have the discount applied

  Scenario: Add multiple products to the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I add product with id 2 to the cart for customer 1 with quantity 3
    Then the cart for customer 1 should contain product with id 1 and quantity 2
    And the cart for customer 1 should contain product with id 2 and quantity 3

  Scenario: Update product quantity in the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I update product with id 1 in the cart for customer 1 to quantity 5
    Then the cart for customer 1 should contain product with id 1 and quantity 5

  Scenario: Add and remove multiple products from the cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I add product with id 2 to the cart for customer 1 with quantity 3
    And I remove product with id 1 from the cart for customer 1
    Then the cart for customer 1 should not contain product with id 1
    And the cart for customer 1 should contain product with id 2 and quantity 3

  Scenario: Clear cart and add new products
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I clear the cart for customer 1
    And I add product with id 2 to the cart for customer 1 with quantity 3
    Then the cart for customer 1 should contain product with id 2 and quantity 3

  Scenario: Calculate total cost after applying discount
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I apply discount with coupon code "DISCOUNT10" to the cart for customer 1
    Then the total cost of the cart for customer 1 should be calculated with discount

  Scenario: Add product to cart and retrieve cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    Then I should be able to retrieve the cart for customer 1 and see product with id 1 and quantity 2

  Scenario: Add product, apply discount, and retrieve cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I apply discount with coupon code "DISCOUNT10" to the cart for customer 1
    Then I should be able to retrieve the cart for customer 1 and see the discount applied

  Scenario: Add multiple products, clear cart, and retrieve cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I add product with id 2 to the cart for customer 1 with quantity 3
    And I clear the cart for customer 1
    Then I should be able to retrieve the cart for customer 1 and see it is empty

  Scenario: Add product, update quantity, and retrieve cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I update product with id 1 in the cart for customer 1 to quantity 5
    Then I should be able to retrieve the cart for customer 1 and see product with id 1 and quantity 5

  Scenario: Add product, remove product, and retrieve cart
    Given I clear the cart for customer 1
    When I add product with id 1 to the cart for customer 1 with quantity 2
    And I remove product with id 1 from the cart for customer 1
    Then I should be able to retrieve the cart for customer 1 and see it does not contain product with id 1
