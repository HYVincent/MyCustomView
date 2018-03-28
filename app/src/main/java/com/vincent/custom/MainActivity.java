package com.vincent.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vincent.custom.custom_view.ElectricStatisticsMonthView;
import com.vincent.custom.util.CalendarUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ElectricStatisticsMonthView mElectricStatisticsMonthView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mElectricStatisticsMonthView = findViewById(R.id.esmv_view);
        mElectricStatisticsMonthView.setTodayNum(CalendarUtil.getDayOfMonth());
        Log.d(TAG, "onCreate: "+CalendarUtil.getDayOfMonth());
    }
}
