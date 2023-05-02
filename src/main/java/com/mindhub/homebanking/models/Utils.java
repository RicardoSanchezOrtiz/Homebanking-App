package com.mindhub.homebanking.models;

import java.util.Random;

public class Utils {
    public String accNumber(){
        return Integer.toString(10000000 + new Random().nextInt(90000000));
    }

    public String cardNumber(){
        return (Integer.toString(1000 + new Random().nextInt(9000)) + "-" + Integer.toString(1000 + new Random().nextInt(9000)) + "-"+ Integer.toString(1000 + new Random().nextInt(9000)) + "-"+ Integer.toString(1000 + new Random().nextInt(9000)));
    }

    public static String ccv(){
        return Integer.toString(100 + new Random().nextInt(900));
    }
}
