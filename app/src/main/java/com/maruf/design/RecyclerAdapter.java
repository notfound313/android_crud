package com.maruf.design;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<dataMahasiswa> listM;
    private Activity activity;
    DatabaseReference database= FirebaseDatabase.getInstance().getReference();

    public RecyclerAdapter(List<dataMahasiswa> listM, Activity activity) {
        this.listM = listM;
        this.activity = activity;
    }

    public RecyclerAdapter(List<dataMahasiswa> listM) {
        this.listM=listM;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_home_read,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        dataMahasiswa msiswa= listM.get(position);
        holder.nim.setText("Kode Pos                     : " +msiswa.getNim());
        holder.nama.setText("Nama                   : "+msiswa.getNama());
        holder.jurusan.setText("Jurusan             : "+msiswa.getJurusan());
        holder.prodi.setText("Prodi                 : "+msiswa.getProdi());
        holder.gol.setText("Golongan Darah      : "+msiswa.getGol());
        holder.jns.setText("Jenis Kelamin         : "+msiswa.getJenis());
        holder.tgl.setText("Tanggal Lahir         : "+msiswa.getTanggal());
        holder.hp.setText("no Telpon              : "+msiswa.getNomor());
        holder.eml.setText("E-mail                : "+msiswa.getEmail());
        holder.ipk.setText("Cek lokasi pendaftaran                   : "+msiswa.getIpk());
        holder.almt.setText("Alamat               : "+msiswa.getAlamat());
        Picasso.with(activity).load(msiswa.getImageUrl()).into(holder.sowes);



        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(activity);
                builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.child("Mahasiswa").child(msiswa.getKey()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(activity, "Behasil dihapus", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();

                    }
                }).setMessage("Apakah ingin di hapus?"+msiswa.getNim());
                builder.show();
            }
        });

        holder.lis_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                FragmentManager manager= ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogForm dialogForm = new DialogForm(msiswa.getNim(),
                        msiswa.getNama(),msiswa.getJurusan(),msiswa.getKey(),msiswa.getTanggal(),msiswa.getNomor(),msiswa.getEmail(),msiswa.getAlamat(),msiswa.getIpk(),msiswa.getImageUrl(),msiswa.getJenis(),msiswa.getGol(),"Ubah");
                dialogForm.show(manager, "form");

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listM.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nim;
        TextView nama;
        TextView jurusan;
        TextView prodi;
        TextView gol;
        TextView jns;
        TextView tgl;
        TextView hp;
        TextView eml;
        TextView ipk;
        TextView almt;
        ImageView sowes;

        LinearLayout lis_item;
        ImageView hapus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nim = itemView.findViewById(R.id.nim);
            nama = itemView.findViewById(R.id.nama);
            prodi = itemView.findViewById(R.id.prodi);
            jurusan = itemView.findViewById(R.id.jurusan);
            gol = itemView.findViewById(R.id.gol);
            jns = itemView.findViewById(R.id.jns);
            hp = itemView.findViewById(R.id.hp);
            eml = itemView.findViewById(R.id.eml);
            tgl = itemView.findViewById(R.id.tgl);
            ipk = itemView.findViewById(R.id.ipk);
            almt = itemView.findViewById(R.id.almt);
            lis_item = itemView.findViewById(R.id.list_item);
            hapus = itemView.findViewById(R.id.hapus);
            sowes= itemView.findViewById(R.id.swowes);



        }
    }
}
