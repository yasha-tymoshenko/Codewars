package com.tymoshenko.codewars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {

    @Test
    void sampleTests() {

        String a = ".W.\n" +
                ".W.\n" +
                "...",

                b = ".W.\n" +
                        ".W.\n" +
                        "W..",

                c = "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......",

                d = "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        ".....W\n" +
                        "....W.";

        assertEquals(4, PathFinder.pathFinder(a), a);
        assertEquals(-1, PathFinder.pathFinder(b), b);
        assertEquals(10, PathFinder.pathFinder(c), c);
        assertEquals(-1, PathFinder.pathFinder(d), d);
    }

    @Test
    void snakes() {

        String maze =
                ".W...W...W...\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                ".W.W.W.W.W.W.\n" +
                "...W...W...W.";
        assertEquals(96, PathFinder.pathFinder(maze), maze);
    }

    @Test
    void large() {
        String maze =
                        ".W....W......WWWW.....W.W.W.W.W.W.WW.....W..................W......WW..W......WW..W.....WW..WW..W...\n" +
                        ".......WWW.W....W......W...W...W..W......W.W.....W...W...W.W.WWWW.......W......W..W......W..W...WWW.\n" +
                        "....W.W...W....W..W.W.W...W..W..W..WW....WW................WW.WW...W...W..WWW...WW........W..W..WWWW\n" +
                        "....W.......W.......W.WW.WW.....W..W.........W..W.WWWW.W.WW.........W....W..W.W.....W.......WW.W....\n" +
                        "W.W.......W....W....W..W.W..W.........W...........W..WW.....W...WW.W......W..WWW..W....W....W...W...\n" +
                        "..WW..W.W...W..W....W.......WW.W....W...W......WWWW.....W.W.........W...W.W.W........W.WW..WW....W.W\n" +
                        ".W...........WW......WWW.....WW........WW.......W.W...WW..WW..W.WW.W.WW..WW....W..W.WW......W...W.W.\n" +
                        "WW........W..........W.....W.W....WW..WW.......W...W.......W...W...W..W......W..W.............W..W.W\n" +
                        ".W...W..W.W........W...........W...WW....W....W.......W.W....W....W....W..W...WW.WW.W.....W.W..W.W..\n" +
                        "WWW.WW......W.....WW.......W.W.WW.W.W...W...W...W....WWWW...W..W...W...WW...W..WWWW..W...W...WW....W\n" +
                        ".....W...WW...W...WW....W...W........W....W....W.....W...W.W..W....WW...W...W...W......W.......W....\n" +
                        ".W.....W...W.WW...........WW..W.WW.W..WW...............WWW...W..W...........W...W.W.W.WW....WW...W..\n" +
                        "......W.W.WWW...WW.W.W......W.....WW...W..W.W....W.W.....W...WW..W...W...WWW...W.......WW.WWW..W...W\n" +
                        "W..W...W.W.....WW.W.W..WW.......W..W.W......WW.......W.W..WW..W..W...WWWW..........WW.W..W..........\n" +
                        ".W.W....W..W.W.W....W.WW.....W....W..W....WW.....W...W...........W..............W...W.W.........W...\n" +
                        "...W...WW....W..WWW........W...W...W.W..WW..W.........WW.WWW.W.....W..W......WW....WWW..W...W.W..W.W\n" +
                        "...W......W.W..W..WW.WW..W.....W..W.....W.......WW..W...W..WW........WW..W.W.W.....WWW.WW.W.W......W\n" +
                        "...WW...........W.WW.W.....W..W.W....W..WWW.....W....WW.W...W......W...W.W.W..W..W..W.......WW......\n" +
                        "WW.W..WW..W.W..WW.W....W....W.....W..WW...WW......W..W...W.WW...........W..W.......W......W...W.W...\n" +
                        "...W..W..WWWW......WW..............WWW..W.......WW.WW.........W.........WW..W...WWW.W..WW.W...W...W.\n" +
                        ".W.........W............WW...WW...W.....W..........WW.W....W.....W.....WW.W..W.W.W....W........WWWW.\n" +
                        "W..W.W..W......WW...W.WW.....WWW......WWW..W..W..W..W....WWW.W.W...W.W........W....W...W..WW........\n" +
                        "....W.W..W......W...W...W.....W.WW.W.W......WWWW..WW..W...WW.WW.W...W......W.W.........W............\n" +
                        "..WW.....W....W.....W..W.W...........WW.W.....W.WWW..W..W.WW....W.........W..W....WW.WW..W.W.W.W.W..\n" +
                        ".W....W..W..WWWW.....W..........W..WWW.W.......W..W....WW....W..W.W.....WW...WW.WW..W...........W..W\n" +
                        "......W.W....W....W.......W.....W.......W..W....WW..W.......WW...W.W..WWW.W.W....W..W.W.....W...W...\n" +
                        "...WW........W..WW.....WW.........W.WW.W.W........WW.........W....W.....W...W.W.....W..WW...........\n" +
                        "............W.W.W...W.......W......W...W.W.....W..WW.W..........WWW...W...WW...........WW.....W.....\n" +
                        "...W.WW.WW.WW.....W..W....W.WWWW...W.W...W.W....WW....W.W....W.........W..W.........WWW......W..W..W\n" +
                        "..W.W..W..W.....W........W........WW..W....W.W..WW.W...W..W...WWW.W.......W.......W.......W..W.W...W\n" +
                        "W....W.W.....WW..WWW....W.W..WWWW.....WW..WW............W..W...W..............W.W.....W..W...WW.W...\n" +
                        "..W...WW..WWW.WWW...W........W..........W......W.WWWW.W..............WWWWW.W.W.W.W.........W.......W\n" +
                        ".......W....W..W...W.W.W....W.WW.......W...W.W..WW.W......W........W........W.W....WW.W.W.WW....W...\n" +
                        "......WW.W....W.........W..WW..WWWW.W..............W.WW......W....W..W..W.W..W...W..W........W......\n" +
                        "..............W..W..WW....WW.W..W..W...W..W...WW......W....WWW..W..W...W...WW......W.....W...W.WW...\n" +
                        ".W.WW....W...W.........W.W.W...W....W.W......W.....WWWW.WW..W....WW...W.W..WW.W.WW.....WW.......W..W\n" +
                        "W......W..WW.W.W...........W..W...WW.........W.......W...WW....W...........W.......W..W..W..W....W..\n" +
                        ".W.W.W.W..WW......W.W............W...W......W.....W.WW...WW.W.....WW.WWW.WWW..WW...W....W........W..\n" +
                        "..W....W..W..W...W.........W.W.WW.W.....WWW.WW.W..W.WW.WW..W..W....W...W.....WW...W..WW......W.....W\n" +
                        ".W..W.W.W.WW..W.......W......WW.W...W...WWW.W.W..W......WW..........WW.....W.WWW..WWW.W..WWW.....W.W\n" +
                        "..W.....W...W..W..WWW..W..W....WW...W.WW..........WWW.W...WW....W.W......W.W..W...W.........W.....W.\n" +
                        "...W...W....WW...........WW.W........W..W.WWW....WW........W...W..W.....W........WW...W...W..W.W..W.\n" +
                        "......W.....W...W...W..W....W..W.W...W.......W.W.W...W.......W....WW.W.W..WWWW....W.WW.....W....WW..\n" +
                        "W.W..W.W.W...WW.....W..WW.......W.W.W.W......W.W....WW....W...W.....W..WW.W.W.....W.....W..WW.....W.\n" +
                        ".....W.W....W....W...W....W.W...WWW..W...W.....W..W.............W.W.W...........W....W.W..WW.......W\n" +
                        "....W...W....W.............W..W..W.W...W.....W.WW...WWW.W............W....W......WW...WWW.W....WW...\n" +
                        "W.WW....W.WW.W....W..WWW.....W.WW..WW.......W.W..W......WW...WW..............WW.W....W...WWW...W.WWW\n" +
                        ".........W................W...W......W.W.W.W.WW..W...W...W...WWWW.W......W..WW..W..WW...W...W....W.W\n" +
                        "W...WWWW.W..W.WW.W..W.W..........WW.WW.........W...WW.WW....W.........W............WW.W.WW.W.W.W....\n" +
                        "..WW.W......W.W.W.....W......W.W...........W..W.....W.W.W.........W...........WW.W...WWW...WWW.WW.W.\n" +
                        ".WW..W..........W.W........WW...W.......WW..WW..WWW..........W...W...W.......WWWW...WW...W..W....W.W\n" +
                        "....................W...W...WWWWWWWW.W..W..W.WW..WW......W.....W....W..WWWWW.WWW.W.......W...WW.WW..\n" +
                        ".W....WW.WW......WWW.......WW.....W....W.....W.WW...W.....W..WW..W.....W.W..WWW.......W......W......\n" +
                        "W........W...W..W..W.W.WW......WW....W.W.....W..W......WWW..W........W...W..W..W......W.W.WW.......W\n" +
                        "........W.W...WW.WW..W.....W...WW....W.W.....W........W.W.W....W.........W....W.W.W....WW..W......WW\n" +
                        ".W..W.WW.W.....WW.W...W....W.....WW....W.........WW...WW.....WW.............W..W.......WW...........\n" +
                        ".W...W.........WWW....W...W.W....WW......W....W....W......W.W.........WW..W.W..WW..W.WW....W...WW.WW\n" +
                        "W.WW...W.W.W....W........W..W.....WW....W.WWWW....W....W....WWW..W.WW.W......W..W..W..WWWW........W.\n" +
                        ".....WW.W.W.W...W...WW...WWWW..WWW..W.W...W.W....W....W.W.....W.W.W.......WW.W.....WW..W..W.WW.W.W.W\n" +
                        ".W..W.....W..WWWW...W...W........W....W.W...W..W.W..WW..W..W...W.....WWWW...........W...W...WWW....W\n" +
                        ".WW..W.....W..W......W.W.WWWWW..W.W...W.....W.....W..W...........W..................WW.....WW..W....\n" +
                        "...W....WW.......W....W......W........W...W.W....WWWW....W...WW...W....WW.....W..WWW........W...WW..\n" +
                        ".WW.W.WW...W........W.W....WW.........W..W.W..............W...W...W..W.................W..W...WW..WW\n" +
                        "..W.WW..W...W.W.....WWW..W.W.WWWW....W..W....W.......W.W....W...W.....W...W........W..WWW....W.WWW.W\n" +
                        "W......W..WW.WW.W.W..W...WW.....W.W..............W...W.W....W.W.WWWW..W.WW........WWW.W....W...W....\n" +
                        ".WW..WW.....WW........WW..WW....W.W....W.W....WW.WW.....W...W....W....WW.....WW........W.....WW...W.\n" +
                        "...WW.......W..................WW..W.W.W..W..W..........W.W.W...........WW..W...W.W......WW.....WW..\n" +
                        ".W.....W..W...W...W.......W.W..W.....W...W.W....WW.W.W.....W...W..WWW..W....WW...W....W....W..W...W.\n" +
                        "W.W.....W.....WW....................W.......W..........WW....W......W...W..WW....W...WW.W.....W.W.W.\n" +
                        ".W..............WW..W.......W.W...W.W.W..W.....W...W..W.W..W....W...........WWWW..W......W...W....WW\n" +
                        "W...WW.W.......W...WW..W.........W...W.W.WW.................WWW.WW.WW...........W..WW.W...W..WW...WW\n" +
                        ".W.....W....WW.W.....W..WW..W..W....W....W.W.W......WW...................WW..W........WW.W..W..W..W.\n" +
                        ".W.....W.W...WW..W.W...W......W..W...W..W.....W.W......W......W.WW..WWW...W.........W.W.WW..WWWW....\n" +
                        ".WWW......WW...W....WWW.W...W....WW.WW.W....W.WW.W...WWW.W....W...WW........W....W..W.........W.W..W\n" +
                        "...W.W.....W.W.W....W...W.W.W....W....W..W..W.W.W..W.WWW.W..W.WW.W........W....WW.W....WW.W.....W...\n" +
                        "W..WW.W..W...W.....WWWW..W...WWWW.......WW.WW.W............WWW...W..WW......W.W...W......W....W.W.W.\n" +
                        "W.W.W.W.W.......W...........W..W........................W...W..W....W......WW..........WW...W.W....W\n" +
                        "WWW...W....WWW...W....W..WW.......WW...W...W.W.W.W...WWW..W....W.....W.W.W.W..WWW....W.W........W.W.\n" +
                        "..W.W.W..WW.WWWW..W......W......W..WWWWWW...W........W.W...W.WW.WWW.....W.WW....W.W...W....WW.....W.\n" +
                        "..W.W......W.WW.W.W.W.......W...W........WW.W.WW...WW...W....W..W..W..WW.W....W...WWW........W..W.W.\n" +
                        "W..W..WW.W....W.......W...WW.......W..........W....W.WW...W.W..........W.W..W....WW..W.W..W...W.....\n" +
                        "....W...W..W.....W..W.W.....WWW...............WW..WW.WW....WW..WW.....W.W.W..W.W....W.W....W..W..WW.\n" +
                        ".W...W...W....WWW.W........W.....W..WW...W......W...W..W.W.....W.W..WW......W.W.W.W.W.........WW..WW\n" +
                        "W.W....WW..WW..WW...........W....W....W...W..W....WW.W.....W.........W...WW..W.W.WW.WW...WWW..WW..W.\n" +
                        "..W..W...W.WW.W.W....W..W...................WW...W...W......W.W..W...W.....W....W..W.W....WW.W..W.W.\n" +
                        "...W....W.....W...WW.W.WWWW.W.W...........W.W..W...W....WWW..W.WW....W.W.W.........W.W.W.W.W...W.WW.\n" +
                        ".WW..........W..W....W.....W.W..W.W......W.........W..W.WW...WWWW.W.WW..W..W..W...W..W....W.......W.\n" +
                        "W..WW..W..W..W.....WW.....W...WW...WW.W.W..W.W..W....W........WWWW...W..W..W..WW.WWW.W......W..W...W\n" +
                        "..............WW.W....WW...W.......W...W......W.............W.W........W......W.....WW....W.W.....W.\n" +
                        "WW....W..........................W.W.W....W...W.....W.WW.....WWWW.W.W.......W..WW.....W..........WWW\n" +
                        "...WW....W.W.W.WW......WW.......W..W....W.W.WWW......WW..W.....WW.W.....W.........W...W...W........W\n" +
                        ".W..WW.W.W........W...WW.....W.W.....W.W.W...W..WW.W..........W.......WWWW...W.....W..W...WWW.W..W..\n" +
                        "...WW...W.W...W............W......WW.....W..WW.WW..W..W......W....WW..W.W.....W.W.....WW...W...WWW..\n" +
                        "........W.....W..W..W.....W.........W....W.W....WW...W.WW.W..W...W.....W..W..W..WWW.........WW.....W\n" +
                        ".WWWW............W.W.W...W...W..W.W..W..........W.....WWW.W.........W.....WW.W...WW.W..........W....\n" +
                        "WW..WW.W...W.W.....WW..WW....W..W.W.WWW...WW.....W.....W....W.WW.W...WW..WW.......W.W...W.W..W..WW.W\n" +
                        "W..W..WWW...W.WW.....W.W...W..WW.WW........W..WW.......W....W..W.W...W..........W.WWW...............\n" +
                        "...W..W..W.W..W........W..W..W..W...W.W.....W...W..W.WW..WW.W..WW.W.W...W...WWW.WW..WW...W.........W\n" +
                        "...W.W.....W.....WW..WW.W.W.W.W....WW....WW...W...W....W....WW.WW......WWWW..WW.WW.......W.....WW.W.\n" +
                        "W.WW...W...W.........W..WW.W.W.W......W..W...WW.....WWWW...WW.....W....W....W......W......W..W.W....";

        assertEquals(198, PathFinder.pathFinder(maze), maze);

    }
}