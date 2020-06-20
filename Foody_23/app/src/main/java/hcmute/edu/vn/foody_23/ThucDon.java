package hcmute.edu.vn.foody_23;

public class ThucDon {
    private String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    private String Price;

    public ThucDon(String title, String price) {
        Title = title;
        Price = price;
    }

}
