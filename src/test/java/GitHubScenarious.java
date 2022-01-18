import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anastasiia Bondarenko
 */
public class GitHubScenarious extends SetupTests {

    private static final String OWNER = "Anastaaasiia";
    private static final String REPO_NAME = "newRepo";
    private static final String REPO_NAME_NEW = "newRepo2";

    @Test(
            priority = 1,
            groups={"stop"},
            description = "This scenario is getting all repositories"
    )
    public void getRequest_getAllRepositories() {
        given()
                .get("/users/"+ OWNER+"/repos")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(
            priority = 2,
            groups={"stop"},
            description = "This scenario is creating a new repository on github using API"
    )
    public void postRequest_createNewRepository() {
        JSONObject payload = new JSONObject();
        payload.put("name", REPO_NAME);
        payload.put("description", "description");
        payload.put("homepage", "https://api.github.com");
        payload.put("private", false);

        given()
                .body(payload.toJSONString())
                .post("user/repos")
                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", equalTo(REPO_NAME),
                        "$", not(hasKey("elo-elo")))
                .header("X-OAuth-Scopes", notNullValue())
                .statusLine("HTTP/1.1 201 Created")
                .time(lessThan(4000L));
    }

    @Test(
            priority = 3,
            groups={"stop"},
            description = "This scenario is getting created repository"
    )
    public void getRequest_getRepository() {
        given()
                .get("/repos/"+ OWNER+"/"+REPO_NAME)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(
            priority = 4,
            groups={"stop"},
            description = "This scenario is editing the repository name on github using API"
    )
    public void patchRequest_editRepository() {
        //REQUEST PAYLOAD
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", REPO_NAME_NEW);

        given()
                .body(requestParams.toJSONString())
                .patch("/repos/" + OWNER + "/" + REPO_NAME)
                .then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(REPO_NAME_NEW))
                .body("full_name", containsString(REPO_NAME_NEW))
                .body("html_url", containsString(REPO_NAME_NEW))
                .header("Content-Encoding", notNullValue())
                .statusLine(containsString("OK"));
    }

    @Test(
            priority = 5,
            groups={"stop"},
            description = "This scenario is setting up topics for a repository on GitHub using PUT request"
     )
    public void putRequest_setUpTopics() {

        String topics = "{\r\n"
                + "    \"names\": [\r\n"
                + "        \"sii\",\r\n"
                + "        \"restapidemo\",\r\n"
                + "        \"testautomation\"\r\n"
                + "    ]\r\n"
                + "}";


        given()
                .body(topics)
                .accept("application/vnd.github.v3+json")
                .put("/repos/" + OWNER + "/" + REPO_NAME_NEW+ "/topics")
                .then()
                .assertThat()
                .statusCode(200)
                .body( containsString("sii"))
                .body( containsString("restapidemo"))
                .body( containsString("testautomation"))
                .header("Content-Encoding", notNullValue())
                .statusLine(containsString("OK"));
    }

    @Test(priority = 6,
            //groups={"stop"},
            description = "This scenario is deleating a repository on github using API"
    )
    public void deleteRequest_deleteRepository() {
        given()
                .delete("/repos/" + OWNER + "/" + REPO_NAME_NEW)
                .then()
                .assertThat()
                .statusCode(204)
                .header("Date", notNullValue())
                .statusLine(containsString("No Content"));

    }

}
