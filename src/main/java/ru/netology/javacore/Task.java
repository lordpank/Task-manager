package ru.netology.javacore;

public class Task {
    private final OperationType type;
    private final String task;

    public Task(OperationType type, String task) {
        this.type = type;
        this.task = task;
    }

    public OperationType getType() {
        return type;
    }

    public String getTask() {
        return task;
    }
}
