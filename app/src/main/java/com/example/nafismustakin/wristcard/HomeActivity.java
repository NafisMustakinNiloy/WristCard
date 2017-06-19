package com.example.nafismustakin.wristcard;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {



    BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;

    //Fragments

    HomeFragment homeFragment;
    ExerciseFragment exerciseFragment;
    ProfileFragment profileFragment;

    //Menu item
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager =(ViewPager) findViewById(R.id.container);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.ic_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.ic_exercise:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.ic_profile:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                }
        );

        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if(prevMenuItem != null){
                            prevMenuItem.setChecked(false);
                        }
                        else{
                            bottomNavigationView.getMenu().getItem(0).setChecked(false);
                        }
                        bottomNavigationView.getMenu().getItem(position).setChecked(true);
                        prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                }
        );

        //GraphView graphView = (GraphView)findViewById(R.id.graphView);
        //series = new LineGraphSeries<DataPoint>();
        //graphView.addSeries(series);

        //Viewport viewport = graphView.getViewport();
        //viewport.setYAxisBoundsManual(true);
        //viewport.setMinY(0);
        //viewport.setMaxY(200);
        setupViewPager(viewPager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        homeFragment.getGraphData(this);
    }


    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        exerciseFragment = new ExerciseFragment();
        profileFragment = new ProfileFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(exerciseFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
    }

    public void switchToBPM(){
        TextView tabText = (TextView)findViewById(R.id.tabText);
        tabText.setText("Heart Rate");
        TextView valueText = (TextView)findViewById(R.id.valueText);
        valueText.setText("Current BPM: ");
    }

    public void switchToPressure(){
        TextView tabText = (TextView)findViewById(R.id.tabText);
        tabText.setText("Blood Pressure");
        TextView valueText = (TextView)findViewById(R.id.valueText);
        valueText.setText("Current Blood Pressure: ");
    }

    public void switchToGlucoseLevel(){
        TextView tabText = (TextView)findViewById(R.id.tabText);
        tabText.setText("Glucose Level");
        TextView valueText = (TextView)findViewById(R.id.valueText);
        valueText.setText("Current Glucose Level: ");
    }
}
