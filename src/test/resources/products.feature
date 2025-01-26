Feature: Products

  Scenario: Example scenario
    Given the application is running
    When I check the status
    Then it should be up and running

  Scenario: Create and retrieve a product
    Given the application is running
    When I create a product with name "Product1" and price 10.0
    Then I should be able to retrieve the product with name "Product1" and price 10.0

  Scenario: Update and retrieve a product
    Given the application is running
    When I create a product with name "Product2" and price 20.0
    And I update the product with name "Product2" to have price 25.0
    Then I should be able to retrieve the product with name "Product2" and price 25.0

  Scenario: Delete and try to retrieve a product
    Given the application is running
    When I create a product with name "Product3" and price 30.0
    And I delete the product with name "Product3"
    Then I should not be able to retrieve the product with name "Product3"

  Scenario: Create multiple products and retrieve all
    Given the application is running
    When I create a product with name "Product4" and price 40.0
    And I create a product with name "Product5" and price 50.0
    Then I should be able to retrieve all products and see "Product4" and "Product5"

  Scenario: Update and delete a product
    Given the application is running
    When I create a product with name "Product6" and price 60.0
    And I update the product with name "Product6" to have price 65.0
    And I delete the product with name "Product6"
    Then I should not be able to retrieve the product with name "Product6"