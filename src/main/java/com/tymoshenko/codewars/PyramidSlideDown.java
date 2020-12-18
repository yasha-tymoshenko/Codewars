package com.tymoshenko.codewars;

import java.util.*;

import static java.util.stream.Collectors.joining;

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

    private static Node[] longestSlideDownNodes;

    private PyramidSlideDown() {
    }

    public static int longestSlideDown(int[][] pyramid) {
        Data.initBlocks(pyramid);
        longestSlideDownNodes = new Node[pyramid.length];
        Node tree = Node.toTree(pyramid);
        return Node.sum(tree, 0);
    }

    public static final class Data {

        private static Map<Integer, List<Data>> dataMap;

        int value;
        int row;
        int col;

        public Data(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        public static void initBlocks(int[][] p) {
            dataMap = new HashMap<>();
            for (int i = 0; i < p.length; i++) {
                LinkedList<Data> dataList = new LinkedList<>();
                dataMap.put(i, dataList);
                for (int j = 0; j < p[i].length; j++) {
                    Data data = new Data(i, j, p[i][j]);
                    dataList.add(data);
                }
            }
        }

        public static Data getBlock(int i, int j) {
            return dataMap.get(i).get(j);
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

        public Node(Data data) {
            this.data = data;
        }

        static Node toTree(int[][] pyramid) {
            Data rootData = Data.getBlock(0, 0);
            Node root = new Node(rootData);
            addChildren(root, pyramid);
            return root;
        }

        static void addChildren(Node node, int[][] pyramid) {
            int row = node.data.row;
            int col = node.data.col;
            int nextRow = row + 1;

            node.left = new Node(Data.getBlock(nextRow, col));
            node.right = new Node(Data.getBlock(nextRow, col + 1));

            if (nextRow + 1 < pyramid.length) {
                addChildren(node.left, pyramid);
                addChildren(node.right, pyramid);
            }
        }

        static int sum(Node node, int nodeHeight) {
            if (node == null) {
                return 0;
            }
            longestSlideDownNodes[nodeHeight] = node;
            nodeHeight++;

            if (node.isLeaf()) {
                printSum();
                printLongestSlideDown();
                return node.data.value;
            }

            return node.data.value + Math.max(sum(node.left, nodeHeight), sum(node.right, nodeHeight));
        }

        private static void printSum() {
            System.out.print("Sum = " + Arrays.stream(longestSlideDownNodes)
                    .mapToInt(n -> n.data.value).sum() + " ");
        }

        private static void printLongestSlideDown() {
            System.out.println(Arrays.stream(longestSlideDownNodes)
                    .map(Node::toString).collect(joining(", ")));
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public String toString() {
            return String.format("%d", data.value);
        }
    }

}
