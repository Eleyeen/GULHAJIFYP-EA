package com.example.bsnotes.activities.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bsnotes.R;
import com.example.bsnotes.activities.authentication.Login;

public class SuccessPasswordMessage extends AppCompatActivity {
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_password_message);
        email = (TextView)findViewById(R.id.emailtext);
        String mail = getIntent().getStringExtra("email");
        email.setText("An email has been sent to \n"+mail +"\n"+ "Please go and Check");
    }

    public void gotoLoginPage(View view) {
        Intent gotologinpage = new Intent(getApplicationContext(), Login.class);
        startActivity(gotologinpage);
    }

    public void gotoHostellist(View view) {
    }
}