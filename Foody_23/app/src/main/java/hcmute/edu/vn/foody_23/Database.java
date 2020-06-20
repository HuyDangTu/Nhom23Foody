package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{


    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public void insert_Province( String Province_Name){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Province VALUES(null,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,Province_Name);
        statement.executeInsert();
    }
    //INSERT HÌNH ẢNH
    public void insert_Img( byte[] HinhAnh,Integer store_Id,String Kieuhinhanh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Image VALUES(null,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1,HinhAnh);
        statement.bindLong(2,store_Id);
        statement.bindString(3,Kieuhinhanh);
        statement.executeInsert();
    }
    public void insert_Store(Store store){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Store VALUES(null,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,store.getAddress());
        statement.bindString(2,store.getName());
        statement.bindString(3,store.getDescription());
        statement.bindLong(4,store.getProvince_Id());
        statement.bindString(5,store.getOpenTime());
        statement.bindString(6,store.getWifi_name());
        statement.bindString(7,store.getWifi_Password());
        statement.executeInsert();

    }

    public Cursor Load_all_store()
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("select Store.Id,Store.Description,Store.Name,Image.HinhAnh from Store inner join Image on Image.Store_Id = Store.Id where Image.Kieuhinhanh ='thumb'",null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
