package com.tymoshenko.codewars;

import java.util.*;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/551f23362ff852e2ab000037/train/java
 * <p>
 * Lyrics...
 * <p>
 * Pyramids are amazing! Both in architectural and mathematical sense.
 * If you have a computer, you can mess with pyramids even if you are not in Egypt at the time.
 * For example, let's consider the following problem. Imagine that you have a pyramid built of numbers, like this one here:
 * <p>
 * /3/
 * \7\ 4
 * 2 \4\ 6
 * 8 5 \9\ 3
 * Here comes the task...
 * <p>
 * Let's say that the 'slide down' is the maximum sum of consecutive numbers from the top to the bottom of the pyramid.
 * As you can see, the longest 'slide down' is 3 + 7 + 4 + 9 = 23
 * <p>
 * Your task is to write a function longestSlideDown (in ruby/crystal/julia: longest_slide_down)
 * that takes a pyramid representation as argument and returns its' largest 'slide down'. For example,
 * <p>
 * longestSlideDown [[3], [7, 4], [2, 4, 6], [8, 5, 9, 3]] => 23
 * By the way...
 * <p>
 * My tests include some extraordinarily high pyramids so as you can guess, brute-force method is a bad idea unless you
 * have a few centuries to waste. You must come up with something more clever than that.
 * <p>
 * (c) This task is a lyrical version of the Problem 18 and/or Problem 67 on ProjectEuler.
 */
public class PyramidSlideDown {

    private static Map<Integer, List<Node>> nodeMap;

    private PyramidSlideDown() {
    }

    public static int longestSlideDown(int[][] pyramid) {
        initNodes(pyramid);
        Node.toTree(pyramid);
        return Node.longestSlideDown();
    }

    private static void initNodes(int[][] p) {
        nodeMap = new HashMap<>();
        for (int i = 0; i < p.length; i++) {
            LinkedList<Node> nodes = new LinkedList<>();
            nodeMap.put(i, nodes);
            for (int j = 0; j < p[i].length; j++) {
                Data data = new Data(p[i][j], i, j);
                Node node = new Node(data);
                nodes.add(node);
            }
        }
    }

    public static final class Data {

        int value;
        int row;
        int col;

        public Data(int value, int row, int col) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return row == data.row && col == data.col && value == data.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, value);
        }

        @Override
        public String toString() {
            return String.format("B-%d [%d, %d]. ", value, row, col);
        }
    }

    public static final class Node {
        Data data;

        Node left;
        Node right;

        int distance;

        public Node(Data data) {
            this.data = data;
            this.distance = data.value;
        }

        private static Node getNode(int i, int j) {
            return nodeMap.get(i).get(j);
        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return this.data.equals(((Node) obj).data);
        }

        @Override
        public String toString() {
            return String.format("%d", data.value);
        }

        static void toTree(int[][] pyramid) {
            Node root = getNode(0, 0);
            addChildren(root, pyramid);
        }

        static void addChildren(Node node, int[][] pyramid) {
            int row = node.data.row;
            int col = node.data.col;
            int nextRow = row + 1;

            node.left = getNode(nextRow, col);
            node.right = getNode(nextRow, col + 1);

            if (nextRow + 1 < pyramid.length) {
                addChildren(node.left, pyramid);
                addChildren(node.right, pyramid);
            }
        }

        static int longestSlideDown() {
            int max = 0;
            for (int i = 0; i < nodeMap.size(); i++) {
                List<Node> nodes = nodeMap.get(i);
                for (Node node : nodes) {
                    if (node.left != null && node.left.distance < node.left.data.value + node.distance) {
                        node.left.distance = node.left.data.value + node.distance;
                    }
                    if (node.right != null && node.right.distance < node.right.data.value + node.distance) {
                        node.right.distance = node.right.data.value + node.distance;
                    }
                    if (node.data.row == nodes.size() - 1 && node.distance > max) {
                        max = node.distance;
                    }
                }
            }
            return max;
        }
    }

}
