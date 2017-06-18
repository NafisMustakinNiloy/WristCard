package com.example.nafismustakin.wristcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

/**
 * Created by Nafis Mustakin on 18-Jun-17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    TextView tabText, valueText;
    private static int mode = 0;

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

        GraphView graphView = (GraphView)view.findViewById(R.id.graphView);


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
}
