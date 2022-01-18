import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;


/**
 *
 * @author Anastasiia Bondarenko
 */
public class SetupTests {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    final String URI = "https://api.github.com";
    
    public SetupTests() {
    }


    @BeforeClass
    public void setUpClass() {

        String accessToken = "1b073951757118a493942d021d805933d819f955";
        authentication = oauth2(accessToken);

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(URI)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectHeader("Server", "GitHub.com")
                .build();

        requestSpecification = requestSpec;
        responseSpecification = responseSpec;
    }

}
