package com.jones.android.nursingpathways;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationDenied extends AppCompatActivity {
    private Button btn_email;
    private Button btn_financial;
    private Button btn_switchMajor;
    private Button btn_personal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_denied);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_long), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,50,0,0);

        btn_email = (Button)findViewById(R.id.btnEmail);
        btn_email.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_email.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_email.setTextSize(16);
        btn_email.setLayoutParams(params);
        btn_financial = (Button)findViewById(R.id.btnFinancial);
        btn_financial.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_financial.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_financial.setTextSize(16);
        btn_financial.setLayoutParams(params);
        btn_switchMajor = (Button)findViewById(R.id.btnSwitchMajor);
        btn_switchMajor.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_switchMajor.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_switchMajor.setTextSize(16);
        btn_switchMajor.setLayoutParams(params);
        btn_personal = (Button)findViewById(R.id.btnPersonal);
        btn_personal.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_personal.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_personal.setTextSize(16);
        btn_personal.setLayoutParams(params);

        btn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.ccbcmd.edu/Resources-for-Students");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                sympathy();
            }
        });
        btn_financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.ccbcmd.edu/costs-and-paying-for-college");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                sympathy();
            }
        });
        btn_switchMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.ccbcmd.edu/resources-for-students/academic-advisement");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                sympathy();
            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "ksamuel1@umbc.edu");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cannot register for course(s)");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter message body here");
                /*String uriText = "mailto:" + Uri.encode("ksamuel1@umbc.edu") +
                        "?subject=" + Uri.encode("Cannot register for course(s)") +
                        "&body=" + Uri.encode("Enter message body here");
                Uri uri = Uri.parse(uriText);
                emailIntent.setData(uri);*/
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                    Log.d("","Sent email!");
                    sympathy();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    sympathy();
                }
            }
        });
    }

    private void sympathy(){
        TextView textView = (TextView) findViewById(R.id.registrationDeniedSympathyText);
        textView.setText(R.string.registrationDeniedSympathyText_text);
    }
}
