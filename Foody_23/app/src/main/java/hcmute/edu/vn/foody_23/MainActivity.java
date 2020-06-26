package hcmute.edu.vn.foody_23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtTimKiem;
    List<CuaHang> monAnList = new ArrayList<>();
    TextView txtTinhThanh;
    TextView txtThucDon;
    Cursor cursor;
    int tinhThanhID = 63;
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static final int REQUEST_WRITE_PERMISSION = 2;
    public static final int REQUEST_CALL_PERMISSION = 3;
    public static Database database;
    String[] appPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE
    };
    private static final int PERMISSION_REQUEST_CODE =1240;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkAndRequestPermissions())
        {
            initApp();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);



        // CÁC SỰ KIỆN CLICK
        txtTinhThanh = findViewById(R.id.txtTinhThanh);
        txtTinhThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TinhThanhActivity.class);
                intent.putExtra("CurrentProvince",txtTinhThanh.getText().toString());
                startActivity(intent);
            }
        });

        // Lọc món ăn theo TỈNH
        Intent intent = getIntent();
        String x = intent.getStringExtra("Province");
        if (x != null){
            txtTinhThanh.setText(x);
            tinhThanhID = DatabaseAccess.getInstance(MainActivity.this).getIdTinhThanh(x);
            monAnList = DatabaseAccess.getInstance(MainActivity.this).getMonAn(tinhThanhID);
            RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this,monAnList);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setAdapter(recycleViewAdapter);
        }
        else {
            txtTinhThanh.setText("TP HCM");
            monAnList = DatabaseAccess.getInstance(MainActivity.this).getMonAn(63);
            RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this,monAnList);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setAdapter(recycleViewAdapter);
        }

        edtTimKiem = (EditText) findViewById(R.id.search_Index);
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                    intent.putExtra("Keyword",edtTimKiem.getText().toString());
                    intent.putExtra("provinceId",tinhThanhID);
                    intent.putExtra("provinceName",txtTinhThanh.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    private void initApp() {
    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<> (  );
        for(String perm : appPermissions)
        {
            if (ContextCompat.checkSelfPermission ( this,perm )!=PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add ( perm );
            }
        }
        if(!listPermissionsNeeded.isEmpty ())
        {
            ActivityCompat.requestPermissions ( this,listPermissionsNeeded.toArray (new String[listPermissionsNeeded.size ()]),PERMISSION_REQUEST_CODE );
            return false;
        }
        return true;
    }
    
    
}
