package bll.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @DisplayName("Test of the formatTime() method")
    @Test
    void formatDateTime() {
        //Arrange
        LocalDate localDate = LocalDate.of(2022,12,25);
        String expectedValue = "2022-12-25";
        //Act
        String actualValue = DateUtil.formatDateTime(localDate);
        //Assert
        Assertions.assertEquals(expectedValue,actualValue);
    }

    @DisplayName("Test of the formatTimeGUI() method")
    @Test
    void formatDateGui() {
        //Arrange
        LocalDate localDate = LocalDate.of(2022,1,24);
        String expectedValue = "24/01/2022";
        //Act
        String actualValue = DateUtil.formatDateGui(localDate);
        //Assert
        Assertions.assertEquals(expectedValue,actualValue);
    }


    @DisplayName("Test of the parseDate() method")
    @Test
    void parseDate() {
        //Arrange
        String strDate = "2022-12-25";
        LocalDate expectedDate = LocalDate.of(2022,12,25);
        //Act
        LocalDate actualDate = DateUtil.parseDate(strDate);
        //Assert
        Assertions.assertEquals(expectedDate,actualDate);
    }

    @DisplayName("Test of the parseDate_GUI() method")
    @Test
    void parseDate_GUI() {
        //Arrange
        String strDate = "24/01/2022";
        LocalDate expectedDate = LocalDate.of(2022,1,24);
        //Act
        LocalDate actualDate = DateUtil.parseDate_GUI(strDate);
        //Assert
         Assertions.assertEquals(expectedDate,actualDate);
    }

    @DisplayName("Test of the validDate() method")
    @Test
    void validDate() {
        //Arrange
        String strDate = "24/01/2022";
        boolean expectedValue = true;
        //Act
        boolean actualValue = DateUtil.validDate(strDate);
        //Assert
        assertTrue(actualValue);
    }

}