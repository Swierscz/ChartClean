package com.example.chartclean;

import java.util.ArrayList;
import java.util.List;

public class Speed {

    public static List<Integer> getSpeedValues() {
        ArrayList<Integer> values = new ArrayList<>();
        values.add(speedToSec(3, 20));
        values.add(speedToSec(3, 20));
        values.add(speedToSec(3, 20));
        values.add(speedToSec(3, 22));
        values.add(speedToSec(3, 24));
        values.add(speedToSec(3, 26));
        values.add(speedToSec(3, 23));
        values.add(speedToSec(3, 30));
        values.add(speedToSec(3, 33));
        values.add(speedToSec(3, 31));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 39));
        values.add(speedToSec(3, 42));
        values.add(speedToSec(3, 45));
        values.add(speedToSec(3, 48));
        values.add(speedToSec(3, 55));
        values.add(speedToSec(3, 53));
        values.add(speedToSec(3, 50));
        values.add(speedToSec(3, 53));
        values.add(speedToSec(3, 50));
        values.add(speedToSec(3, 53));
        values.add(speedToSec(3, 50));
        values.add(speedToSec(3, 47));
        values.add(speedToSec(3, 45));
        values.add(speedToSec(3, 40));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 33));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 30));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 40));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 33));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 40));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 33));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 31));
        values.add(speedToSec(3, 35));
        values.add(speedToSec(3, 37));
        values.add(speedToSec(3, 39));
        values.add(speedToSec(3, 42));
        return values;
    }

    private static int speedToSec(int min, int sec) {
        return min * 60 + sec;
    }


}
