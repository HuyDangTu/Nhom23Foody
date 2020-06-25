package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseAccess  {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the databases connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
        this.database = openHelper.getReadableDatabase();
    }

    /**
     * Close the databases connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    /**
     * Read all quotes from the databases.
     *
     * @return a List of quotes
     */

    public List<MonAn> getMonAn(int IdTinhThanh) {
        List<MonAn> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id, Store.Description, Store.Name, Image.HinhAnh " +
                " from Store inner join Image on Image.Store_Id = Store.Id inner join Province on Store.Province_Id = Province.Id " +
                " where Image.Kieuhinhanh = 'thumb' and Store.Province_Id = " + "'"+IdTinhThanh+"'", null);
         cursor.moveToFirst();
        list.clear();
            while (!cursor.isAfterLast()) {
                // MonAn monAn = new MonAn();
                String Desc = cursor.getString(1);
                String Name = cursor.getString(2);
                String Img = cursor.getString(3);
                int ID = cursor.getInt ( 0 );
                MonAn monAn = new MonAn(Name, Desc, Img,ID);
                list.add(monAn);
                cursor.moveToNext();
            }
        cursor.close();
        return list;
    }

    public List<CuaHang> timKiemQuanAn(String keyWord,int provinceId) {
        List<CuaHang> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id,Store.Name,Store.Address,Store.type,Store.score,Store.comment,Image.HinhAnh " +
                "from Store inner join Image on Image.Store_Id = Store.Id where " +
                "Image.Kieuhinhanh ='thumb' and Store.Province_Id = "+provinceId+" and Store.Name like "+"'%"+keyWord+"%'", null);
        cursor.moveToFirst();
        list.clear();
        while (!cursor.isAfterLast()) {
            String Id = String.valueOf(cursor.getInt(0));
            String Name = cursor.getString(1);
            String Address = cursor.getString(2);
            String type = cursor.getString(3);
            String score = cursor.getString(4);
            String comment = String.valueOf(cursor.getInt(5));
            String Img = cursor.getString(6);
            CuaHang cuaHang = new CuaHang(Id,Name,Address,"8","9",comment,Img,score,type);

            list.add(cuaHang);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<CuaHang> timKiemQuanAnMacDinh(String keyWord, int provinceId, Context context, Location location) {
        List<CuaHang> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id,Store.Name,Store.Address,Store.type,Store.score,Store.comment,Image.HinhAnh " +
                "from Store inner join Image on Image.Store_Id = Store.Id where " +
                "Image.Kieuhinhanh ='thumb' and Store.Province_Id = "+provinceId+" and Store.Name like "+"'%"+keyWord+"%'", null);
        cursor.moveToFirst();
        list.clear();
        while (!cursor.isAfterLast()) {
            String Id = String.valueOf(cursor.getInt(0));
            String Name = cursor.getString(1);
            String Address = cursor.getString(2);
            String distance = String.format ( "%.2f",DistanceCalculation(context,Address,location));
            //Toast.makeText(context,String.valueOf(distance),Toast.LENGTH_SHORT).show();
            String type = cursor.getString(3);
            String score = cursor.getString(4);
            String comment = String.valueOf(cursor.getInt(5));
            String Img = cursor.getString(6);
            CuaHang cuaHang = new CuaHang(Id,Name,Address,distance,"9",comment,Img,score,type);
            list.add(cuaHang);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<CuaHang> timKiemQuanAnPhoBien(String keyWord,int provinceId,Context context,Location location) {
        List<CuaHang> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id,Store.Name,Store.Address,Store.type,Store.score,Store.comment, "+
                "Image.HinhAnh from Store inner join Image on Image.Store_Id = Store.Id where Image.Kieuhinhanh ='thumb' and "+
                "Store.Province_Id = "+provinceId + " and Store.Name like '%"+keyWord+"%' order by Store.score desc",null);
        cursor.moveToFirst();
        list.clear();
        while (!cursor.isAfterLast()) {
            String Id = String.valueOf(cursor.getInt(0));
            String Name = cursor.getString(1);
            String Address = cursor.getString(2);
            String distance = String.format ( "%.2f",DistanceCalculation(context,Address,location));
            String type = cursor.getString(3);
            String score = cursor.getString(4);
            String comment = String.valueOf(cursor.getInt(5));
            String Img = cursor.getString(6);
            CuaHang cuaHang = new CuaHang(Id,Name,Address,distance,"9",comment,Img,score,type);

            list.add(cuaHang);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<CuaHang> timKiemQuanAnGanDay(String keyWord,int provinceId,Context context,Location location) {
        List<CuaHang> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id,Store.Name,Store.Address,Store.type,Store.score,Store.comment, "+
                "Image.HinhAnh from Store inner join Image on Image.Store_Id = Store.Id where Image.Kieuhinhanh ='thumb' and "+
                "Store.Province_Id = "+provinceId+ " and Store.Name like '%"+keyWord+"%' order by Store.score desc",null);
        cursor.moveToFirst();
        list.clear();
        while (!cursor.isAfterLast()) {
            String Address = cursor.getString(2);
            Double distance = DistanceCalculation(context,Address,location);
            if(distance<5) {
                String Id = String.valueOf(cursor.getInt(0));
                String Name = cursor.getString(1);
                String type = cursor.getString(3);
                String score = cursor.getString(4);
                String comment = String.valueOf(cursor.getInt(5));
                String Img = cursor.getString(6);
                CuaHang cuaHang = new CuaHang(Id, Name, Address, String.format ( "%.2f",distance), "9", comment, Img, score, type);
                list.add(cuaHang);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<TinhThanh> getTinhThanh(){
        List<TinhThanh> tinhThanhList = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select *" + " from Province", null);
        cursor.moveToFirst();
        tinhThanhList.clear();
        while (!cursor.isAfterLast()) {
            int idTinhThanh = cursor.getInt(0);
            String tenTinhThanh = cursor.getString(1);
            TinhThanh tinhThanh = new TinhThanh(idTinhThanh,tenTinhThanh);
            tinhThanhList.add(tinhThanh);
            cursor.moveToNext();
        }
        cursor.close();
        return tinhThanhList;
    }

    // Lấy ID Tỉnh theo tên
    int getIdTinhThanh(String TenTinhThanh){
        int tinhThanhID = 0;
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Id" +
                " from Province where Province.Ten =" + "'"+TenTinhThanh+"'" , null);
        cursor.moveToFirst();
        tinhThanhID = cursor.getInt(0);
        cursor.close();
        return tinhThanhID;
    }

    public Store getStore(String key)
    {
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery ("select * from Store where Store.Id= "+String.valueOf ( key ), null);
        cursor.moveToFirst ();
        int StId = cursor.getInt ( 0 );
        String StAddress = cursor.getString ( 1 );
        String StName = cursor.getString ( 2 );
        String StDes = cursor.getString ( 3 );
        int StProvince = cursor.getInt ( 4 );
        String StOpentime = cursor.getString ( 5 );
        String StWifiname = cursor.getString ( 6 );
        String StWifipass = cursor.getString ( 7 );
        String StNum = cursor.getString ( 11 );
        String StClose = cursor.getString ( 12 );
        Store QuanAn = new Store(StId,StAddress,StName,StDes,StProvince,StOpentime,StWifiname,StWifipass,StNum,StClose);
        cursor.close ();
        return QuanAn;
    }
    public void UpdateWifi(String key, String pass)
    {
        database = openHelper.getWritableDatabase ();
        String Query = "UPDATE Store SET Wifi_password='"+ pass+"' WHERE Store.Id= " +String.valueOf (key);
        SQLiteStatement statement = database.compileStatement ( Query );
        statement.execute ();
    }

    public Double DistanceCalculation(Context mConText,String Address,Location location)
    {
        Double distance = Double.valueOf(0.000);
        Geocoder geocoder = new Geocoder(mConText, Locale.getDefault ());
        try {
            List addressList = geocoder.getFromLocationName(Address, 1);
            android.location.Address destination = (Address) addressList.get ( 0 ) ;
            Location locationA = new Location("point A");

            locationA.setLatitude(destination.getLatitude ());
            locationA.setLongitude(destination.getLongitude ());

            Location locationB = new Location("point B");
            locationB.setLatitude(location.getLatitude ());
            locationB.setLongitude(location.getLongitude ());

            distance = Double.valueOf ( locationA.distanceTo(locationB)/1000);
            return distance;

        }
        catch (IOException e) {
            e.printStackTrace ();
            return distance;
        }
    }
}
