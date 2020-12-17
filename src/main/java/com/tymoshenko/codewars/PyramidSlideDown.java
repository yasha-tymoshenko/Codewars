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

    private PyramidSlideDown() {
    }

    public static int longestSlideDown(int[][] pyramid) {
        initBlocks(pyramid);
        Node root = toTree(pyramid);
        return sum(root, new Node[pyramid.length], 0);
    }

    static Map<Integer, List<Data>> blocksMap;

    public static final class Data {
        int row;
        int col;
        int value;

        public Data(int row, int col, int value) {
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
        Node left;
        Node right;
        Data data;

        public Node(Data data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("%d", data.value);
        }
    }

    static void initBlocks(int[][] p) {
        blocksMap = new HashMap<>();
        for (int i = 0; i < p.length; i++) {
            LinkedList<Data> dataList = new LinkedList<>();
            blocksMap.put(i, dataList);
            for (int j = 0; j < p[i].length; j++) {
                Data data = new Data(i, j, p[i][j]);
                dataList.add(data);
            }
        }
    }

    static Data getBlock(int i, int j) {
        return blocksMap.get(i).get(j);
    }

    static Node toTree(int[][] pyramid) {
        Data rootData = getBlock(0, 0);
        Node root = new Node(rootData);
        addChildren(root, 1, 0, pyramid);
        return root;
    }

    static void addChildren(Node node, int nextLevel, int index, int[][] pyramid) {
        node.left = new Node(getBlock(nextLevel, index));
        node.right = new Node(getBlock(nextLevel, index + 1));

        if (nextLevel + 1 < pyramid.length) {
            addChildren(node.left, nextLevel + 1, index, pyramid);
            addChildren(node.right, nextLevel + 1, index + 1, pyramid);
        }

    }

    static int sum(Node node, Node[] nodes, int index) {
        if (node == null) {
            return 0;
        }
        nodes[index] = node;
        index++;

        if (node.left == null && node.right == null) {
            System.out.print("Sum = " + Arrays.stream(nodes).mapToInt(n -> n.data.value).sum() + " ");
            System.out.println(Arrays.stream(nodes).map(Node::toString).collect(joining(", ")));
            return node.data.value;
        }

        return node.data.value + Math.max(sum(node.left, nodes, index), sum(node.right, nodes, index));
    }

}
