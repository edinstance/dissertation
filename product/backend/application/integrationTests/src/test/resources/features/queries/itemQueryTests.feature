
 Feature: Searching for an item
   Scenario: A user wants to search for an item
     Given a user is created using the cognito user
     And an item the user wants exists
     When a user searches for the item
     Then the server returns the item the user searched for
