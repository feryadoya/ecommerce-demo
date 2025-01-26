Feature: Customers

  Scenario: Create and retrieve a customer
    Given the application is running
    When I create a customer with name "Customer1" and email "customer1@example.com"
    Then I should be able to retrieve the customer with name "Customer1" and email "customer1@example.com"

  Scenario: Update and retrieve a customer
    Given the application is running
    When I create a customer with name "Customer2" and email "customer2@example.com"
    And I update the customer with name "Customer2" to have email "updated_customer2@example.com"
    Then I should be able to retrieve the customer with name "Customer2" and email "updated_customer2@example.com"

  Scenario: Delete and try to retrieve a customer
    Given the application is running
    When I create a customer with name "Customer3" and email "customer3@example.com"
    And I delete the customer with name "Customer3"
    Then I should not be able to retrieve the customer with name "Customer3"

  Scenario: Create multiple customers and retrieve all
    Given the application is running
    When I create a customer with name "Customer4" and email "customer4@example.com"
    And I create a customer with name "Customer5" and email "customer5@example.com"
    Then I should be able to retrieve all customers and see "Customer4" and "Customer5"

  Scenario: Update and delete a customer
    Given the application is running
    When I create a customer with name "Customer6" and email "customer6@example.com"
    And I update the customer with name "Customer6" to have email "updated_customer6@example.com"
    And I delete the customer with name "Customer6"
    Then I should not be able to retrieve the customer with name "Customer6"
