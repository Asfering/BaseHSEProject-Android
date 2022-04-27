package org.hse.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class BaseActivity extends AppCompatActivity {

    enum ScheduleType {
        DAY,
        WEEK
    }
    enum ScheduleMode {
        STUDENT,
        TEACHER
    }

    private final static String TAG="BaseActivity";
    public static final String URL="https://api.ipgeolocation.io/ipgeo?apiKey=cf23ee11941f43e3bbfeedb1050d16c7";

    protected TextView time;
    protected Date currentTime;

    private OkHttpClient client = new OkHttpClient();

    // получение времени
    protected void getTime(){
        Request request = new Request.Builder().url(URL).build();       // запрос на URL API
        Call call = client.newCall(request);            // передаем значение апи к OkHttpClient
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call,IOException e) {        // парсим ошибку
                Log.e(TAG, "getTime", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {       // парсим ответ
                parseResponse(response);
            }
        });
    }

    // показать время
    private void showTime(Date dateTime){
        if (dateTime == null){  // если датавремя нет
            return;
        }
        currentTime=dateTime;   // если датавремя есть
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE", Locale.forLanguageTag("ru"));   // час:минуты
        time.setText(simpleDateFormat.format(currentTime));     // делаем текст
    }

    // парсим ответ
    private void parseResponse(Response response){
        Gson gson = new Gson();     // берем JSON
        ResponseBody body = response.body();        // берем тело JSON
        try{
            if(body == null){
                return;         // если запрос пустой
            }
            String string = body.string();      // передаём в текст
            Log.d(TAG, string);     // чекаем в дебагере текст
            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);      // берем время
            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();        // берем зону
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());   // передаем время в формате год.мес.ден час:мин:сек.млс
            Date dateTime = simpleDateFormat.parse(currentTimeVal);     // передаем в datetime
            runOnUiThread(() -> showTime(dateTime));            // показываем datetime полученный
        } catch (Exception e) {
            Log.e(TAG,"",e);            //ошибочка
        }
    }

    // получаем время
    public class TimeResponse{
        @SerializedName("time_zone")
        private TimeZone timeZone;
        public TimeZone getTimeZone() {return timeZone;}
        public void setTimeZone(TimeZone timeZone) {
            this.timeZone = timeZone;
        }
    }

    // получаем зону
    public class TimeZone{
        @SerializedName("current_time")
        private String currentTime;
        public String getCurrentTime() {return currentTime;}
        public void setCurrentTime(String currentTime){
            this.currentTime = currentTime;
        }
    }

    // инициализируем время, получаем. Используем в дочерних классах
    protected void initTime(){
        getTime();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}