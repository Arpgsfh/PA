package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PasscodeActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    LinearLayout regisLayout;
    LinearLayout confirmLayout;

    public static final String PASSCODE = "passcode";
    String pin;
    Boolean pass=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        regisLayout = (LinearLayout) findViewById(R.id.regisLayout);
        final EditText regisPin = (EditText) findViewById(R.id.regisPin);
        final EditText confirmRegis = (EditText) findViewById(R.id.confirmRegis);
        Button buttonRegisPin = (Button) findViewById(R.id.buttonRegisPin);


        confirmLayout = (LinearLayout) findViewById(R.id.confirmLayout);
        final EditText confirmPin = (EditText) findViewById(R.id.confirmPin);
        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);


        buttonRegisPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regisPin.getText().toString().equals(confirmRegis.getText().toString())){
                    SharedPreferences.Editor editor = getSharedPreferences(PASSCODE, MODE_PRIVATE).edit();
                    editor.putString("PIN", confirmRegis.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(PasscodeActivity.this, MainActivity.class);
                    intent.putExtra("PASS", true);
                    startActivity(intent);
                }else {
                    Toast.makeText(PasscodeActivity.this, "Pin yang dimasukkan tidak sama", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmPin.getText().toString().equals(pin))    {
                    Intent intent = new Intent(PasscodeActivity.this, MainActivity.class);
                    intent.putExtra("PASS", true);
                    startActivity(intent);
                }else {
                    Toast.makeText(PasscodeActivity.this, "Pin tidak cocok", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadProfilPreferences();
    }

    public void loadProfilPreferences(){

        SharedPreferences preferences = getSharedPreferences(PASSCODE, MODE_PRIVATE);
        pin = preferences.getString("PIN", null);

        if (pin==null || pin.equals("")){
            regisLayout.setVisibility(View.VISIBLE);
            confirmLayout.setVisibility(View.GONE);
        }else {
            confirmLayout.setVisibility(View.VISIBLE);
            regisLayout.setVisibility(View.GONE);
        }

        preferences.registerOnSharedPreferenceChangeListener(PasscodeActivity.this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadProfilPreferences();
    }
}
