package hcmute.edu.vn.foody_23;

public class TinhThanh {
    private int idTinhThanh;
    private String tenTinhThanh;

    public int getIdTinhThanh() {
        return idTinhThanh;
    }

    public void setIdTinhThanh(int idTinhThanh) {
        this.idTinhThanh = idTinhThanh;
    }

    public String getTenTinhThanh() {
        return tenTinhThanh;
    }

    public void setTenTinhThanh(String tenTinhThanh) {
        this.tenTinhThanh = tenTinhThanh;
    }

    public TinhThanh(int idTinhThanh, String tenTinhThanh) {

        this.idTinhThanh = idTinhThanh;
        this.tenTinhThanh = tenTinhThanh;
    }

    public TinhThanh() {
    }
    @Override
    public String toString() {
        return this.tenTinhThanh;
    }
}
