package org.hse.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements OnClickListener, SensorEventListener{

    // Сохранение и загрузка
    EditText name;
    TextView tvText;
    Button btnSave;

    // Сенсоры
    Button btnSensors;
    final String SAVED_TEXT = "saved_text";
    SharedPreferences sPref;
    SensorManager sensorManager;
    Sensor light;
    TextView lightNowText;

    // Камера
    final int picId = 123;
    Button cameraOpenId;
    ImageView clickImageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Сенсоры
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightNowText = (TextView) findViewById(R.id.txtViewLightNow);
        btnSensors = (Button) findViewById(R.id.btnAllSensors);

        btnSensors.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {showSensors();}
        });

        // Сохранение и загрузка имени
        name = (EditText) findViewById(R.id.editTextTextPersonName);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        loadText(); // загружаем текст по загрузке формы

        // Камера
        cameraOpenId = (Button)findViewById(R.id.btnDoPhoto);
        clickImageId = (ImageView)findViewById(R.id.imageView);

        cameraOpenId.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, picId);
            }
        });
    }

    // Камера
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == picId) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            clickImageId.setImageBitmap(photo);
        }
    }

    // Переходим на все сенсоры
    private void showSensors() {
        Intent intent = new Intent(this, SensorsActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){  // Свич на случай доп кнопок.
            case R.id.btnSave:
                saveText();
                break;
        }
    }

    // сохраняем текст
    void saveText(){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("SAVED_TEXT", name.getText().toString());
        ed.commit();    // сохранение
        Toast.makeText(this, "Text Saved", Toast.LENGTH_SHORT).show();
    }

    // загружаем текст
    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString("SAVED_TEXT", "");
        name.setText(savedText);
    }

    // Изменение сенсора
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float lux = sensorEvent.values[0];
        lightNowText.setText(lux + " lux");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}