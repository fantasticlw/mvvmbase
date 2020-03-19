package cn.ruicz.basecore.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;

public class ShareManager {

    private static final HashMap<String, Object> mMap = new HashMap<>();

    /**
     *
     * @param modelClass    缓存对象
     * @param isPersistence 是否持久化缓存（SharedPreference）
     */
    public static void put(Object modelClass, boolean isPersistence){
        String canonicalName = modelClass.getClass().getCanonicalName();
        mMap.put(canonicalName, modelClass);

        if (isPersistence) {
            String jsonString = new Gson().toJson(modelClass);
            SPManager.INSTANCE.putString(canonicalName, jsonString);
        }
    }

    public static void put(Object modelClass){
        put(modelClass, false);
    }

    public static  <T> T get(@NonNull Class<T> modelClass) {
        String canonicalName = modelClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        Object object = mMap.get(canonicalName);

        if (modelClass.isInstance(object)) {
            //noinspection unchecked
            return (T) object;
        } else {

            String jsonString = SPManager.INSTANCE.getString(canonicalName);
            if (!TextUtils.isEmpty(jsonString)) {
                return new Gson().fromJson(jsonString, modelClass);
            }

            //noinspection StatementWithEmptyBody
            if (object != null) {
                // TODO: log a warning.
            }
        }
        return null;
    }


    public static void clear() {
        mMap.clear();
    }
}
