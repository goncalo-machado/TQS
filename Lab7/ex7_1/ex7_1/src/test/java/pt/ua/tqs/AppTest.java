package pt.ua.tqs;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;


public class AppTest 
{
    @Test
    public void whenRequestGet_thenOK(){
        when().request("GET", "https://jsonplaceholder.typicode.com/todos").then().statusCode(200);
    }

    @Test
    public void whenRequest_Todos4_Then_CheckTitle() {
        get("https://jsonplaceholder.typicode.com/todos/4").then().statusCode(200).assertThat()
        .body("title", equalTo("et porro tempora")); 
    }

    @Test
    public void whenRequest_AllTodos_Then_Id198And199Exist(){
        get("https://jsonplaceholder.typicode.com/todos").then().statusCode(200).assertThat()
        .body("id", hasItems(198,199)); 
    }
}
