package org.d3ifcool.denver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String PROFILE = "profile";
    String idProfil;
    String namaProfile;

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageView signOut = (ImageView) findViewById(R.id.button_sign_out);
        TextView akun = (TextView) findViewById(R.id.akun);
        TextView logOut = (TextView) findViewById(R.id.logOut);
        TextView namaProfileTextButton = (TextView) findViewById(R.id.namaAnakTextView);
        Button testButton = (Button) findViewById(R.id.testBtn);
        Button stimulasiButton = (Button) findViewById(R.id.stimulasiBtn);
        Button riwayatButton = (Button) findViewById(R.id.riwayatBtn);
        Button tentangButton = (Button) findViewById(R.id.aboutBtn);

        loadProfilPreferences();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            akun.setText("Hai!!, "+acct.getDisplayName());;

            Uri personPhoto = acct.getPhotoUrl();

            Picasso.with(MainActivity.this)
                    .load(personPhoto.toString())
                    .into(signOut);
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PROFILE, Context.MODE_PRIVATE).edit();
                editor.clear().commit();
                signOut();
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MainActivity.this, PilihUmurActivity.class);
                testIntent.putExtra("MENU",1);
                startActivity(testIntent);
            }
        });

        stimulasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stimulasiIntent = new Intent(MainActivity.this, PilihUmurActivity.class);
                stimulasiIntent.putExtra("MENU",2);
                startActivity(stimulasiIntent);
            }
        });

        riwayatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent riwayatIntent = new Intent(MainActivity.this, RiwayatActivity.class);
                startActivity(riwayatIntent);
            }
        });

        tentangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tentangIntent = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(tentangIntent);
            }
        });

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account==null){
            Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            finish();

        }else {
            if (idProfil==null){
                Intent intentProfil = new Intent(MainActivity.this, ProfilAnakActivity.class);
                startActivity(intentProfil);
            }
        }
    }

    public void loadProfilPreferences(){
        SharedPreferences preferences = getSharedPreferences(PROFILE, MODE_PRIVATE);
        idProfil = preferences.getString("ID", null);
        namaProfile = preferences.getString("NAMA", null);

        preferences.registerOnSharedPreferenceChangeListener(MainActivity.this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadProfilPreferences();
    }


}
