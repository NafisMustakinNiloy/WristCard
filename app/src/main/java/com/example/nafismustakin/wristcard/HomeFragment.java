package com.example.nafismustakin.wristcard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

/**
 * Created by Nafis Mustakin on 18-Jun-17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    TextView tabText, valueText;
    GraphView graphView;
    private LineGraphSeries<DataPoint> series;
    private static int mode = 0;

    private static final Random RANDOM = new Random();
    private int lastX= 0;

    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container,false );

        tabText = (TextView)view.findViewById(R.id.tabText);
        valueText = (TextView)view.findViewById(R.id.valueText);
        Button buttonBPM = (Button)view.findViewById(R.id.buttonBPM);
        Button buttonGlucose = (Button)view.findViewById(R.id.buttonGlucose);
        Button buttonPressure = (Button)view.findViewById(R.id.buttonPressure);

        graphView = (GraphView)view.findViewById(R.id.graphOutput);

        series = new LineGraphSeries<>();
        graphView.addSeries(series);

        Viewport viewport = graphView.getViewport();

        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(200);

        buttonBPM.setOnClickListener(this);
        buttonPressure.setOnClickListener(this);
        buttonGlucose.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonBPM:
                tabText.setText("Heart Rate");
                break;
            case R.id.buttonGlucose:
                tabText.setText("Glucose Level");
                break;
            case R.id.buttonPressure:
                tabText.setText("Blood Pressure");
                break;
        }
    }


    private void addEntry(){
        double y = RANDOM.nextDouble()*150d;
        System.out.println("X=" + (lastX+1) + ", Y=" +y);
        series.appendData(new DataPoint(lastX++, y), true, 20);
        graphView.addSeries(series);
    }

    public void getGraphData(final Activity activity){

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i= 0; i<100; i++){
                    if(getActivity() != null)
                    {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addEntry();
                            }
                        });
                    }
                    //sleep to slow down
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



}
