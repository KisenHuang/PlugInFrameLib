package com.kisen.plugframelib.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

/**
 * SharedPreferences 工具类
 * Created by huang on 2017/4/18.
 */
public class SharedPreferencesUtil {

    private Context context;

    private final String subTAG = getClass().getSimpleName();

    @SuppressWarnings("unused")
    public SharedPreferencesUtil(Context context) {
        this.context = context;
    }

    /**
     * 写入数据
     */
    @SuppressWarnings("unused")
    public void write(Map<String, Object> map) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share != null) {
            SharedPreferences.Editor editor = share.edit();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object obj = entry.getValue();
                if (obj instanceof String) {
                    editor.putString(entry.getKey(), entry.getValue().toString());
                } else if (obj instanceof Integer) {
                    editor.putInt(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
                } else if (obj instanceof Boolean) {
                    editor.putBoolean(entry.getKey(), Boolean.getBoolean(entry.getValue().toString()));
                } else if (obj instanceof Float) {
                    editor.putFloat(entry.getKey(), Float.parseFloat(entry.getValue().toString()));
                } else if (obj instanceof Long) {
                    editor.putLong(entry.getKey(), Long.parseLong(entry.getValue().toString()));
                }
            }
            editor.apply();
        }
    }

    /**
     * 清除shared
     */
    @SuppressWarnings("unused")
    public void clear() {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.clear();
        editor.apply();
    }

    @SuppressWarnings("unused")
    public void writeString(String key, String value) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share != null) {
            SharedPreferences.Editor editor = share.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    @SuppressWarnings("unused")
    public void writeInt(String key, int value) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share != null) {
            SharedPreferences.Editor editor = share.edit();
            editor.putInt(key, value);
            editor.apply();
        }
    }

    @SuppressWarnings("unused")
    public void writeBoolean(String key, Boolean value) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share != null) {
            SharedPreferences.Editor editor = share.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }
    }

    @SuppressWarnings("unused")
    public void delete(String key) {
        try {
            SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
            if (share != null) {
                SharedPreferences.Editor editor = share.edit();
                editor.remove(key);
                editor.apply();
            }
        } catch (Exception e) {
            Log.e(subTAG, e.getMessage());
        }
    }

    @SuppressWarnings("unused")
    public String readString(String key) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share == null || !share.contains(key)) {
            return null;
        }
        return share.getString(key, null);
    }

    @SuppressWarnings("unused")
    public Integer readInt(String key) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share == null || !share.contains(key)) {
            return 0;
        }
        return share.getInt(key, 0);
    }

    @SuppressWarnings("unused")
    public Long readLong(String key) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share == null || !share.contains(key)) {
            return null;
        }
        return share.getLong(key, 0L);
    }

    @SuppressWarnings("unused")
    public Boolean readBoolean(String key) {
        return readBoolean(key, false);
    }

    @SuppressWarnings("all")
    public Boolean readBoolean(String key, boolean defaultValue) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share == null || !share.contains(key)) {
            return defaultValue;
        }
        return share.getBoolean(key, defaultValue);
    }

    @SuppressWarnings("unused")
    public Float readFloat(String key) {
        SharedPreferences share = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (share == null || !share.contains(key)) {
            return null;
        }
        return share.getFloat(key, 0F);
    }

    @SuppressWarnings("unused")
    public String readString(String arg0, String key) {
        SharedPreferences share = context.getSharedPreferences(arg0, Context.MODE_PRIVATE);
        if (share == null || !share.contains(key)) {
            return null;
        }
        return share.getString(key, null);
    }
}
