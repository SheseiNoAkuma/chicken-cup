package eu.chicken.team;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@Transactional
class TeamTest {

    @Test
    void name() {
        Team.deleteAll();
        assertThat(Team.findAll().stream().count()).isZero();

        Team sut = Team.builder().name("Charlie").member(Teammate.builder().name("Marco").email("marco.righi@tas.eu").build()).build();
        sut.persist();

        assertThat(sut.id).isNotNull();
    }
}
