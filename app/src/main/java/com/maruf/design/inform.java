package com.maruf.design;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class inform extends AppCompatActivity implements LocationListener {
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormate;
    EditText et_tgl;





    TextView et_nim;
    TextView et_nama;
    Spinner et_jurusan;
    Spinner sp_prodi;
    CheckBox g_a,g_b,g_ab,g_o;
    Button btn_simpan;
    RadioGroup jenis_kelamin;
    RadioButton laki;
    RadioButton perempuan;
    EditText et_email;
    EditText et_nomor;
    EditText ml_alamat;
    EditText et_ipk;
    Button upld;
    ImageView mImageView;
    Uri imageuri;
    LocationManager locationManager;




    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    StorageReference mStroge ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inform_input);
        mStroge = FirebaseStorage.getInstance().getReference("image");


        et_nim = findViewById(R.id.nim);
        et_nama = findViewById(R.id.nama);
        et_jurusan = findViewById(R.id.jurusan);
        sp_prodi = findViewById(R.id.listprodi);
        btn_simpan = findViewById(R.id.btn_simpan);
        g_a = findViewById(R.id.g_a);
        g_b = findViewById(R.id.g_b);
        g_ab = findViewById(R.id.g_ab);
        g_o = findViewById(R.id.g_o);
        laki = findViewById(R.id.laki);
        jenis_kelamin=findViewById(R.id.jenis_klmn);
        perempuan = findViewById(R.id.perempuan);
        et_tgl = findViewById(R.id.tgl);
        et_email = findViewById(R.id.email);
        et_nomor = findViewById(R.id.noHp);
        ml_alamat = findViewById(R.id.alamt);
        et_ipk = findViewById(R.id.ipk);
        upld = findViewById(R.id.uplods);
        mImageView = findViewById(R.id.imageView);
        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotos();


            }
        });


        dateFormate = new SimpleDateFormat("dd-MM-yyyy");
        et_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(inform.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        et_tgl.setText(dateFormate.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //permission
        if (ContextCompat.checkSelfPermission(inform.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(inform.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);

        }
        et_ipk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

            }
        });


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = et_nim.getText().toString();
                String nama = et_nama.getText().toString();
                String jurusan = et_jurusan.getSelectedItem().toString();
                String tanggal = et_tgl.getText().toString();
                String prodi = sp_prodi.getSelectedItem().toString();
                String nomor = et_nomor.getText().toString();
                String email = et_email.getText().toString();
                String ipk = et_ipk.getText().toString();
                String alamat = ml_alamat.getText().toString();
                Float nmr = Float.parseFloat(nomor);


                String gol = "";
                if (g_a.isChecked()) {
                    gol += "A";
                }
                if (g_b.isChecked()) {
                    gol += "B";
                }
                if (g_ab.isChecked()) {
                    gol += "AB";
                }
                if (g_o.isChecked()) {
                    gol += "O";

                }
                String jns_kelmain = "";

                if (laki.isChecked()) {
                    jns_kelmain += "Laki-laki";
                }
                if (perempuan.isChecked()) {
                    jns_kelmain += "Perempuan";


                }
                final ProgressDialog dialog = new ProgressDialog(inform.this);
                dialog.setTitle("File Uploader");
                dialog.show();



                if (TextUtils.isEmpty(nim)) {
                    input((EditText) et_nim, "NIM");


                } else if (TextUtils.isEmpty(nama)) {
                    input((EditText) et_nama, "Nama");
                } else if (TextUtils.isEmpty(tanggal)) {
                    input((EditText) et_tgl, "tanggal");
                }
                else if (TextUtils.isEmpty(nomor)) {
                    input((EditText) et_nomor, "Nomor hp");
                }
                else if (TextUtils.isEmpty(ipk)) {
                    input((EditText) et_ipk, "ipk");
                }else if (TextUtils.isEmpty(email)) {
                    input((EditText) et_email, "email");
                }
                else if (TextUtils.isEmpty(alamat)) {
                    input((EditText) ml_alamat, "alamat");
                }
                else if (!g_a.isChecked() && !g_ab.isChecked() && !g_b.isChecked() && !g_o.isChecked()) {
                    Toast.makeText(inform.this, "centang salah satu atau lebih", Toast.LENGTH_SHORT).show();;
                }else if (nmr < 4.1) {
                    Toast.makeText(inform.this, "ipk melebihi standart", Toast.LENGTH_SHORT).show();
                }else if (!laki.isChecked()&&!perempuan.isChecked()) {
                    Toast.makeText(inform.this, "chek salah satu", Toast.LENGTH_SHORT).show();
                }

                else {
                    FirebaseStorage mStorageRef = FirebaseStorage.getInstance();
                    final StorageReference uploader = mStorageRef.getReference("Image1"+new Random().nextInt(50));

                    String finalGol = gol;
                    String finalJns_kelmain = jns_kelmain;
                    uploader.putFile(imageuri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                  uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                      @Override
                                      public void onSuccess(Uri uri) {
                                          String imageUrl = uri.toString();
                                          database.child("Mahasiswa").push()
                                                  .setValue(new dataMahasiswa(nim,nama,jurusan, prodi, finalGol, finalJns_kelmain, tanggal, nomor, email, alamat, ipk,imageUrl))
                                                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                      @Override
                                                      public void onSuccess(Void aVoid) {
                                                          Toast.makeText(inform.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                                          bersihkan(v);



                                                      }
                                                  }).addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  Toast.makeText(inform.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();

                                              }
                                          });
                                      }
                                  });
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded :"+(int)percent+" %");

                        }
                    });



                }
            }
        });
    }









    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    private void choosePhotos() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {
        if(requestCode == 2 && resultCode == RESULT_OK && data !=null && data.getData() !=null){
            imageuri = data.getData();
            try{
                Picasso.with(this).load(imageuri).into(mImageView);
                mImageView.setImageURI(imageuri);
            }catch (Exception ex)
            {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);


    }
    public void bersihkan (View v){
        Intent intent= getIntent();
        finish();
        startActivity(intent);
        et_nim.requestFocus();
    }



    private void input(EditText txt, String s){
        txt.setError(s+"tidak boleh Kosong");
        txt.requestFocus();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+ location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(inform.this, MapsActivity.class);
        i.putExtra("lat", location.getLatitude());
        i.putExtra("long", location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String addres = addressList.get(0).getAddressLine(0);
            et_ipk.setText(addres);
            startActivity(i);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    @SuppressLint("MissingPermission")
    private  void getLocation(){

        try {

            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,5, inform.this);

        }catch (Exception e){
            e.printStackTrace();

        }

    }
}
