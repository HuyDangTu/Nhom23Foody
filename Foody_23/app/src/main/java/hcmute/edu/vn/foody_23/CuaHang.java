package hcmute.edu.vn.foody_23;

import android.graphics.Path;
import android.widget.ImageView;

public class CuaHang {

    private String  id,name,score,address,distance,type,imageCount,comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getProvince_Id() {
        return Province_Id;
    }

    public void setProvince_Id(Integer province_Id) {
        Province_Id = province_Id;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getWifi_name() {
        return wifi_name;
    }

    public void setWifi_name(String wifi_name) {
        this.wifi_name = wifi_name;
    }

    public String getWifi_Password() {
        return wifi_Password;
    }

    public void setWifi_Password(String wifi_Password) {
        this.wifi_Password = wifi_Password;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    private String Description;
    private Integer Province_Id;
    private String OpenTime;
    private String wifi_name;
    private String wifi_Password;
    private String PhoneNum;
    private String image;

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    private String CloseTime;

    public CuaHang(String id,String name,String address, String distance, String imageCount, String comment, String image,String score,String type,String description, Integer province_Id, String openTime, String wifi_name, String wifi_Password,String PhoneNum, String closeTime) {
        this.id= id;
        this.name = name;
        this.score = score;
        this.address = address;
        this.distance = distance;
        this.type = type;
        this.imageCount = imageCount;
        this.comment = comment;
        this.image = image;
        Description= description;
        Province_Id = province_Id;
        OpenTime = openTime;
        this.wifi_name = wifi_name;
        this.wifi_Password = wifi_Password;
        this.PhoneNum = PhoneNum;
        this.CloseTime = closeTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public String getType() {
        return type;
    }

    public String getImageCount() {
        return imageCount;
    }

    public String getCommentCount() {
        return comment;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageCount(String imageCount) {
        this.imageCount = imageCount;
    }

    public void setCommentCount(String commentCount) {
        this.comment = commentCount;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
