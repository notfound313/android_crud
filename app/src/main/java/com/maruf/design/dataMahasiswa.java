package com.maruf.design;

public class dataMahasiswa {
    private String nim;
    private String nama;
    private String jurusan;
    private String key;
    private String prodi;
    private String gol;
    private String jns_kelmain;
    private String tanggal;
    private String nomor;
    private String email;
    private String alamat;
    private String ipk;
    private String imageUrl;


    public dataMahasiswa() {

    }



    public dataMahasiswa(String nim, String nama, String jurusan, String prodi, String gol, String jns_kelmain, String tanggal,String nomor,String email,String alamat,String ipk,String imageUrl) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.prodi= prodi;
        this.gol=gol;
        this.jns_kelmain=jns_kelmain;
        this.tanggal=tanggal;
        this.nomor = nomor;
        this.email= email;
        this.ipk= ipk;
        this.alamat=alamat;
        this.imageUrl= imageUrl;




    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
    public String getProdi(){return  prodi;}
    public void setProdi(String prodi){this.prodi= prodi;}

    public String getGol(){return  gol;}
    public void setGol(String gol){this.gol= gol;}

    public String getJenis(){return  jns_kelmain;}
    public void setJenis(String jns_kelmain){this.jns_kelmain= jns_kelmain;}

    public String getTanggal(){return  tanggal;}
    public void setTanggal(String tanggal){this.tanggal= tanggal;}

    public String getNomor(){return  nomor;}
    public void setNomor(String nomor){this.nomor= nomor;}

    public String getEmail(){return  email;}
    public void setEmail(String email){this.email= email;}
    public String getIpk(){return  ipk;}
    public void setIpk(String ipk){this.ipk= ipk;}

    public String getAlamat(){return  alamat;}
    public void setAlamat(String alamat){this.alamat= alamat;}

    public String getImageUrl(){return  imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl= imageUrl;}



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
