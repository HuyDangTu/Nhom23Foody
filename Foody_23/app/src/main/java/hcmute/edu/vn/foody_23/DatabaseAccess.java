package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
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
                MonAn monAn = new MonAn(Name, Desc, Img);
                list.add(monAn);
                cursor.moveToNext();
            }
        cursor.close();
        return list;
    }

    public List<CuaHang> timKiemQuanAn(String keyWord) {
        List<CuaHang> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id,Store.Name,Store.Address,Store.type,Store.score,Store.comment,Image.HinhAnh " +
                "from Store inner join Image on Image.Store_Id = Store.Id where " +
                "Image.Kieuhinhanh ='thumb' and Store.Name like "+ "'%"+keyWord+"%'", null);
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

    public List<CuaHang> timKiemQuanAnPhoBien(String keyWord) {
        List<CuaHang> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id,Store.Name,Store.Address,Store.type,Store.score,Store.comment, "+
                "Image.HinhAnh from Store inner join Image on Image.Store_Id = Store.Id where Image.Kieuhinhanh ='thumb' and "+
                "Store.Name like '%"+keyWord+"%' order by Store.score desc",null);
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
    public List<CuaHang> timKiemQuanAnGanDay(String keyWord) {
        List<CuaHang> list = new ArrayList<>();
        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select Store.Id,Store.Name,Store.Address,Store.type,Store.score,Store.comment, "+
                "Image.HinhAnh from Store inner join Image on Image.Store_Id = Store.Id where Image.Kieuhinhanh ='thumb' and "+
                "Store.Name like '%"+keyWord+"%' order by Store.score desc",null);
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
}
