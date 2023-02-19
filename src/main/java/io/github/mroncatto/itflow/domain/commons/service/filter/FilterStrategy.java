package io.github.mroncatto.itflow.domain.commons.service.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface FilterStrategy {
    Predicate equalsFilter(CriteriaBuilder builder, Root<?> root, String param, Object filter);
    Predicate likeFilter(CriteriaBuilder builder, Root<?> root, String param, String filter);
    Predicate StartWithFilter(CriteriaBuilder builder, Root<?> root, String param, String filter);
    Predicate whereInFilter(Root<?> root, String param1, String param2, List<?> listValues);
}
