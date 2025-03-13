package dmit2015.resource;


import dmit2015.entity.TodoItem;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://github.com/rest-assured/rest-assured
 * https://github.com/rest-assured/rest-assured/wiki/Usage
 * http://www.mastertheboss.com/jboss-frameworks/resteasy/restassured-tutorial
 * https://eclipse-ee4j.github.io/jsonb-api/docs/user-guide.html
 * https://github.com/FasterXML/jackson-databind
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoItemResourceRestAssuredIT {

    String todoResourceUrl = "http://localhost:8090/restapi/TodoItemsDto";
    String testDataResourceLocation;

    @BeforeAll
    static void beforeAllTests() {
        // code to execute before all tests in the current test class
    }

    @AfterAll
    static void afterAllTests() {
        // code to execute after all tests in the current test class
    }

    @BeforeEach
    void beforeEachTestMethod() {
        // Code to execute before each test such as creating the test data
    }

    @AfterEach
    void afterEachTestMethod() {
        // code to execute after each test such as deleting the test data
    }

    @Order(1)
    @ParameterizedTest
    @CsvSource(value = {
            "Create JAX-RS demo project,true,Create DTO version of TodoResource,false"
    })
    void shouldFindAll(String firstName, boolean firstComplete, String lastName, boolean lastComplete) {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(todoResourceUrl)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        List<TodoItem> queryResultList = jsonb.fromJson(jsonBody, new ArrayList<TodoItem>(){}.getClass().getGenericSuperclass());

        TodoItem firstTodoItem = queryResultList.get(0);
        assertThat(firstTodoItem.getTask())
                .isEqualTo(firstName);
        assertThat(firstTodoItem.isDone())
                .isEqualTo(firstComplete);

        TodoItem lastTodoItem = queryResultList.get(queryResultList.size() - 1);
        assertThat(lastTodoItem.getTask())
                .isEqualTo(lastName);
        assertThat(lastTodoItem.isDone())
                .isEqualTo(lastComplete);

    }

    @Order(2)
    @ParameterizedTest
    @CsvSource(value = {
            "Create REST Assured Integration Test,false"
    })
    void shouldCreate(String name, boolean complete) {
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setTask(name);
        newTodoItem.setDone(complete);

        // Jsonb jsonb = JsonbBuilder.create();
        // String jsonBody = jsonb.toJson(newTodoItem);

        Response response = given()
                .contentType(ContentType.JSON)
                // .body(jsonBody)
                .body(newTodoItem)
                .when()
                .post(todoResourceUrl)
                .then()
                .statusCode(201)
                .extract()
                .response();
        testDataResourceLocation = response.getHeader("location");
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource(value = {
            "Create REST Assured Integration Test,false"
    })
    void shouldFineOne(String name, boolean complete) {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        TodoItem existingTodoItem = jsonb.fromJson(jsonBody, TodoItem.class);

        assertThat(existingTodoItem.getTask())
                .isEqualTo(name);
        assertThat(existingTodoItem.isDone())
                .isEqualTo(complete);
    }

    @Order(4)
    @ParameterizedTest
    @CsvSource(value = {
            "Run REST Assured Integration Test,true"
    })
    void shouldUpdate(String name, boolean complete) {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(testDataResourceLocation)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
        String jsonBody = response.getBody().asString();

        Jsonb jsonb = JsonbBuilder.create();
        TodoItem existingTodoItem = jsonb.fromJson(jsonBody, TodoItem.class);

        existingTodoItem.setTask(name);
        existingTodoItem.setDone(complete);

        String jsonTodoItem = jsonb.toJson(existingTodoItem);

        Response putResponse = given()
                .contentType(ContentType.JSON)
                .body(jsonTodoItem)
                .when()
                .put(testDataResourceLocation)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String putResponseJsonBody = putResponse.getBody().asString();
        TodoItem updatedTodoItem = jsonb.fromJson(putResponseJsonBody, TodoItem.class);

        assertThat(existingTodoItem)
                .usingRecursiveComparison()
//                .ignoringFields("updateTime")
                .isEqualTo(updatedTodoItem);
    }

    @Order(5)
    @Test
    void shouldDelete() {
        given()
                .when()
                .delete(testDataResourceLocation)
                .then()
                .statusCode(204);
    }

}