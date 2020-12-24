package com.tymoshenko.codewars;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathFinderTest {

    @ParameterizedTest
    @MethodSource("provideMazeFromFile")
    void mazePathFinder(String maze, int expectedPathLength) {
        assertEquals(expectedPathLength, PathFinder.pathFinder(maze));
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