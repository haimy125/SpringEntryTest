package com.myph.springentrytest.helper;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IsbnGeneratorService {

    /**
     * Generates a random valid ISBN-13 number.
     * ISBN-13 format starts with the prefix '978' followed by 9 random digits
     * and a checksum digit calculated according to the ISBN-13 standard.
     *
     * @return A valid ISBN-13 number as a String.
     */
    public String generateIsbn13() {
        Random random = new Random();
        int[] digits = new int[12];

        // Initialize ISBN-13 with prefix '978'
        digits[0] = 9;
        digits[1] = 7;
        digits[2] = 8;

        // Generate the next 9 random digits
        for (int i = 3; i < 12; i++) {
            digits[i] = random.nextInt(10);
        }

        // Calculate the checksum digit (13th digit)
        int checksum = 0;
        for (int i = 0; i < 12; i++) {
            checksum += (i % 2 == 0 ? digits[i] : digits[i] * 3);
        }
        int checkDigit = (10 - (checksum % 10)) % 10;

        // Concatenate to form the ISBN-13 string
        StringBuilder isbn = new StringBuilder();
        for (int digit : digits) {
            isbn.append(digit);
        }
        isbn.append(checkDigit);

        return isbn.toString();
    }
}
