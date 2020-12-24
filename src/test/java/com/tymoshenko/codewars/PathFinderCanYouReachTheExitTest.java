package com.tymoshenko.codewars;

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

import static org.junit.jupiter.api.Assertions.*;

class PathFinderCanYouReachTheExitTest {

    @ParameterizedTest
    @MethodSource("provideMazeFromFile")
    void mazePathFinder(String maze, boolean reachable) {
        assertEquals(reachable, PathFinderCanYouReachTheExit.pathFinder(maze));
    }

    @Test
    void smallestMaze() {
        assertTrue(PathFinderCanYouReachTheExit.pathFinder(".\n"));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void emptyMazes(String maze) {
        assertFalse(PathFinderCanYouReachTheExit.pathFinder(maze));
    }

    private static Stream<Arguments> provideMazeFromFile() throws IOException {
        return Stream.of(
                ofMazeAndPathLength("a", true),
                ofMazeAndPathLength("b", false),
                ofMazeAndPathLength("c", true),
                ofMazeAndPathLength("d", false),
                ofMazeAndPathLength("snakes", true),
                ofMazeAndPathLength("big", true)
        );
    }

    private static Arguments ofMazeAndPathLength(String maze, boolean reachable) throws IOException {
        return Arguments.of(mazeFromFile(maze), reachable);
    }

    private static String mazeFromFile(String fileName) throws IOException {
        Path filePath = Paths.get("src/test/resources/pathfinder", fileName + ".txt");
        return Files.readString(filePath);
    }

}