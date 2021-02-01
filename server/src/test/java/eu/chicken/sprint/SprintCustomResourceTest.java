package eu.chicken.sprint;

import eu.chicken.RestAssuredUtils;
import eu.chicken.team.Team;
import eu.chicken.team.Teammate;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@QuarkusTest
class SprintCustomResourceTest {

    @Test
    void getLastByTeamTest() {
        // given
        var team = RestAssuredUtils.saveTeam(Team.builder().name("teamTwo").build()
                .addMember(Teammate.builder().name("TeamMemberTwo").email("memberTwo@sprint.it").build()));
        RestAssuredUtils.saveSprint(Sprint.builder().name("Sprint One")
                .starting(LocalDate.now())
                .ending(LocalDate.now().plusDays(14))
                .teamRef(team.id).build());
        var expected =RestAssuredUtils.saveSprint(Sprint.builder().name("Sprint Two")
                .starting(LocalDate.now().plusDays(15))
                .ending(LocalDate.now().plusDays(31))
                .teamRef(team.id).build());
        // when
        var actual = given().when().get("/sprint/team/" + team.id + "/last")
                .then().statusCode(200).extract().as(Sprint.class);
        // then
        assertThat(actual).isNotNull();
        assertThat(actual.id).isEqualTo(expected.id);
        assertThat(actual.teamRef).isEqualTo(expected.teamRef);
        assertThat(actual.starting).isEqualTo(expected.starting);
        assertThat(actual.ending).isEqualTo(expected.ending);
    }

    @Test
    void getByTeamTest() {
        //given
        var team = RestAssuredUtils.saveTeam(Team.builder().name("teamOne").build()
                .addMember(Teammate.builder().name("TeamMemberTwo").email("member@sprint.it").build()));
        var one = RestAssuredUtils.saveSprint(Sprint.builder().name("Sprint One")
                .starting(LocalDate.now())
                .ending(LocalDate.now().plusDays(14))
                .teamRef(team.id).build());
        var two = RestAssuredUtils.saveSprint(Sprint.builder().name("Sprint Two")
                .starting(LocalDate.now().plusDays(15))
                .ending(LocalDate.now().plusDays(31))
                .teamRef(team.id).build());
        //when
        var actual = given().when().get("/sprint/team/" + team.id)
                .then().statusCode(200).extract().body().jsonPath().getList(".", Sprint.class);
        //then
        assertThat(actual).hasSize(2)
                .extracting("name", "teamRef", "starting", "ending")
                .contains(tuple(one.name, team.id, one.starting, one.ending), tuple(two.name, team.id, two.starting, two.ending));
    }

}
