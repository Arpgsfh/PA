package org.d3ifcool.denver;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class TestActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    TextView textViewRefresh;
    TextView textViewKoneksi;
    TextView textViewKategori;
    CardView cardView;
    TextView noPertanyaan;
    TextView pertanyaan;
    ImageView gmbr;
    ImageView canvas;
    Button draw;
    Button btnIya;
    Button btnTidak;

    FirebaseDatabase database;

    int umur;
    int nomor=0;
    int nilai=0;
    int kategori=0;
    int jKasar, jHalus, jBicara, jSosialisasi;
    int nKasar, nHalus, nBicara, nSosialisasi;

    String rPertanyaan[] = new String[10];
    int rJawaban[] = new int[10];

    int jumlahSoal;



    DrawingView dv;
    private Paint mPaint;

    private Bitmap mBitmap;
    private Canvas mCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        umur = intent.getIntExtra("UMUR",0);

        hitungSoal();
        setPertanyaan(nomor, jumlahSoal);

        mProgressBar = (ProgressBar) findViewById(R.id.loadingBar);
        textViewRefresh = (TextView) findViewById(R.id.textViewRefresh);
        textViewKoneksi = (TextView) findViewById(R.id.koneksi);
        textViewKategori = (TextView) findViewById(R.id.kategori);
        cardView = (CardView) findViewById(R.id.cardView);
        noPertanyaan = (TextView) findViewById(R.id.noPertanyaan);
        pertanyaan = (TextView) findViewById(R.id.pertanyaan);
        gmbr = (ImageView) findViewById(R.id.gmbrPertanyaan);
        canvas = (ImageView) findViewById(R.id.canvas);
        draw = (Button) findViewById(R.id.draw);
        btnIya = (Button) findViewById(R.id.buttonIya);
        btnTidak = (Button) findViewById(R.id.buttonTdk);

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                builder.setTitle("Menggambar");

                LinearLayout layout = new LinearLayout(TestActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                dv = new DrawingView(TestActivity.this);
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setDither(true);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeJoin(Paint.Join.ROUND);
                mPaint.setStrokeCap(Paint.Cap.ROUND);
                mPaint.setStrokeWidth(12);

                layout.addView(dv);

                builder.setView(layout); // Again this is a set method, not add

                builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canvas.setBackgroundColor(Color.WHITE);
                        canvas.setImageBitmap(mBitmap);
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        btnIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (kategori){
                    case 1:
                        nKasar++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                    case 2:
                        nHalus++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                    case 3:
                        nBicara++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                    case 4:
                        nSosialisasi++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                }

                reset();
                nomor++;
                nilai++;

                if (cekKoneksi()){
                    textViewRefresh.setVisibility(View.GONE);
                    textViewKoneksi.setVisibility(View.GONE);
                    setPertanyaan(nomor, jumlahSoal);
                }else {
                    textViewRefresh.setVisibility(View.VISIBLE);
                    textViewKoneksi.setVisibility(View.VISIBLE);
                }
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomor != 0){
                    rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                    rJawaban[nomor-1] = 0;
                }
                reset();
                nomor++;

                if (cekKoneksi()){
                    textViewRefresh.setVisibility(View.GONE);
                    textViewKoneksi.setVisibility(View.GONE);
                    setPertanyaan(nomor, jumlahSoal);
                }else {
                    textViewRefresh.setVisibility(View.VISIBLE);
                    textViewKoneksi.setVisibility(View.VISIBLE);
                }
            }
        });

        textViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cekKoneksi()){
                    textViewRefresh.setVisibility(View.GONE);
                    textViewKoneksi.setVisibility(View.GONE);
                    setPertanyaan(nomor, jumlahSoal);
                }else {
                    textViewRefresh.setVisibility(View.VISIBLE);
                    textViewKoneksi.setVisibility(View.VISIBLE);
                }
            }
        });
    }



    private void hitungSoal(){
        DatabaseReference cek = database.getReference("Test").child(String.valueOf(umur));
        cek.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                jumlahSoal = (int) dataSnapshot.getChildrenCount()-1;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setPertanyaan(final int nomor, final int jumlahSoal){

        if (nomor<=jumlahSoal){
            DatabaseReference myRef = database.getReference("Test").child(String.valueOf(umur)).child(String.valueOf(nomor));
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (mProgressBar != null) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    Test test = dataSnapshot.getValue(Test.class);

                    Glide
                            .with(TestActivity.this)
                            .load(test.getImageUrl())
                            .into(gmbr);

                    if (nomor==0){
                        noPertanyaan.setText("Persiapan");
                    }else {
                        noPertanyaan.setText(String.valueOf(nomor)+" /"+String.valueOf(jumlahSoal));
                    }
                    pertanyaan.setText(String.valueOf(test.getPertanyaan().replaceAll("_b", "\n")));


                    kategori = test.kategori;
                    switch (kategori){
                        case 0:
                            textViewKategori.setText("Alat dan Bahan");
                            break;
                        case 1:
                            jKasar++;
                            textViewKategori.setText("Gerak Kasar");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.green));
                            break;
                        case 2:
                            jHalus++;
                            textViewKategori.setText("Gerak Halus");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.blue));
                            break;
                        case 3:
                            jBicara++;
                            textViewKategori.setText("Bicara dan Bahasa");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                            break;
                        case 4:
                            jSosialisasi++;
                            textViewKategori.setText("Sosialisasi dan Kemandirian");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.yellow));
                            break;
                    }

                    if (nomor == 0){
                        btnIya.setVisibility(View.GONE);
                        btnTidak.setVisibility(View.VISIBLE);
                        btnTidak.setText("Mulai");
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        btnIya.setVisibility(View.VISIBLE);
                        btnTidak.setVisibility(View.VISIBLE);
                        btnTidak.setText("Tidak");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pertanyaan.setText("Failed");
                }


            });
        }else {
            Intent intent = new Intent(TestActivity.this, PenilaianActivity.class);
            intent.putExtra("NILAI",nilai);
            intent.putExtra("NK",nKasar);
            intent.putExtra("NH",nHalus);
            intent.putExtra("NB",nBicara);
            intent.putExtra("NS",nSosialisasi);

            intent.putExtra("JK",jKasar);
            intent.putExtra("JH",jHalus);
            intent.putExtra("JB",jBicara);
            intent.putExtra("JS",jSosialisasi);

            intent.putExtra("UMUR",umur);

            intent.putExtra("JP", jumlahSoal);
            intent.putExtra("RP", rPertanyaan);
            intent.putExtra("RJ", rJawaban);
            startActivity(intent);
            finish();
        }
    }

    private void reset(){
        mProgressBar.setVisibility(View.VISIBLE);
        btnIya.setVisibility(View.INVISIBLE);
        btnTidak.setVisibility(View.INVISIBLE);
        canvas.setImageBitmap(null);


    }

    public boolean cekKoneksi(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
    }

    public class DrawingView extends View {

        public int width;
        public  int height;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context=c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath( mPath,  mPaint);
            canvas.drawPath( circlePath,  circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath,  mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }
}
