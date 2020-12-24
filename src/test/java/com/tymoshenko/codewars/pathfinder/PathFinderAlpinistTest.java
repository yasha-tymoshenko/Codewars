package com.tymoshenko.codewars.pathfinder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderAlpinistTest {

    @Test
    void sampleTests() {

        String a = "000\n" +
                "000\n" +
                "000",

                b = "010\n" +
                        "010\n" +
                        "010",

                c = "010\n" +
                        "101\n" +
                        "010",

                d = "0707\n" +
                        "7070\n" +
                        "0707\n" +
                        "7070",

                e = "700000\n" +
                        "077770\n" +
                        "077770\n" +
                        "077770\n" +
                        "077770\n" +
                        "000007",

                f = "777000\n" +
                        "007000\n" +
                        "007000\n" +
                        "007000\n" +
                        "007000\n" +
                        "007777",

                g = "000000\n" +
                        "000000\n" +
                        "000000\n" +
                        "000010\n" +
                        "000109\n" +
                        "001010",

                h = "8\n",


                i =     "328193837181180478\n" +
                        "701813772880129216\n" +
                        "141378254125027334\n" +
                        "196616776954351753\n" +
                        "895077647871006244\n" +
                        "184103179345022814\n" +
                        "892588867146270406\n" +
                        "605527041970416810\n" +
                        "988782530907588686\n" +
                        "268784996885033287\n" +
                        "420559616386464226\n" +
                        "432540596826568229\n" +
                        "359702155121931730\n" +
                        "009922623574974672\n" +
                        "290998266920667698\n" +
                        "294632226986682816\n" +
                        "347424608642463728\n" +
                        "579316793414725367";


//        assertEquals(0, PathFinderAlpinist.pathFinder(a));
//        assertEquals(2, PathFinderAlpinist.pathFinder(b));
//        assertEquals(4, PathFinderAlpinist.pathFinder(c));
//        assertEquals(42, PathFinderAlpinist.pathFinder(d));
//        assertEquals(14, PathFinderAlpinist.pathFinder(e));
//        assertEquals(0, PathFinderAlpinist.pathFinder(f));
//        assertEquals(4, PathFinderAlpinist.pathFinder(g));
//        assertEquals(0, PathFinderAlpinist.pathFinder(h));
        assertEquals(48, PathFinderAlpinist.pathFinder(i));
    }
}