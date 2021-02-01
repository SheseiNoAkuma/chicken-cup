package eu.chicken;

import eu.chicken.sprint.Sprint;
import eu.chicken.team.Team;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class RestAssuredUtils {
    public static Team saveTeam(Team team) {
        return given().when().contentType(ContentType.JSON).body(team).post("/team/")
                .then().statusCode(201).extract().as(Team.class);
    }

    public static Sprint saveSprint(Sprint sprint) {
        return given().when().contentType(ContentType.JSON).body(sprint).post("/sprint/")
                .then().statusCode(201).extract().as(Sprint.class);
    }
}
