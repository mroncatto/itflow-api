package io.github.mroncatto.itflow.domain.commons.service.filter;

import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class FilterService implements FilterStrategy {
    @Override
    public Predicate equalsFilter(CriteriaBuilder builder, Root<?> root, String param, Object filter) {
        if(filter instanceof String)
            return builder.equal(builder.lower(root.get(param)), filter.toString().toLowerCase());

        return builder.equal(root.get(param), filter);
    }

    @Override
    public Predicate likeFilter(CriteriaBuilder builder, Root<?> root, String param, String filter) {
        return builder.like(builder.lower(root.get(param)), "%" + filter.toLowerCase() + "%");
    }

    @Override
    public Predicate StartWithFilter(CriteriaBuilder builder, Root<?> root, String param, String filter) {
        return builder.like(builder.lower(root.get(param)), filter.toLowerCase() + "%");
    }

    @Override
    public Predicate whereInFilter(Root<?> root, String param1, String param2, List<?> listValues) {
        Expression<Long> data = root.get(param1).get(param2);
        return data.in(listValues);
    }
}
