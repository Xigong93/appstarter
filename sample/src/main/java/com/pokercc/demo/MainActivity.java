package com.pokercc.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showClassNameToast(View view) {
        ClassNameToastUtil.show("Hello Google! " + "showClassNameToast");
    }
    public void showManifestToast(View view) {
        AndroidManifestToastUtil.show("Hello Google! " + "showManifestToast");
    }
    public void showAnnotationToast(View view) {
//        AnnotationToastUtil.show("Hello Google! " + "showAnnotationToast");
    }
}

