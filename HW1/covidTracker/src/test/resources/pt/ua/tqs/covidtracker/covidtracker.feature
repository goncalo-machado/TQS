Feature: CovidTracker web testing

    Scenario: Getting data from Portugal from Two Days Ago
        When I navigate to "http://localhost:8080/"
        And I select the searchbar and type "Portugal"
        And I click on the radio button for "Two Days Ago"
        And I click Submit
        Then the title of the page should be "CountryStats"
        And there should be a card that has "Portugal"
        And there should be a card that has "Two Days Ago"
    
    Scenario: Getting cache statistics
        When I navigate to "http://localhost:8080/"
        And I click Cache 
        Then the title of the page should be "Cache Page"
        And there should be a card that has "Hits"
        And there should be a card that has "Misses"
        And there should be a card that has "Get Requests"
        And there should be a card that has "Save Requests"
        And there should be a card that has "Delete Requests"