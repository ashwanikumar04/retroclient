package in.ashwanik.retroclient.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Helper class for Json objects
 */
public class Json {

    /**
     * Serialize string.
     *
     * @param <T> Type of the object passed
     * @param obj Object to serialize
     * @return Serialized string
     */
    public static <T> String serialize(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);

    }

    /**
     * De-serialize a string
     *
     * @param <T>        Type of the object
     * @param jsonString Serialized string
     * @param tClass     Class of the type
     * @return De-serialized object
     * @throws ClassNotFoundException the class not found exception
     */
    public static <T> T deSerialize(String jsonString, Class<T> tClass) throws ClassNotFoundException {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, tClass);
    }

    /**
     * De-serialize to list
     *
     * @param <T>  Type of the object
     * @param data Serialized string
     * @param type Type of the object
     * @return List of converted object
     */
    public static <T> List<T> deSerializeList(String data, Type type) {
        return new Gson().fromJson(data, type);
    }

}
