package zerobase.wifi.model;


import java.time.LocalDateTime;


public class HistoryModel {
    private int HIS_ID;
    private double LAT;
    private double LNT;
    private String HIS_DT;

    public int getHIS_ID() {
        return HIS_ID;
    }

    public void setHIS_ID(int HIS_ID) {
        this.HIS_ID = HIS_ID;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLNT() {
        return LNT;
    }

    public void setLNT(double LNT) {
        this.LNT = LNT;
    }

    public String getHIS_DT() {
        return HIS_DT;
    }

    public void setHIS_DT(String HIS_DT) {
        this.HIS_DT = HIS_DT;
    }
}
