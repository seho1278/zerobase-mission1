package zerobase.wifi.dto;

public class TbPublicWifiInfo {
    private int list_total_count;
    private Result RESULT;
    private Row[] row;

    public int getList_total_count() {
        return list_total_count;
    }

    public void setList_total_count(int list_total_count) {
        this.list_total_count = list_total_count;
    }

    public Result getRESULT() {
        return RESULT;
    }

    public void setRESULT(Result RESULT) {
        this.RESULT = RESULT;
    }

    public Row[] getRow() {
        return row;
    }

    public void setRow(Row[] row) {
        this.row = row;
    }
}
