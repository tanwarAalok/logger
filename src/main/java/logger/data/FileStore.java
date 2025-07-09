package logger.data;

import logger.pojo.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

public class FileStore implements Datastore{

    @Override
    public void addLog(Log log){

    }

    @Override
    public void appendLog(Collection<Log> logs) throws TimeoutException {
        try{
            File file = new File("test.log");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(Log log : logs){
                oos.writeObject(log);
            }

            oos.close();
            fos.close();

        } catch (Exception e) {
            System.err.println("File not found or not able to write to file");
        }
    }

    @Override
    public void deleteLog() {
        //TODO: read file logs, delete old logs 30%, do file logo io

    }
}
