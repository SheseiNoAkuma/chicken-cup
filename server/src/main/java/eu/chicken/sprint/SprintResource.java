package eu.chicken.sprint;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.rest.data.panache.MethodProperties;

import java.util.List;

public interface SprintResource extends PanacheEntityResource<Sprint, String> {

    @Override
    @MethodProperties(exposed = false)
    List<Sprint> list(Page page, Sort sort);

    @Override
    @MethodProperties(exposed = false)
    Sprint update(String s, Sprint sprint);

    @Override
    @MethodProperties(exposed = false)
    boolean delete(String s);
}
