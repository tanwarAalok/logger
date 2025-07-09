package logger;

import logger.pojo.Log;
import logger.service.Logger;


public class App {
    public static void main(String[] args) {
        Logger logger = new Logger();

        logger.addLog(new Log("First log"));
        logger.addLog(new Log("Second log"));

        logger.appendLog();

    }
}
