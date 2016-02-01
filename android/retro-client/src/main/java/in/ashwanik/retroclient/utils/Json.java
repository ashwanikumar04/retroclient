package in.ashwanik.retroclient.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

public class Json {

    public static <T> String serialize(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);

    }

    public static <T> T deSerialize(String jsonString, Class<T> tClass) throws ClassNotFoundException {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, tClass);
    }

    public static <T> List<T> deSerializeList(String data, Type type) {
        return new Gson().fromJson(data, type);
    }

}
