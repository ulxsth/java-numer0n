package com.ulxsth.strategy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CallNumberStrategyTest {

    @Nested
    class testCall {

        @Nested
        class NormalCases {

            @Test
            void testCall_AllCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "123";

                // Act
                var result = CallNumberStrategy.call(number, callNumber);

                // Assert
                assertEquals(3, result.getEat(), "Expected all characters to match exactly.");
                assertEquals(0, result.getBite(), "Expected no bites as there are no wrong positions.");
                assertEquals(true, result.isCorrect(), "Expected the call to be correct.");
            }

            @Test
            void testCall_NoCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "567";

                // Act
                var result = CallNumberStrategy.call(number, callNumber);

                // Assert
                assertEquals(0, result.getEat(), "Expected no matching characters in the correct positions.");
                assertEquals(0, result.getBite(), "Expected no bites as there are no matching characters.");
                assertEquals(false, result.isCorrect(), "Expected the call to be incorrect.");
            }

            @Test
            void testCall_SomeCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "127";

                // Act
                var result = CallNumberStrategy.call(number, callNumber);

                // Assert
                assertEquals(2, result.getEat(), "Expected exact matches for two characters.");
                assertEquals(0, result.getBite(), "Expected no additional characters in the wrong positions.");
                assertEquals(false, result.isCorrect(), "Expected the call to be incorrect.");
            }

            @Test
            void testCall_BiteSomeCharactersMatch() {
                // Arrange
                String number = "123";
                String callNumber = "312";

                // Act
                var result = CallNumberStrategy.call(number, callNumber);

                // Assert
                assertEquals(0, result.getEat(), "Expected no matching characters in the right positions.");
                assertEquals(3, result.getBite(), "Expected all characters to match but in wrong positions.");
                assertEquals(false, result.isCorrect(), "Expected the call to be incorrect.");
            }

            @Test
            void testCall_BothEatAndBite() {
                // Arrange
                String number = "123";
                String callNumber = "132";

                // Act
                var result = CallNumberStrategy.call(number, callNumber);

                // Assert
                assertEquals(1, result.getEat(), "Expected exact match for one character.");
                assertEquals(2, result.getBite(), "Expected two characters to match in the wrong positions.");
                assertEquals(false, result.isCorrect(), "Expected the call to be incorrect.");
            }
        }

        @Nested
        class AbnormalCases {
            @Test
            void testCall_DifferentLengths() {
                // Arrange
                String number = "123";
                String callNumber = "12345";

                // Act & Assert
                assertThrows(IllegalArgumentException.class, () -> CallNumberStrategy.call(number, callNumber),
                        "Expected an exception when strings are of different lengths.");
            }
        }
    }
}