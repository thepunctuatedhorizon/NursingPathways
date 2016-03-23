package com.jones.android.nursingpathways;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationDenied extends AppCompatActivity {
    private Button btn_childCare;
    private Button btn_financial;
    private Button btn_switchMajor;
    private Button btn_personal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_denied);
        btn_childCare = (Button)findViewById(R.id.btnChildCare);
        btn_financial = (Button)findViewById(R.id.btnFinancial);
        btn_switchMajor = (Button)findViewById(R.id.btnSwitchMajor);
        btn_personal = (Button)findViewById(R.id.btnPersonal);
        btn_childCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.ccbcmd.edu/Resources-for-Students/Childcare-Services.aspx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btn_financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.ccbcmd.edu/costs-and-paying-for-college");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btn_switchMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.ccbcmd.edu/resources-for-students/academic-advisement");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
