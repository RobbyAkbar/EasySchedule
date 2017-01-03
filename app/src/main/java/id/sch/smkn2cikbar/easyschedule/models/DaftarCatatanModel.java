package id.sch.smkn2cikbar.easyschedule.models;

/**
 * Created by Robby Akbar on 30/12/16.
 */

public class DaftarCatatanModel {

    private int id;
    private String pelajaran, tugas, deadline;

    public DaftarCatatanModel (int id, String pelajaran, String tugas, String deadline){
        this.id = id;
        this.pelajaran = pelajaran;
        this.tugas = tugas;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPelajaran() {
        return pelajaran;
    }

    public void setPelajaran(String pelajaran) {
        this.pelajaran = pelajaran;
    }

    public String getTugas() {
        return tugas;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
