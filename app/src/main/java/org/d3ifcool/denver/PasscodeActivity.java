package org.d3ifcool.denver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasscodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        final EditText confirmPin = (EditText) findViewById(R.id.confirmPin);
        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmPin.getText().toString().equals("123456"))    {
                    Intent intent = new Intent(PasscodeActivity.this, MainActivity.class);
                    intent.putExtra("PASS", true);
                    startActivity(intent);
                }else {
                    Toast.makeText(PasscodeActivity.this, "Pin tidak cocok", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
