package com.aromasdevina.core.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FileCriteriaTest {

    @Test
    void newFileCriteriaHasAllFiltersNullTest() {
        var fileCriteria = new FileCriteria();
        assertThat(fileCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void fileCriteriaFluentMethodsCreatesFiltersTest() {
        var fileCriteria = new FileCriteria();

        setAllFilters(fileCriteria);

        assertThat(fileCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void fileCriteriaCopyCreatesNullFilterTest() {
        var fileCriteria = new FileCriteria();
        var copy = fileCriteria.copy();

        assertThat(fileCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(fileCriteria)
        );
    }

    @Test
    void fileCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var fileCriteria = new FileCriteria();
        setAllFilters(fileCriteria);

        var copy = fileCriteria.copy();

        assertThat(fileCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(fileCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var fileCriteria = new FileCriteria();

        assertThat(fileCriteria).hasToString("FileCriteria{}");
    }

    private static void setAllFilters(FileCriteria fileCriteria) {
        fileCriteria.id();
        fileCriteria.distinct();
    }

    private static Condition<FileCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria -> condition.apply(criteria.getId()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FileCriteria> copyFiltersAre(FileCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria -> condition.apply(criteria.getId(), copy.getId()) && condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
