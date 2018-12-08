package com.josh.billrssroom.screens.common.controllers;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.common.dependencyinjection.ControllerCompositionRoot;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private ControllerCompositionRoot controllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot(){
        if (controllerCompositionRoot == null){
            controllerCompositionRoot = new ControllerCompositionRoot(
                    ((BasicApp)requireActivity().getApplication()).getCompositionRoot(),
                    requireActivity()
            );
        }

        return controllerCompositionRoot;
    }
}
