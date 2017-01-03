package id.sch.smkn2cikbar.easyschedule.helper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Robby Akbar on 30/12/16.
 */

public class DaftarCatatan extends RealmObject {

    @PrimaryKey private int id;
    private String pelajaran, tugas, deadline;

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
