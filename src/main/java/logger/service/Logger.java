package logger.service;

import logger.data.Datastore;
import logger.data.FileStore;
import logger.enums.Severity;
import logger.pojo.Log;
import logger.utils.DeepCopyUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger {

    private Datastore datastore = new FileStore();

    private Set<Log> logTrackSet = new HashSet<>();

    private Queue<Set<Log>> logsProcessingQueue = new ArrayDeque<>();

    private Integer timeout;

    ExecutorService service = Executors.newFixedThreadPool(10);

    private static Logger logger = null;

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void addLog(Log log){
        synchronized (Logger.class) {
            Timestamp timestamp = new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            Thread currentThread = Thread.currentThread();

            StackTraceElement[] elements =  currentThread.getStackTrace();
            StringBuilder builder = new StringBuilder();

            for(StackTraceElement element : elements){
                builder.append("\tat").append(element.toString()).append("\n");
            }

            String stackTrace = builder.toString();

            log.setTimestamp(timestamp);
            log.setStackTrace(stackTrace);
            log.setThreadId(Long.toString(currentThread.threadId()));
            log.setThreadName(currentThread.getName());

            put(logTrackSet, log);
        }
    }

    public void appendLog(){
        synchronized (Logger.class) {
            try{
                Set<Log> logTrackSetCopied = DeepCopyUtil.deepCopy(logTrackSet);
                put(logsProcessingQueue, logTrackSetCopied);
                flushLogTrackSet();
                service.submit(() -> {
                    try{
                        datastore.appendLog(logsProcessingQueue.peek());
                        logsProcessingQueue.remove();
                    } catch (Exception e){}
                });
            } catch (Exception e){}
        }
    }

    private void flushLogProcessingQueue(){
        logsProcessingQueue.clear();
    }

    private void flushLogTrackSet(){
        logTrackSet.clear();
    }

    private <T> void put(Collection<T> collection, T item){
        collection.add(item);
    }

    private void deleteLogs(){

    }



}
