package io.github.mroncatto.itflow.domain.commons.service.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

public interface FilterStrategy {
    Predicate equalsFilter(CriteriaBuilder builder, Root<?> root, String param, Object filter);
    Predicate likeFilter(CriteriaBuilder builder, Root<?> root, String param, String filter);
    Predicate StartWithFilter(CriteriaBuilder builder, Root<?> root, String param, String filter);
    Predicate whereInFilter(Root<?> root, String param1, String param2, List<?> listValues);
}
