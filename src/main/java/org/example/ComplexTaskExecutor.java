package org.example;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.concurrent.*;

@AllArgsConstructor
class ComplexTaskExecutor {

    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        ArrayList<String> results = new ArrayList<>();
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("All tasks have completed. Results: " + results);
        });
        for (int i = 0; i < numberOfTasks; i++) {
            int taskId = i;
            executorService.execute(() -> {
                ComplexTask task = new ComplexTask(taskId);
                String result = task.execute();
                synchronized (results) {
                    results.add(result);
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}