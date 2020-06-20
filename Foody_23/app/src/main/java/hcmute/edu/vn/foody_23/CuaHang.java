package hcmute.edu.vn.foody_23;

import android.widget.ImageView;

public class CuaHang {

    private String  id,name,score,address,distance,type,imageCount,comment;
    private String image;

    public CuaHang(String id,String name,String address, String distance, String imageCount, String comment, String image,String score,String type) {
        this.id= id;
        this.name = name;
        this.score = score;
        this.address = address;
        this.distance = distance;
        this.type = type;
        this.imageCount = imageCount;
        this.comment = comment;
        this.image = image;
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
