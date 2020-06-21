package hcmute.edu.vn.foody_23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Intent;
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
    List<MonAn> monAnList = new ArrayList<>();
    TextView txtTinhThanh;
    TextView txtThucDon;
    Cursor cursor;
    int tinhThanhID = 17;

    public static Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
