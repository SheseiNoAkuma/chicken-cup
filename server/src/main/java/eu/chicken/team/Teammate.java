package eu.chicken.team;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Teammate {
    public String name;
    public String avatar;
    public String email;
}
