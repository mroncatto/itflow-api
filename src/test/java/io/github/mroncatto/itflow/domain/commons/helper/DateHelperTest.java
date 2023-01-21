package io.github.mroncatto.itflow.domain.commons.helper;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {

    @Test
    void shouldReturnCurrentDate() {
        Date date1 = DateHelper.currentDate();
        Date date2 = DateHelper.increaseDate(1);
        assertTrue(date1.before(date2));
    }

    @Test
    void shouldReturnCurrentDateWithIncrement() {
        Date date = new Date();
        Date increaseDateOneMinute = DateHelper.increaseDate(date,1);
        assertInstanceOf(Date.class, increaseDateOneMinute);
        assertTrue(increaseDateOneMinute.after(new Date()));
    }
}