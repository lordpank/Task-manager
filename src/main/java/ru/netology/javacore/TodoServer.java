package ru.netology.javacore;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TodoServer {
    private final int port;
    private final Todos todos;
    private OperationType action = OperationType.START;

    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Запуск сервера " + port + "...");
            while (!action.equals(OperationType.END)) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    Gson gson = new Gson();
                    Task task = gson.fromJson(in.readLine(), Task.class);
                    action = task.getType();
                    if (task.getType() == null) {
                        throw new IllegalStateException("Не корректный ввод");
                    }
                    switch (task.getType()) {
                        case ADD: {
                            todos.addTask(task.getTask());
                            break;
                        }
                        case REMOVE: {
                            todos.removeTask(task.getTask());
                            break;
                        }
                        case RESTORE: {
                            todos.restoreTask();
                            break;
                        }
                        default: {
                            System.out.println("Сервер остановлен!");
                        }
                    }
                    out.println(todos.getAllTasks());
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
