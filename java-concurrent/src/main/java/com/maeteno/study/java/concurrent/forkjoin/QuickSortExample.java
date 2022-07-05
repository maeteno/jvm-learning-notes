package com.maeteno.study.java.concurrent.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class QuickSortExample extends RecursiveTask<List<Integer>> {
    private final List<Integer> list;
    private final int left;
    private final int right;

    public QuickSortExample(List<Integer> list) {
        this(list, 0, list.size());
    }

    public QuickSortExample(List<Integer> list, Integer left, Integer right) {
        this.list = list;
        this.left = left;
        this.right = right;
    }

    @Override
    protected List<Integer> compute() {
        if (list.size() <= 1) {
            return list;
        }

        int partition = partition();

        List<Integer> listLeft = list.subList(left, partition + 1);
        List<Integer> listRight = list.subList(partition + 1, right);
        var leftSort = new QuickSortExample(listLeft);
        var rightSort = new QuickSortExample(listRight);

        ForkJoinTask<List<Integer>> task = leftSort.fork();

        List<Integer> rightList = rightSort.compute();
        List<Integer> leftList = task.join();

        ArrayList<Integer> result = new ArrayList<>();
        result.addAll(leftList);
        result.addAll(rightList);

        return result;
    }

    private int partition() {
        int index = left + 1;

        Integer current = list.get(left);
        for (int i = index; i < right; i++) {
            if (list.get(i) < current) {
                swap(list, i, index);
                index++;
            }
        }

        swap(list, left, index - 1);

        return index - 1;
    }

    private void swap(List<Integer> list, int a, int b) {
        Integer temp = list.get(b);
        list.set(b, list.get(a));
        list.set(a, temp);
    }
}
