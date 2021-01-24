package eu.chicken.sprint;

import io.quarkus.mongodb.panache.MongoEntity;
import lombok.*;

import java.time.LocalDate;

@MongoEntity(collection = "Sprint", database = "chicken")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Sprint {
    public String name;
    public LocalDate from;
    public LocalDate to;
}
