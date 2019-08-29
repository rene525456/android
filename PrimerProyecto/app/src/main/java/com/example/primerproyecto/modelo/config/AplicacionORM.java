package com.example.primerproyecto.modelo.config;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class AplicacionORM extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
