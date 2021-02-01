package eu.chicken.team;

import eu.chicken.RestAssuredUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@QuarkusTest
class TeamCustomResourceTest {

    @Test
    void saveTest() {
        // given
        var expected = Team.builder().name("team").build().addMember(Teammate.builder().name("Test").email("other@domain.it").build());
        // when
        var actual = RestAssuredUtils.saveTeam(expected);
        // then
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "members").isEqualTo(expected);
        assertThat(actual.members).hasSize(1)
                .extracting("name", "email").contains(tuple("Test", "other@domain.it"));
    }

    @Test
    void getTeammateByEmailTest() {
        // given
        var team = RestAssuredUtils.saveTeam(Team.builder().name("team").build()
                .addMember(Teammate.builder().name("Test").email("user@domain.it").build()));
        //when
        var actual = given().when().get("/team/" + team.id + "/teammate/email/user@domain.it")
                .then().statusCode(200).extract().as(Teammate.class);
        //then
        assertThat(actual).isEqualTo(team.members.get(0));
    }

    @Test
    void addTeammateTest() {
        // given
        var team = RestAssuredUtils.saveTeam(Team.builder().name("team").build());
        var teammate = Teammate.builder().name("Add").email("addUser@domain.it").build();
        //when
        var actual = given().when().contentType(ContentType.JSON).body(teammate).post("/team/" + team.id + "/teammate")
                .then().statusCode(200).extract().as(Team.class);
        //then
        assertThat(actual.members).hasSize(1)
                .extracting("name", "email").contains(tuple("Add", "addUser@domain.it"));
    }

    @Test
    void getByTeamTest() {
        // given
        var team = RestAssuredUtils.saveTeam(Team.builder().name("team").build()
                .addMember(Teammate.builder().name("One").email("one@domain.it").build())
                .addMember(Teammate.builder().name("Two").email("two@domain.it").build()));
        //when
        var actual = given().when().get("/team/" + team.id + "/teammate")
                .then().statusCode(200).extract().body().jsonPath().getList(".", Teammate.class);
        //then
        assertThat(actual).hasSize(2)
                .extracting("name", "email")
                .contains(tuple("One", "one@domain.it"), tuple("Two", "two@domain.it"));
    }

}
