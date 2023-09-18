package com.example.motivegoal;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import androidx.core.util.Pair;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class plusbuttonpg extends AppCompatActivity {

    private TextView dateTextView;
    private String startDate;
    private String endDate;
    private TextView timeTextView;
    private Calendar calendar;
    private Button exerciseButton;
    private Button studyButton;
    private boolean isExerciseSelected = true;

    private boolean isMondaySelected = false;
    private boolean isTuesdaySelected = false;
    private boolean isWednesdaySelected = false;
    private boolean isThursdaySelected = false;
    private boolean isFridaySelected = false;
    private boolean isSaturdaySelected = false;
    private boolean isSundaySelected = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusbuttonpg);





        // 뒤로 가기 버튼
        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        // 목표 제목 입력 EditText
        EditText titleEditText = findViewById(R.id.title2);
        // dateRadioGroup 변수를 정의하고 초기화
        RadioGroup dateRadioGroup = findViewById(R.id.rdG_date);

        // 글자 수 세는 TextView
        TextView characterCountTextView = findViewById(R.id.count);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int currentLength = s.length();
                characterCountTextView.setText(currentLength + "/40");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Firebase 데이터베이스 참조 가져오기
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference goalsRef = database.getReference("AllGoalItem");

        // 개설하기 버튼
        Button makeButton = findViewById(R.id.make);
        makeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자로부터 입력 받기
                String goalTitle = titleEditText.getText().toString();
                String selectedDate = dateTextView.getText().toString();
                String selectedTime = timeTextView.getText().toString();
                int selectedRadioButtonId = dateRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedFrequency = selectedRadioButton.getText().toString();

                // 목표 데이터 생성
                AllGoalItem newGoal = new AllGoalItem(goalTitle, 0, "", selectedDate, selectedTime, selectedFrequency);

                // Firebase에 데이터 저장
                String goalKey = goalsRef.push().getKey();
                goalsRef.child(goalKey).setValue(newGoal);

                // 전체목표로 이동
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 먼저, 공부 버튼과 운동 버튼을 초기화합니다.
        studyButton = findViewById(R.id.study_btn);
        exerciseButton = findViewById(R.id.exercise_btn);

        // 공부 버튼 클릭 시
        studyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExerciseSelected = false;
                studyButton.setBackgroundResource(R.drawable.makebutton); // 선택된 상태의 드로어블을 적용
                exerciseButton.setBackgroundResource(R.drawable.makebutton2); // 선택되지 않은 상태의 드로어블을 적용
                // 텍스트 색상 변경
                studyButton.setTextColor(Color.parseColor("#FFFFFF")); // 선택된 상태일 때의 텍스트 색상
                exerciseButton.setTextColor(Color.parseColor("#999999")); // 선택되지 않은 상태일 때의 텍스트 색상

            }

        });

        // 운동 버튼 클릭 시
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExerciseSelected = true;
                exerciseButton.setBackgroundResource(R.drawable.makebutton); // 선택된 상태의 드로어블을 적용
                studyButton.setBackgroundResource(R.drawable.makebutton2); // 선택되지 않은 상태의 드로어블을 적용
                // 텍스트 색상 변경
                exerciseButton.setTextColor(Color.parseColor("#FFFFFF")); // 선택된 상태일 때의 텍스트 색상
                studyButton.setTextColor(Color.parseColor("#999999")); // 선택되지 않은 상태일 때의 텍스트 색상
            }
        });









        RadioGroup radioGroup = findViewById(R.id.rdG_date);
        RelativeLayout radiogaga = findViewById(R.id.radiogaga);
        LinearLayout weeklyDaySelectionLayout = findViewById(R.id.weeklyDaySelectionLayout);
        RelativeLayout daySelectLayout = findViewById(R.id.dayselect);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                if (checkedId == R.id.rdB_day2) {
                    // 매주 선택되었을 때
                    params.addRule(RelativeLayout.BELOW, R.id.weeklyDaySelectionLayout);

                    // daySelectLayout을 weeklyDaySelectionLayout 밑에 두기
                    RelativeLayout.LayoutParams daySelectParams = (RelativeLayout.LayoutParams) daySelectLayout.getLayoutParams();
                    daySelectParams.addRule(RelativeLayout.BELOW, R.id.weeklyDaySelectionLayout);
                    daySelectLayout.setLayoutParams(daySelectParams);

                    // weeklyDaySelectionLayout 보이기
                    weeklyDaySelectionLayout.setVisibility(View.VISIBLE);
                } else {
                    // 매주 선택되지 않았을 때
                    params.addRule(RelativeLayout.BELOW, R.id.radiogaga);

                    // daySelectLayout을 radiogaga 아래에 두기
                    RelativeLayout.LayoutParams daySelectParams = (RelativeLayout.LayoutParams) daySelectLayout.getLayoutParams();
                    daySelectParams.addRule(RelativeLayout.BELOW, R.id.radiogaga);
                    daySelectLayout.setLayoutParams(daySelectParams);

                    // weeklyDaySelectionLayout 숨기기
                    weeklyDaySelectionLayout.setVisibility(View.GONE);
                }
                daySelectLayout.setLayoutParams(params);
            }
        });











        RadioButton rdB_day3 = findViewById(R.id.rdB_day3);

        rdB_day3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showCalendar(); // 매월 라디오버튼이 선택되었을 때 캘린더 표시
                } else {
                    hideCalendar(); // 다른 라디오버튼이 선택되었을 때 캘린더 숨김
                }
            }

            private void hideCalendar() {
            }

            private void showCalendar() {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(plusbuttonpg.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 선택된 날짜 처리
                        String selectedDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                        // Firebase에 데이터 저장
                        saveSelectedDateToFirebase(selectedDate);
                    }

                    private void saveSelectedDateToFirebase(String selectedDate) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                        String userID = "userID"; // 사용자의 고유 ID
                        databaseReference.child(userID).child("selectedDate").setValue(selectedDate);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        RadioGroup frequencyRadioGroup = findViewById(R.id.rdG_date);
        CheckBox mondayCheckbox = findViewById(R.id.monday_checkbox);

        frequencyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdB_day2) {
                    // "매주" 라디오 버튼이 선택된 경우 요일 선택 UI를 활성화
                    findViewById(R.id.weeklyDaySelectionLayout).setVisibility(View.VISIBLE);
                } else {
                    // 다른 라디오 버튼이 선택된 경우 요일 선택 UI를 숨김
                    findViewById(R.id.weeklyDaySelectionLayout).setVisibility(View.GONE);
                }
            }
        });

        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 월요일이 선택된 경우 처리
                } else {
                    // 월요일 선택이 해제된 경우 처리
                }
            }
        });

        CheckBox MondayCheckbox = findViewById(R.id.monday_checkbox);
        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMondaySelected = isChecked; // 월요일 선택 여부 업데이트
            }
        });

        CheckBox TuesdayCheckbox = findViewById(R.id.tuesday_checkbox);
        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isTuesdaySelected = isChecked; // 화요일 선택 여부 업데이트
            }
        });

        CheckBox WednesdayCheckbox = findViewById(R.id.wednesday_checkbox);
        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isWednesdaySelected = isChecked; // 수요일 선택 여부 업데이트
            }
        });

        CheckBox ThursdayCheckbox = findViewById(R.id.thursday_checkbox);
        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isThursdaySelected = isChecked; // 목요일 선택 여부 업데이트
            }
        });

        CheckBox FridayCheckbox = findViewById(R.id.friday_checkbox);
        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFridaySelected = isChecked; // 금요일 선택 여부 업데이트
            }
        });

        CheckBox SaturdayCheckbox = findViewById(R.id.saturday_checkbox);
        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSaturdaySelected = isChecked; // 토요일 선택 여부 업데이트
            }
        });

        CheckBox SundayCheckbox = findViewById(R.id.sunday_checkbox);
        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSundaySelected = isChecked; // 일요일 선택 여부 업데이트
            }
        });

        // 날짜를 표시할 TextView 초기화
        dateTextView = findViewById(R.id.dateTextView);

        // 캘린더 이미지 클릭 시 MaterialDatePicker 표시
        ImageButton calendarButton = findViewById(R.id.calendarButton);
        TextView finalDateTextView1 = dateTextView;
        calendarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0); // 시간을 0으로 설정하여 오늘의 시작 시간으로 설정
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                long today = calendar.getTimeInMillis();

                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
                constraintsBuilder.setStart(today); // 시작일로 오늘을 설정
                constraintsBuilder.setEnd(today + (365 * 24 * 60 * 60 * 1000L)); // 1년 후까지 선택 가능

                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
                builder.setTitleText("날짜 선택");
                builder.setCalendarConstraints(constraintsBuilder.build());

                MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

                MaterialDatePicker.Builder<Pair<Long, Long>> builder2 = MaterialDatePicker.Builder.dateRangePicker();
                builder2.setTitleText("날짜 선택");
                builder2.setCalendarConstraints(constraintsBuilder.build());

                MaterialDatePicker<Pair<Long, Long>> picker2 = builder2.build();

                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        long startMillis = selection.first;
                        long endMillis = selection.second;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                        startDate = dateFormat.format(new Date(startMillis));
                        endDate = dateFormat.format(new Date(endMillis));

                        // 선택한 기간을 TextView에 표시
                        finalDateTextView1.setText(startDate + " ~ " + endDate);
                    }
                });

                picker.show(getSupportFragmentManager(), "DATE_PICKER");

                timeTextView = findViewById(R.id.timeTextView);
                ImageButton setTimeButton = findViewById(R.id.setTimeButton);

                calendar = Calendar.getInstance();

                // 시간 설정 버튼 클릭 시 TimePickerDialog 표시
                Calendar finalCalendar = calendar;
                setTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentHour = finalCalendar.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = finalCalendar.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                plusbuttonpg.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        finalCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        finalCalendar.set(Calendar.MINUTE, minute);

                                        updateTimeTextView();
                                    }

                                    private void updateTimeTextView() {
                                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                        String selectedTime = timeFormat.format(finalCalendar.getTime());
                                        timeTextView.setText(selectedTime);
                                    }
                                },
                                currentHour,
                                currentMinute,
                                false // 24시간 형식 사용 (true일 경우 AM/PM 표시)
                        );

                        timePickerDialog.show();
                    }
                });

            }
        });
    }
}

            

