package org.hse.android;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentActivity extends AppCompatActivity {

    TextView time;
    TextView textPara;
    TextView discipline;
    TextView cabinet;
    TextView corp;
    TextView teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        final Spinner spinner = findViewById(R.id.SpinnerGroup);

        List<StudyGroup> groups = new ArrayList<>();
        initGroupList(groups);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                Object item = adapter.getItem(selectedItemPosition);
                Log.d(TAG, "Selected item: " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });

        time = findViewById(R.id.textTime);
        initTime();

        textPara = findViewById(R.id.textPara);
        discipline = findViewById(R.id.textDiscipline);
        cabinet = findViewById(R.id.textCab);
        corp = findViewById(R.id.textCampus);
        teacher = findViewById(R.id.textTeacher);

        initData();
    }


    private void initGroupList(List<StudyGroup> groups){
        groups.add(new StudyGroup(1, "ПИ-19-1"));
        groups.add(new StudyGroup(2, "ПИ-19-2"));
        groups.add(new StudyGroup(3, "ПИ-19-3"));
    }

    private void initTime(){
        Date CurrentTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        time.setText(simpleDateFormat.format(CurrentTime));
    }

    private void initData(){
        textPara.setText("Нет пар");
        discipline.setText("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        teacher.setText("Преподаватель");
    }


    static class StudyGroup {
        private Integer id;
        private String name;

        public StudyGroup(Integer id, String name){
            this.id  = id;
            this.name = name;
        }

        public Integer getId(){
            return id;
        }

        public void setId(Integer id){
            this.id = id;
        }

        @Override
        public String toString(){
            return name;
        }

        public String getName(){
            return name;
        }

        public void setName(String name){
            this.name = name;
        }
    }
}