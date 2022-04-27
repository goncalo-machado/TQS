package pt.ua.tqs.covidtracker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CovidTrackerSteps {
    
    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = WebDriverManager.chromedriver().create();
        driver.get(url);
    }

    @And("I select the searchbar and type {string}")
    public void iSelectSeachbarAndType(String country){
        driver.findElement(By.xpath("/html/body/div[2]/form/p[1]/input")).sendKeys(country);
    }

    @And("I click on the radio button for {string}")
    public void iClickRadioButton(String day){
        String xPath = "";
        if (day.equalsIgnoreCase("Today")){
            xPath = "/html/body/div[2]/form/input[1]";
        } else if (day.equalsIgnoreCase("Yesterday")){
            xPath = "/html/body/div[2]/form/input[2]";
        } else if (day.equalsIgnoreCase("Two Days Ago")){
            xPath = "/html/body/div[2]/form/input[3]";
        }
        driver.findElement(By.xpath(xPath)).click();
    }

    @And("I click Submit")
    public void iPressEnter() {
        driver.findElement(By.xpath("/html/body/div[2]/form/button")).click();
    }

    @And("I click Cache")
    public void clickCache(){
        driver.findElement(By.xpath("/html/body/div[1]/div/a[2]")).click();
    }

    @Then("the title of the page should be {string}")
    public void assertTitleOfPage(String title){
        assertEquals(title, driver.getTitle());
    }

    @And("there should be a card with the title {string} and value {string}")
    public void assertSomeCardHas(String title, String value){
        String titleid = "key" + title;
        String valueid = "value" + title;
        try{
            driver.findElement(By.id(titleid));
            WebElement element =  driver.findElement(By.id(valueid));
            assertEquals(value, element.getText());
        }catch (NoSuchElementException e){
            throw new AssertionError("\"" + title + "\" is not present in any card");
        }
    }
}
