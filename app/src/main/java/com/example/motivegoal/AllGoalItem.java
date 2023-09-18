package com.example.motivegoal;

import java.util.ArrayList;

public class AllGoalItem {
    // 추가: 인자 없는 생성자
    public AllGoalItem() {
        // 기본 생성자 내용 (예: 초기화)
    }
    private String title;
    public int number;
    private String contents;
    private String selectedDate;
    private String selectedTime;
    private String selectedFrequency;
    private ArrayList<AllGoalItem> itemList;
    private boolean selectedDay; // 선택한 요일을 저장하는 변수


    public AllGoalItem(String goalTitle, String selectedDate, String selectedTime, String selectedFrequency) {
        // 기본 생성자 내용을 여기에 추가
    }


    // 생성자: 목표 생성에 필요한 정보를 받아옴
    public AllGoalItem(String title, int number, String contents, String selectedDate, String selectedTime, String selectedFrequency) {
        this.title = title;
        this.number = number;
        this.contents = contents;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.selectedFrequency = selectedFrequency;
    }



    // Getter 및 Setter 메서드 추가 (필요한 경우)

    public void setKey(String key) {
        // 구현 내용 추가 (필요한 경우)
    }

    public String getSelectedDday() {
        // 구현 내용 추가 (필요한 경우)
        return "";
    }

    public String getTitle() {
        return title;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}










