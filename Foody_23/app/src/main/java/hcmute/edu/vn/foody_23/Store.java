package hcmute.edu.vn.foody_23;

import androidx.annotation.Nullable;

public class Store {

    @Nullable
    private Integer id;
    private String Address;
    private String Name;
    private String Description;
    private Integer Province_Id;
    private String OpenTime;
    private String wifi_name;
    private String wifi_Password;

    public Store(){

    }

    public Store(Integer id, String address, String name, String description, Integer province_Id, String openTime, String wifi_name, String wifi_Password) {
        this.id = id;
        Address = address;
        Name = name;
        Description = description;
        Province_Id = province_Id;
        OpenTime = openTime;
        this.wifi_name = wifi_name;
        this.wifi_Password = wifi_Password;
    }

    public Integer getId() {
        return id;
    }


    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public Integer getProvince_Id() {
        return Province_Id;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public String getWifi_name() {
        return wifi_name;
    }

    public String getWifi_Password() {
        return wifi_Password;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setAddress(String address) {
        Address = address;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setProvince_Id(Integer province_Id) {
        Province_Id = province_Id;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public void setWifi_name(String wifi_name) {
        this.wifi_name = wifi_name;
    }

    public void setWifi_Password(String wifi_Password) {
        this.wifi_Password = wifi_Password;
    }
}

