package org.d3ifcool.denver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String PASSCODE = "passcode";
    String pin;
    Boolean pass=false;

    public static final String PROFILE = "profile";
    String idProfil;
    String namaProfile;
    int tahun, bulan, hari;
    int years = 0;
    int months = 0;
    int days = 0;

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

        ConstraintLayout profilAnak = (ConstraintLayout) findViewById(R.id.profilAnak);
        final ImageView signOut = (ImageView) findViewById(R.id.button_sign_out);
        TextView namaProfileTextButton = (TextView) findViewById(R.id.namaAnakTextView);
        Button testButton = (Button) findViewById(R.id.testBtn);
        Button stimulasiButton = (Button) findViewById(R.id.stimulasiBtn);
        Button riwayatButton = (Button) findViewById(R.id.riwayatBtn);
        Button tentangButton = (Button) findViewById(R.id.aboutBtn);

        loadProfilPreferences();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            Uri personPhoto = acct.getPhotoUrl();

            Picasso.with(MainActivity.this)
                    .load(personPhoto.toString())
                    .into(signOut);
        }

        profilAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, ProfilAnakActivity.class);
                startActivity(intent1);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idProfil==null || idProfil.equalsIgnoreCase("KOSONG")){
                    Intent profilAnakIntent = new Intent(MainActivity.this, ProfilAnakActivity.class);
                    profilAnakIntent.putExtra("MENU",1);
                    startActivity(profilAnakIntent);
                }else {
                    Intent testIntent = new Intent(MainActivity.this, PilihUmurActivity.class);
                    testIntent.putExtra("MENU",1);
                    startActivity(testIntent);
                }

            }
        });

        stimulasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idProfil==null || idProfil.equalsIgnoreCase("KOSONG")){
                    Intent profilAnakIntent = new Intent(MainActivity.this, ProfilAnakActivity.class);
                    profilAnakIntent.putExtra("MENU",2);
                    startActivity(profilAnakIntent);
                }else {
                    Intent stimulasiIntent = new Intent(MainActivity.this, PilihUmurActivity.class);
                    stimulasiIntent.putExtra("MENU", 2);
                    startActivity(stimulasiIntent);
                }
            }
        });

        riwayatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idProfil==null || idProfil.equalsIgnoreCase("KOSONG")){
                    Intent profilAnakIntent = new Intent(MainActivity.this, ProfilAnakActivity.class);
                    profilAnakIntent.putExtra("MENU",3);
                    startActivity(profilAnakIntent);
                }else {
                    Intent riwayatIntent = new Intent(MainActivity.this, RiwayatActivity.class);
                    startActivity(riwayatIntent);
                }
            }
        });

        tentangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tentangIntent = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(tentangIntent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, signOut);

                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.logOut:
                                SharedPreferences.Editor editor = getSharedPreferences(PROFILE, Context.MODE_PRIVATE).edit();
                                editor.clear().commit();
                                signOut();
                                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                finish();
                                return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
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
        }else{
            Intent intent = getIntent();
            pass = intent.getBooleanExtra("PASS", false);

            if (pass==false){
                Intent intentPass = new Intent(MainActivity.this, PasscodeActivity.class);
                startActivity(intentPass);
                finish();
            }
        }
    }

    public void AgeCalculator(){
        Calendar now = Calendar.getInstance();
        years = now.get(Calendar.YEAR) - tahun;
        months = now.get(Calendar.MONTH) - bulan;

        if (months<0){
            years--;
            months = 12 + months;
        }
        months = (years*12) + months;
        days = now.get(Calendar.DAY_OF_MONTH) - hari;

        if (days<=0){
            months++;
        }
    }

    public void loadProfilPreferences(){

        SharedPreferences preferences = getSharedPreferences(PROFILE, MODE_PRIVATE);
        TextView namaAnak = (TextView) findViewById(R.id.namaAnakTextView);
        TextView umurAnak = (TextView) findViewById(R.id.umurTextView);

        idProfil = preferences.getString("ID", null);

        if (idProfil==null || idProfil.equalsIgnoreCase("KOSONG")){
            namaAnak.setText("Profil Anak");
            umurAnak.setVisibility(View.GONE);
        }else {
            namaProfile = preferences.getString("NAMA", null);
            tahun = preferences.getInt("TAHUN", 0);
            bulan = preferences.getInt("BULAN", 0);
            hari = preferences.getInt("HARI", 0);

            namaAnak.setText(namaProfile);
            AgeCalculator();
            umurAnak.setVisibility(View.VISIBLE);
            umurAnak.setText("Umur "+months+" Bulan");
        }



        preferences.registerOnSharedPreferenceChangeListener(MainActivity.this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadProfilPreferences();
    }
}
