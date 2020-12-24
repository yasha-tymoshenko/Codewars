package com.tymoshenko.codewars.pathfinder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * You are at position [0, 0] in maze NxN and you can only move in one of the four cardinal directions
 * (i.e. North, East, South, West).
 * Return the minimal number of steps to exit position [N-1, N-1]
 * if it is possible to reach the exit from the starting position.
 * Otherwise, return false in JavaScript/Python and -1 in C++/C#/Java.
 * <p>
 * Empty positions are marked ..
 * Walls are marked W.
 * Start and exit positions are guaranteed to be empty in all test cases.
 */
class PathFinderTest {

    @ParameterizedTest
    @MethodSource("provideMazeFromFile")
    void mazePathFinder(String maze, int expectedPathLength) {
        assertEquals(expectedPathLength, PathFinder.pathFinder(maze));
    }

    @Test
    void smallestMaze() {
        assertEquals(0, PathFinder.pathFinder(".\n"));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void emptyMazes(String maze) {
        assertEquals(-1, PathFinder.pathFinder(maze));
    }

    private static Stream<Arguments> provideMazeFromFile() throws IOException {
        return Stream.of(
                ofMazeAndPathLength("a", 4),
                ofMazeAndPathLength("b", -1),
                ofMazeAndPathLength("c", 10),
                ofMazeAndPathLength("d", -1),
                ofMazeAndPathLength("snakes", 96),
                ofMazeAndPathLength("big", 198)
        );
    }

    private static Arguments ofMazeAndPathLength(String maze, int pathLength) throws IOException {
        return Arguments.of(mazeFromFile(maze), pathLength);
    }

    private static String mazeFromFile(String fileName) throws IOException {
        Path filePath = Paths.get("src/test/resources/pathfinder", fileName + ".txt");
        return Files.readString(filePath);
    }
}