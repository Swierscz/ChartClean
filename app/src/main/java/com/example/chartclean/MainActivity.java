package com.example.chartclean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import java8.util.stream.Collectors;

import static java8.util.stream.StreamSupport.stream;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private static final int ANIMATION_DURATION = 1000;
    private static final int NORMALIZED_MIN_VALUE = 0;
    private static final int NORMALIZED_MAX_VALUE = 100;

    @BindView(R.id.line_chart)
    LineChart chart;

    List<ILineDataSet> currentDataSet = new ArrayList<>();
    LineDataSet hrDataSet, elevationDataSet, tempoDataSet, tempearatureDataSet;
    private boolean wasDataSetCreationAnimated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        CustomMarkerView customMarkerView = new CustomMarkerView(getApplicationContext(), R.layout.radar_marker_view);
        chart.setMarker(customMarkerView);
        chart.setOnChartValueSelectedListener(this);
        initDataSet();
        initChar(chart, Color.WHITE);

    }

    private void initChar(LineChart chart, int color) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);
        chart.setBackgroundColor(color);
//        chart.setViewPortOffsets(0, -10, 0, 0);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);
//        //Pionowa linia nad x
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setGranularity(1);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setValueFormatter(new ValueFormatter());
        Legend l = chart.getLegend();
        l.setTextSize(12f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setTypeface(ResourcesCompat.getFont(this, R.font.stoltz_regular));
        l.setEnabled(true);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(20);
        l.setForm(Legend.LegendForm.CIRCLE);
    }

    class ValueFormatter extends com.github.mikephil.charting.formatter.ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return secToTimeXX_XX_XX((long) value);
        }
    }

    public String secToTimeXX_XX_XX(long sec) {
        long minutes = sec / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (minutes == 0 && hours == 0 && days == 0)
            return String.format("%02d s", sec % 60);
        else if (hours == 0 && days == 0)
            return String.format("%02d m %02d s", minutes % 60, sec % 60);
        else if (days == 0)
            return String.format("%02d h %02d m %02d s", hours % 24, minutes % 60, sec % 60);
        else return String.format("%02d days %02d h %02d m", days, hours % 24, minutes % 60);
    }

    private void initDataSet() {
        hrDataSet = getHrDataSet(Hr.arrayList, Color.RED, Color.RED, Color.WHITE);
        tempoDataSet = getTempoDataSet(Speed.getSpeedValues(), Color.BLUE);
        tempearatureDataSet = getTemperatureDataSet(Temperature.getTemperatureValues(), Color.MAGENTA);
        elevationDataSet = getElevationDataSet(Elevation.arrayList, Color.GREEN);
    }

    private LineDataSet getHrDataSet(List<Integer> inputValues, int lineColor, int holeColor, int circleColor) {
        LineDataSet dataSet = new LineDataSet(getHrEntries(inputValues), "Heart rate");
        dataSet.setCircleHoleColor(holeColor);
        dataSet.setColor(lineColor);
        dataSet.setLineWidth(1.75f);
        dataSet.setDrawCircles(false);
        dataSet.setHighLightColor(lineColor);
        dataSet.setDrawValues(false);
        return dataSet;
    }

    private LineDataSet getTempoDataSet(List<Integer> inputValues, int lineColor) {
        LineDataSet dataSet = new LineDataSet(getTempoEntries(inputValues), "Tempo");
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setLineWidth(3f);
        dataSet.setColor(lineColor);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setHighLightColor(lineColor);
        return dataSet;
    }

    private LineDataSet getTemperatureDataSet(List<Integer> inputValues, int lineColor) {
        LineDataSet dataSet = new LineDataSet(getTemperatureEntries(inputValues), "Temperature");
        dataSet.setMode(LineDataSet.Mode.STEPPED);
        dataSet.setDrawValues(false);
        dataSet.setColor(lineColor);
        dataSet.setLineWidth(1.75f);
        dataSet.setDrawCircles(false);
        dataSet.setHighLightColor(lineColor);
        return dataSet;
    }

    private LineDataSet getElevationDataSet(List<Double> inputValues, int lineColor) {
        LineDataSet dataSet = new LineDataSet(getElevationEntries(inputValues), "Evelation");
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        dataSet.setHighLightColor(lineColor);
        dataSet.setColor(lineColor);
        dataSet.setFillColor(lineColor);
        dataSet.setFillAlpha(20);
        dataSet.setLineWidth(1.75f);
        dataSet.setDrawFilled(true);
        return dataSet;
    }

    private List<Entry> getElevationEntries(List<Double> inputValues) {
        List<Entry> entries = new ArrayList<>();
        double max = stream(inputValues).max(Double::compare).orElse(0D);
        double min = stream(inputValues).min(Double::compare).orElse(0D);
        List<Float> normalizedValues = stream(inputValues).map(v -> normalize(v.intValue(), 0, (float) max, NORMALIZED_MIN_VALUE, NORMALIZED_MAX_VALUE / 5)).collect(Collectors.toList());

        for (int i = 0; i < inputValues.size(); i++) {
            entries.add(new Entry(i, normalizedValues.get(i), String.format("%.2f m", inputValues.get(i))));
        }
        return entries;
    }

    private List<Entry> getTemperatureEntries(List<Integer> inputValues) {
        List<Entry> entries = new ArrayList<>();
        int max = stream(inputValues).max(Integer::compare).orElse(0);
        int min = stream(inputValues).min(Integer::compare).orElse(0);
        List<Float> normalizedValues = stream(inputValues).map(v -> normalize(v.intValue(), min, max, NORMALIZED_MIN_VALUE, NORMALIZED_MAX_VALUE)).collect(Collectors.toList());

        for (int i = 0; i < inputValues.size(); i++) {
            entries.add(new Entry(i, normalizedValues.get(i), inputValues.get(i) + " *C"));
        }
        return entries;
    }

    private List<Entry> getTempoEntries(List<Integer> inputValues) {
        List<Entry> entries = new ArrayList<>();
        int max = stream(inputValues).max(Integer::compare).orElse(0);
        int min = stream(inputValues).min(Integer::compare).orElse(0);
        List<Float> normalizedValues = stream(inputValues).map(v -> normalize(v.intValue(), min, max, NORMALIZED_MIN_VALUE, NORMALIZED_MAX_VALUE)).collect(Collectors.toList());

        for (int i = 0; i < inputValues.size(); i++) {
            entries.add(new Entry(i, normalizedValues.get(i), getTempoFormat(inputValues.get(i))));
        }
        return entries;
    }

    private String getTempoFormat(int seconds) {
        int minutes = seconds / 60;
        int secondsModulo = seconds % 60;
        return minutes + " m " + secondsModulo + " s";
    }

    private List<Entry> getHrEntries(List<Integer> inputValues) {
        List<Entry> entries = new ArrayList<>();
        int max = stream(inputValues).max(Integer::compare).orElse(0);
        int min = stream(inputValues).min(Integer::compare).orElse(0);
        List<Float> normalizedValues = stream(inputValues).map(v -> normalize(v.intValue(), min, max, NORMALIZED_MIN_VALUE, NORMALIZED_MAX_VALUE)).collect(Collectors.toList());

        for (int i = 0; i < inputValues.size(); i++) {
            entries.add(new Entry(i, normalizedValues.get(i), inputValues.get(i) + " hr"));
        }
        return entries;
    }


    private float normalize(float value, float min, float max, int newMin, int newMax) {
        return ((value - min) / (max - min)) * (newMax - newMin) + newMin;
    }

    @OnCheckedChanged(R.id.checkbox_hr)
    void addHrCheckBoxListener(CompoundButton button, boolean isChecked) {
        addOrRemoveDataSet(isChecked, currentDataSet, hrDataSet);
    }

    @OnCheckedChanged(R.id.checkbox_speed)
    void addTempoCheckBoxListener(CompoundButton button, boolean isChecked) {
        addOrRemoveDataSet(isChecked, currentDataSet, tempoDataSet);
    }

    @OnCheckedChanged(R.id.checkbox_temperature)
    void addTemperatureCheckBoxListener(CompoundButton button, boolean isChecked) {
        addOrRemoveDataSet(isChecked, currentDataSet, tempearatureDataSet);
    }

    @OnCheckedChanged(R.id.checkbox_height)
    void addElevationCheckBoxListener(CompoundButton button, boolean isChecked) {
        addOrRemoveDataSet(isChecked, currentDataSet, elevationDataSet);
    }


    private void addOrRemoveDataSet(boolean shouldAdd, List<ILineDataSet> dataSetList, ILineDataSet dataSet) {
        if (shouldAdd) {
            dataSetList.add(dataSet);
        } else {
            currentDataSet.remove(dataSet);
            chart.highlightValues(null);
        }

        chart.setData(new LineData(currentDataSet));
        chart.invalidate();

        if (dataSetList.size() == 1) {
            if (!wasDataSetCreationAnimated) {
                chart.animateX(ANIMATION_DURATION);
                wasDataSetCreationAnimated = true;
            }
        }
        if (dataSetList.size() == 0)
            wasDataSetCreationAnimated = false;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Some tag", "e: " + e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Some tag", "Nothing selected");
    }
}
