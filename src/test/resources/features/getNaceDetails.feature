Feature: get the order details
  This feature includes tests that test the get order RESTFul services

  @test
  Scenario: User is able to get the order details
    Given order details api is up
    And a 200 status code is returned
    When get the order details for orderid "398481"
    Then a 200 status code is returned
    And validate the order details in the response
