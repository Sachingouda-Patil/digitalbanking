package com.xyzbank.digitalbanking.util;

import java.util.Random;

public class AccountNumberGenerator {
    public static String generateAccountNumber() {
        long randomNumber = Math.abs(new Random().nextLong() % 1000000000000L);
        return String.format("ABNA%012d", randomNumber);
    }
}
