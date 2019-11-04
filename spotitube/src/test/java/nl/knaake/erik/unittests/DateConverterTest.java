package nl.knaake.erik.unittests;

import nl.knaake.erik.crosscuttingconcerns.utils.DateConverter;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DateConverterTest {

    @Test
    void dateToString() {
        assertEquals("11-09-2018", DateConverter.convertDateToString(new GregorianCalendar(2018, Calendar.SEPTEMBER, 11).getTime()));
    }

    @Test
    void dateToDateTimeString() {
        assertEquals("11-09-2018 15:54:12", DateConverter.convertDateToDateTimeString(new GregorianCalendar(2018, Calendar.SEPTEMBER, 11, 15, 54, 12).getTime()));
    }

    @Test
    void convertStringToDateTime() {
        assertEquals(new GregorianCalendar(2018, Calendar.SEPTEMBER, 11, 16, 12, 34).getTime(), DateConverter.convertStringToDateTime("11-09-2018 16:12:34"));
    }

    @Test
    void stringToDateTimeParseError() {
        assertNull(DateConverter.convertStringToDateTime("1-1-111-1111-111"));
    }

    @Test
    void addMinutes() {
        assertEquals(new GregorianCalendar(2018, Calendar.SEPTEMBER, 11, 15, 58, 23).getTime(), DateConverter.addMinutes(new GregorianCalendar(2018, Calendar.SEPTEMBER, 11, 15, 53, 23).getTime(), 5));
    }

    @Test
    void roundToNearestMinutes() {
        assertEquals(new GregorianCalendar(2018, Calendar.SEPTEMBER, 11, 15, 55, 0).getTime(), DateConverter.roundToNearestMinutes(new GregorianCalendar(2018, Calendar.SEPTEMBER, 11, 15, 57, 23).getTime(), 5));
    }
}
