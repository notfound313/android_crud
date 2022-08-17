package com.maruf.design;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class DialogForm extends DialogFragment implements LocationListener {
    String nim;
    String nama;
    String jurusan;
    String key;
    String pilih;
    String email;
    String nomor;

    String prodi;
    String gol;
    String jns_kelmain;
    String imageUrl;
    String alamat;
    String ipk;



   DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String tanggal;

    public DialogForm(String nim, String nama, String jurusan, String key,String tanggal,String nomor,String email,String alamat,String ipk,String imageUrl,String jns_kelmain, String gol, String pilih) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.key = key;
        this.tanggal= tanggal;
        this.nomor= nomor;
        this.prodi=prodi;
        this.email=email;
        this.gol=gol;
        this.ipk=ipk;
        this.jns_kelmain= jns_kelmain;
        this.imageUrl=imageUrl;
        this.alamat=alamat;
        this.pilih= pilih;




    }
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
    EditText et_tgl;
    DatePickerDialog datePickerDialog;
    LocationManager locationManager;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.inform_input, container, false);



        et_nim = view.findViewById(R.id.nim);
        et_nama = view.findViewById(R.id.nama);
        et_jurusan = view.findViewById(R.id.jurusan);
        sp_prodi = view.findViewById(R.id.listprodi);
        btn_simpan = view.findViewById(R.id.btn_simpan);
        g_a = view.findViewById(R.id.g_a);
        g_b = view.findViewById(R.id.g_b);
        g_ab = view.findViewById(R.id.g_ab);
        g_o = view.findViewById(R.id.g_o);
        jenis_kelamin=view.findViewById(R.id.jenis_klmn);
        laki = view.findViewById(R.id.laki);
        perempuan = view.findViewById(R.id.perempuan);
        et_tgl = view.findViewById(R.id.tgl);
        et_email = view.findViewById(R.id.email);
        et_nomor = view.findViewById(R.id.noHp);
        ml_alamat = view.findViewById(R.id.alamt);
        et_ipk = view.findViewById(R.id.ipk);
        upld = view.findViewById(R.id.uplods);
        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosePhotos();
            }
        });
        mImageView = view.findViewById(R.id.imageView);
        et_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                et_tgl.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }


        });

        //permission
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);

        }
        et_ipk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

            }
        });







        et_nim.setText(nim);
        et_nama.setText(nama);


        et_tgl.setText(tanggal);
        et_nomor.setText(nomor);
        ml_alamat.setText(alamat);
        et_ipk.setText(ipk);
        if (jns_kelmain.equals("Perempuan")){
            perempuan.setChecked(true);

        }else{
            laki.setChecked(true);

        }
        Glide.with(getContext()).load(imageUrl).into(mImageView);
        if(gol.equals("A")){
            g_a.setChecked(true);
        }else if(gol.equals("B")){
            g_b.setChecked(true);
        }else if(gol.equals("AB")){
            g_ab.setChecked(true);
        }else{
            g_o.setChecked(true);
        }



        et_email.setText(email);



        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim= et_nim.getText().toString();
                String nama= et_nama.getText().toString();
                String jurusan = et_jurusan.getSelectedItem().toString();
                String tanggal = et_tgl.getText().toString();
                String prodi = sp_prodi.getSelectedItem().toString();
                String nomor = et_nomor.getText().toString();
                String email = et_email.getText().toString();
                String ipk = et_ipk.getText().toString();
                String alamat = ml_alamat.getText().toString();
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


                final ProgressDialog dialog = new ProgressDialog(view.getContext());
                dialog.setTitle("File Uploader");
                dialog.show();




                if(TextUtils.isEmpty(nim)){
                    input((EditText) et_nim, "NIM");


                }else if(TextUtils.isEmpty(nama)){
                    input((EditText) et_nama, "Nama");
                }else{
                     if (pilih.equals("Ubah") && imageUrl.isEmpty() || imageuri != null){
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
                                               dialog.dismiss();
                                               String imageUrl = uri.toString();
                                               database.child("Mahasiswa").child(key)
                                                       .setValue(new dataMahasiswa(nim,nama,jurusan, prodi, finalGol, finalJns_kelmain, tanggal, nomor, email, alamat, ipk,imageUrl))
                                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void aVoid) {
                                                               Toast.makeText(view.getContext(), "Data Berhasil di ubah", Toast.LENGTH_SHORT).show();


                                                           }
                                                       }).addOnFailureListener(new OnFailureListener() {
                                                   @Override
                                                   public void onFailure(@NonNull Exception e) {
                                                       Toast.makeText(view.getContext(), "Data gagal Di ubah", Toast.LENGTH_SHORT).show();

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

                    }else{
                         dialog.dismiss();
                         database.child("Mahasiswa").child(key)
                                 .setValue(new dataMahasiswa(nim,nama,jurusan, prodi, gol, jns_kelmain, tanggal, nomor, email, alamat, ipk,imageUrl))
                                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                         Toast.makeText(view.getContext(), "Data Berhasil di ubah", Toast.LENGTH_SHORT).show();


                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(view.getContext(), "Data gagal Di ubah", Toast.LENGTH_SHORT).show();

                             }
                         });

                     }

                }

            }
        });
        return view;


    }

    private void choosePhotos() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK && data !=null && data.getData() !=null){
            imageuri = data.getData();
            try{
                InputStream inputStream = getContext().getContentResolver().openInputStream(imageuri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!= null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }
    private void input(EditText txt,String s){
        txt.setError(s+"tidakboleh Kosong");
        txt.requestFocus();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(getContext(), ""+ location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String addres = addressList.get(0).getAddressLine(0);
            et_ipk.setText(addres);

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
    public void getLocation(){
        try {
            locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,5, (LocationListener) getContext());

        }catch (Exception e){
            e.printStackTrace();
    }
    }
}
