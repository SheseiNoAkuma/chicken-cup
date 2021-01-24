package eu.chicken.team;

import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TeamTest {

    @Test
    void name() {
        Team.deleteAll();
        Assertions.assertThat(Team.findAll().stream().count()).isZero();

        Team sut = Team.builder().name("prova").icon("whatever").teammate(Teammate.builder().name("Marco").build()).build();
        sut.persist();
        Assertions.assertThat(sut.id).isNotNull();
        ObjectId id = sut.id;

        Assertions.assertThat(Team.findAll().stream().count()).isEqualTo(1);

        Team byId = Team.findById(id);
        Assertions.assertThat(byId).isEqualTo(sut);
    }
}
