package eu.chicken.badge;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface BadgeResource extends PanacheEntityResource<Badge, String> {
}
