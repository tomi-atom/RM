package atomstudioid.rm;

import java.io.Serializable;

public class Data implements Serializable {
    String namaRm;
    String alamatRm;
    String menu;
    String waktuBuka;

    public String getNamaRm() {
        return namaRm;
    }

    public void setNamaRm(String namaRm) {
        this.namaRm = namaRm;
    }

    public String getAlamatRm() {
        return alamatRm;
    }

    public void setAlamatRm(String alamatRm) {
        this.alamatRm = alamatRm;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getWaktuBuka() {
        return waktuBuka;
    }

    public void setWaktuBuka(String waktuBuka) {
        this.waktuBuka = waktuBuka;
    }

    @Override
    public String toString() {
        return "Data{" +
                "namaRm='" + namaRm + '\'' +
                ", alamatRm='" + alamatRm + '\'' +
                ", menu='" + menu + '\'' +
                ", waktuBuka='" + waktuBuka + '\'' +
                '}';
    }
    public Data(String nm, String almt, String mn,String wkt){
        namaRm = nm;
        alamatRm = almt;
        menu = mn;
        waktuBuka = wkt;
    }

}
