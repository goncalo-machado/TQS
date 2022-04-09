package pt.ua.tqs;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = WebDriverManager.chromedriver().create();
        driver.get(url);
    }

    @And("I login with the username {string} and password {string}")
    public void iLogin(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

    }

    @And("I click Submit")
    public void iPressEnter() {
        driver.findElement(By.cssSelector("button")).click();
    }

    @Then("I should be see the message {string}")
    public void iShouldSee(String result) {
        try {
            driver.findElement(
                    By.xpath("//*[contains(text(), '" + result + "')]"));
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result + "\" not available in results");
        } finally {
            driver.quit();
        }
    }

    @And("I click to {word} to {word}")
    public void iClickDepartorArrive(String option, String city){
        String op;
        op = option.equals("depart") ? "fromPort" : "toPort";
        Select dropdown = new Select(driver.findElement(By.name(op)));
        dropdown.selectByValue(city);
    }

    @And("I click Find Flights")
    public void iClickFindFlights(){
        driver.findElement(By.tagName("input")).click();
    }

    @And("I choose the third flight")
    public void iChooseThirdFlight(){
        driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[3]/td[1]/input")).click();
    }

    @Then("the flight number should be {string}")
    public void assertFlightNumber(String flightNumber){
        try {
            WebElement elem = driver.findElement(By.xpath("/html/body/div[2]/p[2]"));
            Assertions.assertTrue(elem.getText().contains(flightNumber));
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + flightNumber + "\" not available in results");
        } finally {
            driver.quit();
        }
    }

}
