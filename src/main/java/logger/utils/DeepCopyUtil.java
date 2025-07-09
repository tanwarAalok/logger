package logger.utils;

import java.io.*;

public class DeepCopyUtil {

    public static <T> T deepCopy(T original) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(original);
        oos.flush();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        T copy = (T) ois.readObject();

        oos.close();
        bos.close();
        ois.close();
        bis.close();

        return copy;
    }
}
