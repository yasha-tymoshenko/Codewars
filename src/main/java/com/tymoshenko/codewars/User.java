package com.tymoshenko.codewars;

import java.util.HashMap;
import java.util.Map;

/**
 * 4 kyu
 * <p>
 * https://www.codewars.com/kata/51fda2d95d6efda45e00004e/train/java
 * Write a class called User that is used to calculate the amount that a user will progress through a ranking system
 * similar to the one Codewars uses.
 * <p>
 * Business Rules:
 * <p>
 * A user starts at rank -8 and can progress all the way to 8.
 * There is no 0 (zero) rank. The next rank after -1 is 1.
 * Users will complete activities. These activities also have ranks.
 * Each time the user completes a ranked activity the users rank progress is updated based off of the activity's rank
 * The progress earned from the completed activity is relative to what the user's current rank
 * is compared to the rank of the activity
 * A user's rank progress starts off at zero, each time the progress reaches 100 the user's rank is upgraded to
 * the next level.
 * Any remaining progress earned while in the previous rank will be applied towards the next rank's progress
 * (we don't throw any progress away).
 * The exception is if there is no other rank left to progress towards
 * (Once you reach rank 8 there is no more progression).
 * <p>
 * A user cannot progress beyond rank 8.
 * The only acceptable range of rank values is -8,-7,-6,-5,-4,-3,-2,-1,1,2,3,4,5,6,7,8.
 * Any other value should raise an error.
 * <p>
 * The progress is scored like so:
 * <p>
 * Completing an activity that is ranked the same as that of the user's will be worth 3 points.
 * Completing an activity that is ranked one ranking lower than the user's will be worth 1 point.
 * Any activities completed that are ranking 2 levels or more lower than the user's ranking will be ignored
 * Completing an activity ranked higher than the current user's rank will accelerate the rank progression.
 * The greater the difference between rankings the more the progression will be increased.
 * The formula is 10 * d * d where d equals the difference in ranking between the activity and the user.
 * Logic Examples:
 * <p>
 * If a user ranked -8 completes an activity ranked -7 they will receive 10 progress
 * If a user ranked -8 completes an activity ranked -6 they will receive 40 progress
 * If a user ranked -8 completes an activity ranked -5 they will receive 90 progress
 * If a user ranked -8 completes an activity ranked -4 they will receive 160 progress,
 * resulting in the user being upgraded to rank -7 and having earned 60 progress towards their next rank.
 * <p>
 * If a user ranked -1 completes an activity ranked 1 they will receive 10 progress (remember, zero rank is ignored)
 * Code Usage Examples:
 * <p>
 * User user = new User();
 * user.rank; // => -8
 * user.progress; // => 0
 * user.incProgress(-7);
 * user.progress; // => 10
 * user.incProgress(-5); // will add 90 progress
 * user.progress; // => 0 // progress is now zero
 * user.rank; // => -7 // rank was upgraded to -7
 */
public class User {
    private static final int FIRST_RANK = -8;
    private static final int LAST_RANK = 8;
    private static final int PROGRESS_PER_RANK = 100;
    private static final Map<Integer, Integer> ranks;

    static {
//        ranks = IntStream.rangeClosed(FIRST_RANK, LAST_RANK)
//                .boxed()
//                .filter(i -> i != 0)
//                .collect(groupingBy(rank -> rank, counting())); - Why does counting return 1 for each rank instead of incrementing it by 1 ?
        ranks = new HashMap<>();
        int rankIndex = 1;
        for (int codewarsRank = FIRST_RANK; codewarsRank <= LAST_RANK; codewarsRank++) {
            if (codewarsRank == 0) {
                continue;
            }
            ranks.put(codewarsRank, rankIndex++);
        }
    }

    // Using package private because of Codewars automated tests.
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
}
