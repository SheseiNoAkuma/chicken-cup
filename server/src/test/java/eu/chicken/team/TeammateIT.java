package eu.chicken.team;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Transactional
class TeammateIT {

    @Inject
    Validator validator;

    @Test
    @DisplayName("email is unique")
    void emailUnique() {
        // given
        Teammate teammate = Teammate.builder().name("name").email("name@domain.it").build();
        teammate.persistAndFlush();
        Teammate sameEmailTeammate = Teammate.builder().name("other").email("name@domain.it").build();
        // when
        Throwable thrown = catchThrowable(sameEmailTeammate::persistAndFlush);
        // then
        assertThat(thrown)
                .isInstanceOf(PersistenceException.class)
                .hasStackTraceContaining("Unique index or primary key violation");
    }

    @Test
    @DisplayName("entity validation - with validator")
    void validation() {
        // given
        Teammate teammate = Teammate.builder().email("pippo").build();
        // when
        Set<ConstraintViolation<Teammate>> validate = validator.validate(teammate);
        // then
        assertThat(validate).isNotEmpty();
    }

    @Test
    @DisplayName("entity validation - with persist")
    void validationException() {
        // given
        Teammate teammate = Teammate.builder().email("pippo").build();
        // when
        Throwable thrown = catchThrowable(teammate::persistAndFlush);
        // then
        assertThat(thrown)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("name cannot be null")
                .hasMessageContaining("must be a well-formed email address");
    }

    @Test
    @DisplayName("verify findByEmail")
    void findByEmail() {
        // given
        Teammate expected = Teammate.builder().name("User").email("user@domain.it").build();
        expected.persistAndFlush();
        // when
        Teammate actual = Teammate.findByEmail(expected.email);
        // then
        assertThat(actual).isEqualTo(expected);
    }
}
