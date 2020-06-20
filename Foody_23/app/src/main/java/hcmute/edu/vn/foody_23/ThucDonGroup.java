package hcmute.edu.vn.foody_23;

public class ThucDonGroup {
    private int id;
    private String name;
    private int CuaHangId;

    public ThucDonGroup(int id, String name, int cuaHangId) {
        this.id = id;
        this.name = name;
        CuaHangId = cuaHangId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCuaHangId() {
        return CuaHangId;
    }

    public void setCuaHangId(int cuaHangId) {
        CuaHangId = cuaHangId;
    }
}
