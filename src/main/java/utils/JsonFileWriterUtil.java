package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class JsonFileWriterUtil {
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public <T> void write(T object, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(gson.toJson(object, object.getClass()));
        fileWriter.close();
    }
}
