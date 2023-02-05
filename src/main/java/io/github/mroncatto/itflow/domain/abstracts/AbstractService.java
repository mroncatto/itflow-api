package io.github.mroncatto.itflow.domain.abstracts;

import io.github.mroncatto.itflow.config.exception.model.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.BindingResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractService {

    protected void validateResult(BindingResult result) throws BadRequestException {
        if(result.hasErrors()){
            throw new BadRequestException(getResultError(result));
        }
    }

    private String getResultError(BindingResult result) {
        String error = "";
        try {
            error = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
        } catch (Exception e){
            log.error("Fail to get binding result field: {}", e.getMessage());
        }
        return error;
    }

    protected static void validateNullFields(Object... objects) throws BadRequestException {
        if(Arrays.stream(objects).anyMatch(Objects::isNull))
            throw new BadRequestException("ALL FIELDS ARE REQUIRED");
    }

    protected static void validateEmptyFields(String... fields) throws BadRequestException {
        if(Arrays.stream(fields).anyMatch(String::isEmpty))
            throw new BadRequestException("ALL FIELDS ARE REQUIRED");
    }

    protected static void validateEmailField(String email) throws BadRequestException {
        if (!EmailValidator.getInstance().isValid(email)){
            throw new BadRequestException("INVALID FORMAT EMAIL");
        }
    }

    protected static Predicate filterEquals(CriteriaBuilder builder, Root<?> root, String param, String filter) {
        return builder.equal(builder.lower(root.get(param)), filter.toLowerCase());
    }

    protected static Predicate filterEquals(CriteriaBuilder builder, Root<?> root, String param, Long filter) {
        return builder.equal(root.get(param), filter);
    }

    protected static Predicate filterEquals(CriteriaBuilder builder, Root<?> root, String param, boolean filter) {
        return builder.equal(root.get(param), filter);
    }

    protected static Predicate filterLike(CriteriaBuilder builder, Root<?> root, String param, String filter) {
        return builder.like(builder.lower(root.get(param)), "%" + filter.toLowerCase() + "%");
    }

    protected static Predicate filterInWhereID(Root<?> root, String param, List<String> ids) {
        Expression<Long> department = root.get(param).get("id");
        return department.in(ids);
    }

    protected static Predicate filterStartWith(CriteriaBuilder builder, Root<?> root, String param, String filter) {
        return builder.like(builder.lower(root.get(param)), filter.toLowerCase() + "%");
    }

    protected static Predicate filterEndWith(CriteriaBuilder builder, Root<?> root, String param, String filter) {
        return builder.like(builder.lower(root.get(param)), "%" + filter.toLowerCase());
    }

    protected static Predicate[] removeNullPredicates(Predicate... predicates) {
        return Arrays.stream(predicates).filter(Objects::nonNull).toArray(Predicate[]::new);
    }
}





















