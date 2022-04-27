Feature: CovidTracker web testing

    Scenario: Getting data from Portugal from Two Days Ago
        When I navigate to "http://localhost:8080/"
        And I select the searchbar and type "Portugal"
        And I click on the radio button for "Two Days Ago"
        And I click Submit
        Then the title of the page should be "CountryStats"
        And there should be a card with the title "Country" and value "Portugal"
        And there should be a card with the title "DayOfData" and value "Two Days Ago"
    
    Scenario: Getting cache statistics after first scenario
        When I navigate to "http://localhost:8080/"
        And I click Cache 
        Then the title of the page should be "Cache Page"
        And there should be a card with the title "Hits" and value "1"
        And there should be a card with the title "Misses" and value "1"
        And there should be a card with the title "Get Requests" and value "1"
        And there should be a card with the title "Save Requests" and value "1"
        And there should be a card with the title "Delete Requests" and value "0"