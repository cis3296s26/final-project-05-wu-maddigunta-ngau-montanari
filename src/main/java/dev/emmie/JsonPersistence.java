package dev.emmie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonPersistence {
    // Pretty printing makes the "template" files readable for users to edit
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Generic Save Method
    public static <T> void saveToFile(String filePath, T data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generic Load Method
    public static <T> T loadFromFile(String filePath, Class<T> classOfT) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, classOfT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}