package com.example.motivegoal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

public class TodayDecorator implements DayViewDecorator {
    private final Drawable drawable;

    public TodayDecorator(Context context, int drawableRes) {
        drawable = context.getResources().getDrawable(drawableRes);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Date currentDate = new Date();
        return day.getDate().equals(currentDate);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}

