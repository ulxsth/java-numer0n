package com.ulxsth.strategy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CallNumberStrategyTest {

    @Nested
    class testGetBite {

        @Nested
        class NormalCases {

            @Test
            void testAllCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "312";

                // Act
                int result = CallNumberStrategy.getBite(number, callNumber);

                // Assert
                assertEquals(3, result, "Expected all characters to match but in the wrong positions.");
            }

            @Test
            void testNoCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "567";

                // Act
                int result = CallNumberStrategy.getBite(number, callNumber);

                // Assert
                assertEquals(0, result, "Expected no matching characters.");
            }

            @Test
            void testSomeCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "132";

                // Act
                int result = CallNumberStrategy.getBite(number, callNumber);

                // Assert
                assertEquals(2, result, "Expected two matching characters in the wrong positions.");
            }
        }

        @Nested
        class AbnormalCases {

            @Test
            void testDifferentLengths() {
                // Arrange
                String number = "123";
                String callNumber = "12345";

                // Act & Assert
                assertThrows(StringIndexOutOfBoundsException.class, () -> CallNumberStrategy.getBite(number, callNumber),
                        "Expected an exception when strings are of different lengths.");
            }

            @Test
            void testEmptyStrings() {
                // Arrange
                String number = "";
                String callNumber = "";

                // Act
                int result = CallNumberStrategy.getBite(number, callNumber);

                // Assert
                assertEquals(0, result, "Expected no bites as both numbers are empty.");
            }
        }
    }

    @Nested
    class testGetEat {

        @Nested
        class NormalCases {

            @Test
            void testAllCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "123";

                // Act
                int result = CallNumberStrategy.getEat(number, callNumber);

                // Assert
                assertEquals(3, result, "Expected all characters to match exactly.");
            }

            @Test
            void testNoCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "567";

                // Act
                int result = CallNumberStrategy.getEat(number, callNumber);

                // Assert
                assertEquals(0, result, "Expected no matching characters.");
            }

            @Test
            void testSomeCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "127";

                // Act
                int result = CallNumberStrategy.getEat(number, callNumber);

                // Assert
                assertEquals(2, result, "Expected exact matches for two characters.");
            }
        }

        @Nested
        class AbnormalCases {

            @Test
            void testDifferentLengths() {
                // Arrange
                String number = "123";
                String callNumber = "12345";

                // Act & Assert
                assertThrows(StringIndexOutOfBoundsException.class, () -> CallNumberStrategy.getEat(number, callNumber),
                        "Expected an exception when strings are of different lengths.");
            }
        }
    }
}