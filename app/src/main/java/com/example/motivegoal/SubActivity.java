package com.example.motivegoal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class SubActivity extends AppCompatActivity {

    private TextView itemCountTextView;
    private MaterialCalendarView calendarView;
    private Button changeModeButton;
    private CalendarMode currentMode = CalendarMode.WEEKS;
    private Drawable selectedDrawable;
    private LocalDate selectedDate = null; // 선택한 날짜 저장 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_today);

        itemCountTextView = findViewById(R.id.itemCountTextView);
        calendarView = findViewById(R.id.calendarView);
        changeModeButton = findViewById(R.id.changeModeButton);

        // 선택된 날짜에 적용할 드로어블 초기화
        selectedDrawable = getResources().getDrawable(R.drawable.todaydate);

        // 초기 캘린더 모드 설정
        calendarView.state().edit().setCalendarDisplayMode(currentMode).commit();

        // TitleFormatter를 사용하여 연/월을 지정된 포맷으로 표시
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                int year = day.getYear();
                int month = day.getMonth();
                return year + "년 " + month + "월";
            }
        });

        // 오늘의 날짜를 가져오기
        org.threeten.bp.LocalDate today = org.threeten.bp.LocalDate.now();

        // 오늘의 날짜에 드로어블 설정
        setDrawableForDate(today);

        // 날짜 선택 리스너
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                LocalDate newSelectedDate = date.getDate();

                if (selectedDate != null) {
                    // 이전에 선택한 날짜가 있을 때, 이전 날짜의 드로어블 제거
                    clearDecorators();
                }

                // 선택한 날짜에 드로어블 적용
                setDrawableForDate(newSelectedDate);

                // 선택한 날짜 업데이트
                selectedDate = newSelectedDate;
            }

            private void clearDecorators() {
                // 모든 데코레이터를 제거
                calendarView.removeDecorators();
            }
        });

        // 버튼 클릭 리스너
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // WEEKS와 한달 모드 사이를 토글
                if (currentMode == CalendarMode.WEEKS) {
                    currentMode = CalendarMode.MONTHS;
                    changeModeButton.setText("한달");
                } else {
                    currentMode = CalendarMode.WEEKS;
                    changeModeButton.setText("일주일");
                }
                calendarView.state().edit()
                        .setCalendarDisplayMode(currentMode)
                        .commit();
            }
        });

        // 월, 요일을 한글로 보이게 설정
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        calendarView.setHeaderTextAppearance(R.style.CalendarHeaderStyle);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // 어댑터 설정
        CustomAdapterall customAdapterall = new CustomAdapterall(this, getTestDataSet(), itemCountTextView);
        recyclerView.setAdapter(customAdapterall);

        // plusbutton 버튼 클릭시 plusbuttonpg 액티비티로 이동
        ImageButton plusButton = findViewById(R.id.imageButton3);
        plusButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubActivity.this, plusbuttonpg.class);
            intent.putExtra("parentActivity", "SubActivity");
            startActivity(intent);
        });

        // goal_today_btn 버튼 클릭시 현재 액티비티 다시 호출
        Button goalTodayButton = findViewById(R.id.goal_today_btn);
        goalTodayButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubActivity.this, SubActivity.class);
            startActivity(intent);
        });

        // goal_all_btn 버튼 클릭시 MainActivity로 이동
        Button goalAllButton = findViewById(R.id.goal_all_btn);
        goalAllButton.setOnClickListener(view -> {
            Intent intent = new Intent(SubActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // RecyclerView 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // ItemTouchHelper 설정
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(customAdapterall));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setDrawableForDate(org.threeten.bp.LocalDate selectedDate) {
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return day.getDate().equals(selectedDate);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(selectedDrawable);
            }
        });
    }

    // 임의의 테스트 데이터 생성
    private ArrayList<String> getTestDataSet() {
        ArrayList<String> dataSet = new ArrayList<>();
        dataSet.add("Item 1");
        dataSet.add("Item 2");
        dataSet.add("Item 3");
        dataSet.add("Item 4");
        // ... 이하 필요한 만큼 추가
        return dataSet;
    }
}











