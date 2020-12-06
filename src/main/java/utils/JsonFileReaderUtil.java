package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonFileReaderUtil {
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public <T> T read(File file, Class<T> classOfT) throws FileNotFoundException {
        return gson.fromJson(new FileReader(file), classOfT);
    }

    public <T> T read(String filePath, Class<T> classOfT) throws FileNotFoundException {
        return read(new File(filePath), classOfT);
    }
}
