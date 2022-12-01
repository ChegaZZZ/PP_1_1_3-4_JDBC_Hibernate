package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Tony", "Montana", (byte) 42);
        userService.saveUser("Henry", "Hill", (byte) 69);
        userService.saveUser("Vito", "Corleone", (byte) 68);
        userService.saveUser("Tony", "Soprano", (byte) 47);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}