package com.tymoshenko.codewars;

import java.util.HashMap;
import java.util.Map;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/51fda2d95d6efda45e00004e/train/java
 */
public class CodewarsUserRanking {
    private static final int FIRST_RANK = -8;
    private static final int LAST_RANK = 8;
    private static final int PROGRESS_PER_RANK = 100;
    private static final Map<Integer, Integer> ranks;

    // Using package-private instead of private because of Codewars automated tests.
    int rank = FIRST_RANK;
    int progress = 0;

    public void incProgress(int activity) {
        if (!ranks.containsKey(activity)) {
            throw new IllegalArgumentException(
                    String.format("Codewars ranks must be in range [%d, %d], excluding zero.", FIRST_RANK, LAST_RANK));
        }
        if (rank >= LAST_RANK) {
            return;
        }
        long score = calculateScore(activity);
        updateProgressAndRank(score);
    }

    private int calculateScore(int activity) {
        int score;
        if (isActivityLowerThanRankByLevelOrMore(activity, 2)) {
            score = 0;
        } else if (isActivityLowerThanRankByLevelOrMore(activity, 1)) {
            score = 1;
        } else if (activity == rank) {
            score = 3;
        } else {
            int d = ranks.get(activity) - ranks.get(rank);
            score = 10 * d * d;
        }
        return score;
    }

    private void updateProgressAndRank(long score) {
        if (score == 0 || rank == LAST_RANK) {
            return;
        }
        progress += score;
        while (progress >= PROGRESS_PER_RANK && rank < LAST_RANK) {
            nextRank();
            refreshProgress();
        }
    }

    private boolean isActivityLowerThanRankByLevelOrMore(int activity, int level) {
        return ranks.get(rank) - ranks.get(activity) >= level;
    }

    private void nextRank() {
        rank = ranks.entrySet().stream()
                .filter(entry -> entry.getValue() == ranks.get(rank) + 1)
                .map(Map.Entry::getKey)
                .findAny()
                .orElse(LAST_RANK);
    }

    private void refreshProgress() {
        if (rank >= LAST_RANK) {
            progress = 0;
        } else {
            progress -= PROGRESS_PER_RANK;
        }
    }

    static {
        ranks = new HashMap<>();
        int rankIndex = 1;
        for (int codewarsRank = FIRST_RANK; codewarsRank <= LAST_RANK; codewarsRank++) {
            if (codewarsRank == 0) {
                continue;
            }
            ranks.put(codewarsRank, rankIndex++);
        }
    }
}
