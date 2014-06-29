package cn.android.zonda.retrofit.data;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class WeatherHistoryResult {

    private String                         message;

    private String                         cod;

    @SerializedName("list")
    private ArrayList<WeatherHistoryModel> historyArray;

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final String getCod() {
        return cod;
    }

    public final void setCod(String cod) {
        this.cod = cod;
    }

    public final ArrayList<WeatherHistoryModel> getHistoryArray() {
        return historyArray;
    }

    public final void setHistoryArray(ArrayList<WeatherHistoryModel> historyArray) {
        this.historyArray = historyArray;
    }
    
    public String toJson(){
        
        Gson gson = new GsonBuilder().create();
        
        Type type = new TypeToken<WeatherHistoryResult>(){}.getType();
        
        return gson.toJson(this, type);
    }
}
