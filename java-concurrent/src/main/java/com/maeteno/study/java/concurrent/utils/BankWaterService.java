package com.maeteno.study.java.concurrent.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Map.Entry;
import java.util.concurrent.*;

/**
 * 银行流水处理服务类
 *
 * @author Alan.Fu
 */
@Slf4j
public class BankWaterService implements Runnable {
    /**
     * 假设只有 4 个sheet， 所以只启动 4 个线程
     */
    public final ExecutorService executor = Executors.newFixedThreadPool(4);
    /**
     * 创建 4 个屏障，处理完之后执行当前类的 run 方法
     */
    private final CyclicBarrier c = new CyclicBarrier(4, this);
    /**
     * 保存每个 sheet 计算出的银流结果
     */
    private final ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        BankWaterService bankWaterCount = new BankWaterService();
        bankWaterCount.count();
        bankWaterCount.executor.shutdown();
    }

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                // 计算当前 sheet 的银流数据，计算代码省略
                sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                // 银流计算完成， 插入一个屏障
                try {
                    c.await();
                    log.info("计算完成: {}", Thread.currentThread().getId());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    @Override
    public void run() {
        int result = 0;
        // 汇总每个 sheet 计算出的结果
        for (Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            result += sheet.getValue();
        }
        // 将结果输出
        sheetBankWaterCount.put("result", result);
        log.info("result: {}", result);
    }
}
