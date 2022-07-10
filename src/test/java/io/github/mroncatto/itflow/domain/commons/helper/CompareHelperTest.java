package io.github.mroncatto.itflow.domain.commons.helper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CompareHelperTest {

    @Test
    void biggerThan() {

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
}