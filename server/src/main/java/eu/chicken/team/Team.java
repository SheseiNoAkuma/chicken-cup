package eu.chicken.team;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.*;

import java.util.List;

@MongoEntity(collection = "Team", database = "chicken")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Team extends PanacheMongoEntity {
    public String name;
    public String icon;
    @Singular
    public List<Teammate> teammates;
}
