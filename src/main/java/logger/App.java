package logger;

import logger.service.Logger;


public class App {
    public static void main(String[] args) {
        System.out.println(Logger.getInstance());
        System.out.println(Logger.getInstance());

    }
}
