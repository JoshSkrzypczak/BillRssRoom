package com.josh.billrssroom.screens.common;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.common.dependencyinjection.ControllerCompositionRoot;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ControllerCompositionRoot controllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot(){
        if (controllerCompositionRoot == null){
            controllerCompositionRoot = new ControllerCompositionRoot(
                    ((BasicApp)getApplication()).getCompositionRoot(),
                    this
            );
        }

        return controllerCompositionRoot;
    }
}
