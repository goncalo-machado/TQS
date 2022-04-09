Feature: Login in practice site

    Scenario: Successful login
        When I navigate to "https://bonigarcia.dev/selenium-webdriver-java/login-form.html"
        And I login with the username "user" and password "user"
        And I click Submit
        Then I should be see the message "Login successful"

    Scenario: Failure login
        When I navigate to "https://bonigarcia.dev/selenium-webdriver-java/login-form.html"
        And I login with the username "bad-user" and password "bad-password"
        And I click Submit
        Then I should be see the message "Invalid credentials"

    Scenario: Philadelphia to Berline Flight Number
        When I navigate to "https://blazedemo.com/"
        And I click to depart to Philadelphia
        And I click to arrive to Berlin
        And I click Find Flights
        And I choose the third flight
        Then the flight number should be "UA954"