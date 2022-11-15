package com.maeteno.study.java.base;

public class TowNum {
    public int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            int tow = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == tow) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[0];
    }
}
