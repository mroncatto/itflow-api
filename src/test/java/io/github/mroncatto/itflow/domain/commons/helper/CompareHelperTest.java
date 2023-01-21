package io.github.mroncatto.itflow.domain.commons.helper;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompareHelperTest {

    @Test
    void shouldCompareIfValueIsGreater() {

        // Test Date
        Calendar calendar = Calendar.getInstance();
        Date dateA = calendar.getTime();
        calendar.add(Calendar.MINUTE, 1);
        Date dateB = calendar.getTime();
        boolean compareDate = CompareHelper.biggerThan(dateB, dateA);
        assertTrue(compareDate);

        // Test Long
        boolean compareLong = CompareHelper.biggerThan(2L, 1L);
        assertTrue(compareLong);
    }

    @Test
    void shouldCompareIfValueIsEqual() {

        boolean compareEquals = CompareHelper.match("Value A", "Value A");
        boolean compareDifferent = CompareHelper.match("Value A", "Value B");
        assertTrue(compareEquals);
        assertFalse(compareDifferent);
    }

    @Test
    void shouldCompareIfValueIsDistinct() {

        boolean compareEquals = CompareHelper.distinct("Value A", "Value A");
        boolean compareDifferent = CompareHelper.distinct("Value A", "Value B");
        assertFalse(compareEquals);
        assertTrue(compareDifferent);
    }
}