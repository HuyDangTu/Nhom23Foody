package hcmute.edu.vn.foody_23;

public class MonAn {
    private String Title;
    private String Description;
    private String Thumbnail;

    public MonAn(String title, String description,String thumbnail) {
        Title = title;
        Description = description;
        Thumbnail = thumbnail;
    }

    public MonAn() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }
}
