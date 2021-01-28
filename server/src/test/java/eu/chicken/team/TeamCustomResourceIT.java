package eu.chicken.team;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@Transactional
class TeamCustomResourceIT {

    @Test
    void saveTest() {
        // given
        Team expected = Team.builder().name("team").build().addMember(Teammate.builder().name("Test").email("other@domain.it").build());
        // when
        Team actual = given().when()
                .contentType(ContentType.JSON).body(expected).post("/team/")
                .then().statusCode(201)
                .extract().as(Team.class);
        // then
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "members").isEqualTo(expected);
        assertThat(actual.members).hasSize(1)
                .extracting("name", "email").contains(Tuple.tuple("Test", "other@domain.it"));
    }

    @Test
    void getTeammateByEmailTest() {
        // given
        Team team = Team.builder().name("team").build().addMember(Teammate.builder().name("Test").email("user@domain.it").build());
        team = given().when().contentType(ContentType.JSON).body(team).post("/team/")
                .then().statusCode(201).extract().as(Team.class);
        //when
        Teammate actual = given().when().get("/team/" + team.id + "/teammate/email/user@domain.it")
                .then().statusCode(200).extract().as(Teammate.class);
        //then
        assertThat(actual).isEqualTo(team.members.get(0));
    }
}
