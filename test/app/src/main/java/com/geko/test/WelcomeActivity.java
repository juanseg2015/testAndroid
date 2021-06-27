package com.geko.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geko.test.main.SqliteHelper;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    TextView textName,textMail,textPhone;
    private SqliteHelper dbHelper = new SqliteHelper(this);
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, this.MODE_PRIVATE);
        int setting = sharedPreferences.getInt("UserId", 0);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList =  dbHelper.getUserData(setting);
        textName = (TextView) findViewById(R.id.textName);
        textMail = (TextView) findViewById(R.id.textMail);
        textPhone = (TextView) findViewById(R.id.textPhone);
        textName.setText(arrayList.get(0));
        textMail.setText(arrayList.get(1));
        textPhone.setText(arrayList.get(2));
    }

    public void onClickLogOut(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UserId", 0);
        editor.apply();
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}