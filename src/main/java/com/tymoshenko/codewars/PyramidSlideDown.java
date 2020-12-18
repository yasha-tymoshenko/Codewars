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
    private static Map<Integer, List<Node>> nodeMap;

    private PyramidSlideDown() {
    }

    public static int longestSlideDown(int[][] pyramid) {
        initNodes(pyramid);
        longestSlideDownNodes = new Node[pyramid.length];
        Node tree = Node.toTree(pyramid);
        return Node.maxSum(tree, 0);
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

        public Node(Data data) {
            this.data = data;
        }

        private static Node getNode(int i, int j) {
            return nodeMap.get(i).get(j);
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public String toString() {
            return String.format("%d", data.value);
        }

        static Node toTree(int[][] pyramid) {
            Node root = getNode(0, 0);
            addChildren(root, pyramid);
            return root;
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

        static int maxSum(Node node, int nodeHeight) {
            if (node == null) {
                return 0;
            }
            longestSlideDownNodes[nodeHeight] = node;
            if (node.isLeaf()) {
                printSum();
                printLongestSlideDown();
                return node.data.value;
            }
            int sumLeft = maxSum(node.left, nodeHeight + 1);
            int sumRight = maxSum(node.right, nodeHeight + 1);
            return node.data.value + Math.max(sumLeft, sumRight);
        }

        private static void printSum() {
            System.out.print("Sum=" + Arrays.stream(longestSlideDownNodes)
                    .mapToInt(n -> n.data.value).sum());
        }

        private static void printLongestSlideDown() {
            System.out.println(" || " + Arrays.stream(longestSlideDownNodes)
                    .map(Node::toString).collect(joining(", ")));
        }
    }

}
