package com.geko.test;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.geko.test.main.SqliteHelper;
import com.geko.test.main.TabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText textUserFullName,textUserMail,textUserPassword,textUserNumber,
            textLoginMail,textLoginPassword;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private SqliteHelper dbHelper = new SqliteHelper(this);
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);
        int setting = sharedpreferences.getInt("UserId", 0);
        if(setting != 0){
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            finish();
            startActivity(intent);
        }else{
            TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(tabsPagerAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#A9D899"));
            tabs.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        }
    }

    public void onClickLogin(View v)
    {
        textLoginMail = (EditText) findViewById(R.id.textLoginMail);
        textLoginPassword = (EditText) findViewById(R.id.textLoginPassword);
        String mailLogin = textLoginMail.getText().toString();
        String passwordLogin = textLoginPassword.getText().toString();
        Boolean haveError = false;
        if(mailLogin.isEmpty()) {
            haveError = true;
            textLoginMail.setError("Email is required");
        }else {
            if (!mailLogin.trim().matches(emailPattern)) {
                haveError = true;
                textLoginMail.setError("Invalid email format");
            }
        }
        if(passwordLogin.isEmpty()) {
            haveError = true;
            textLoginPassword.setError("Password is required");
        }

        if(!haveError){
            int userId = dbHelper.validateUser(mailLogin,passwordLogin);
            if(userId != 0){
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("UserId", userId);
                editor.apply();
                finish();
                startActivity(intent);
            } else {
                Toast.makeText(this,"Log In error",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void onClickSignup(View v)
    {
        textUserFullName = (EditText) findViewById(R.id.textUserFullName);
        textUserMail = (EditText) findViewById(R.id.textUserMail);
        textUserPassword = (EditText) findViewById(R.id.textUserPassword);
        textUserNumber = (EditText) findViewById(R.id.textUserNumber);
        textLoginMail = (EditText) findViewById(R.id.textLoginMail);
        textLoginPassword = (EditText) findViewById(R.id.textLoginPassword);
        String userFullName = textUserFullName.getText().toString();
        String userMail = textUserMail.getText().toString();
        String userPassword = textUserPassword.getText().toString();
        String userNumber = textUserNumber.getText().toString();

        Boolean haveError = false;
        if(userFullName.isEmpty()) {
            haveError = true;
            textUserFullName.setError("Is required");
        }
        if(userMail.isEmpty()) {
            haveError = true;
            textUserMail.setError("Is required");
        }else {
            if (!userMail.trim().matches(emailPattern)) {
                haveError = true;
                textUserMail.setError("Invalid email format");
            } else if(dbHelper.validateEmail(userMail) != 0){
                haveError = true;
                textUserMail.setError("Invalid email");
            }
        }
        if(userPassword.isEmpty()) {
            haveError = true;
            textUserPassword.setError("Is required");
        } else {
            if(!isValidPassword(userPassword)){
                textUserPassword.setError("Password does not meet the required fields");
            }
        }
        if(userNumber.isEmpty()) {
            haveError = true;
            textUserNumber.setError("Is required");
        } else {
            if(userNumber.length() != 10){
                textUserNumber.setError("Phone number invalid");
            }
        }

        if(!haveError) {
            textLoginMail.setText("");
            textLoginPassword.setText("");
            textUserFullName.setText("");
            textUserMail.setText("");
            textUserPassword.setText("");
            textUserNumber.setText("");
            long userId = dbHelper.saveUser(userFullName, userMail, userPassword, userNumber);
            Toast.makeText(this,"Sign Up successfully",Toast.LENGTH_SHORT).show();
            TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);
            tabs.getTabAt(0).select();
        }
    }
}