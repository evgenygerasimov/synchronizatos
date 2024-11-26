package org.example;

class ComplexTask {
    private final int taskId;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public String execute() {
        System.out.println("Task " + taskId + " is being executed by thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            System.out.println("Task " + taskId + " has finished executing" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Result of task " + taskId;
    }
}