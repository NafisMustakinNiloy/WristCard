package com.example.nafismustakin.wristcard.Database;

import java.sql.Time;

/**
 * Created by Nafis Mustakin on 18-Jun-17.
 */

public class BloodPressure {

    private int _id;
    private Time _time;
    private int _value;

    public BloodPressure(){}

    public BloodPressure(int value, Time time){
        _time = time;
        _value = value;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Time get_time() {
        return _time;
    }

    public void set_time(Time _time) {
        this._time = _time;
    }

    public int get_value() {
        return _value;
    }

    public void set_value(int _value) {
        this._value = _value;
    }
}
