package hcmute.edu.vn.foody_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    CardView cardView;
    List<CuaHang> CuaHangList;
    int provinceId;
    String keyWord;
    EditText edtTimKiem;
    TextView txtViewTinhThanh;
    ImageView backBtn;
    Button defaultBtn,popularBtn,nearBtn;
    public static Database database;
    private static final float LOCATION_REFRESH_DISTANCE = 100 ;
    private static final long LOCATION_REFRESH_TIME = 1;
    Location currentLocation;


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            currentLocation = location;
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        CuaHangList = new ArrayList<>();
        Intent intent = getIntent();
        txtViewTinhThanh = (TextView) findViewById(R.id.txtTinhThanh);

        keyWord = intent.getStringExtra("Keyword");
        provinceId = intent.getIntExtra("provinceId",-1);
        txtViewTinhThanh.setText("Địa điểm ở "+intent.getStringExtra("provinceName"));

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);

        //tìm cua hang theo input từ input từ main activity
        CuaHangList = DatabaseAccess.getInstance(SearchActivity.this).timKiemQuanAn(keyWord,provinceId);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ResultRecyclerView);
        SeachResultRecycleViewAdapter recycleViewAdapter = new SeachResultRecycleViewAdapter(SearchActivity.this,CuaHangList);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,1));
        recyclerView.setAdapter(recycleViewAdapter);

        edtTimKiem = (EditText) findViewById(R.id.searchBox);
        backBtn = (ImageView)  findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyWord = edtTimKiem.getText().toString();
                    CuaHangList = DatabaseAccess.getInstance(SearchActivity.this).timKiemQuanAnMacDinh(keyWord,provinceId,SearchActivity.this,currentLocation);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ResultRecyclerView);
                    SeachResultRecycleViewAdapter recycleViewAdapter = new SeachResultRecycleViewAdapter(SearchActivity.this,CuaHangList);
                    recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,1));
                    recyclerView.setAdapter(recycleViewAdapter);
                }
                return false;
            }
        });
//////////////////CLICK EVENT
        defaultBtn = (Button) findViewById(R.id.defaultBtn);
        popularBtn = (Button) findViewById(R.id.popularBtn);
        nearBtn = (Button) findViewById(R.id.nearBtn);

        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = edtTimKiem.getText().toString();
                CuaHangList = DatabaseAccess.getInstance(SearchActivity.this).timKiemQuanAnMacDinh(keyWord,provinceId,SearchActivity.this,currentLocation);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ResultRecyclerView);
                SeachResultRecycleViewAdapter recycleViewAdapter = new SeachResultRecycleViewAdapter(SearchActivity.this,CuaHangList);
                recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,1));
                recyclerView.setAdapter(recycleViewAdapter);
            }
        });
        popularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = edtTimKiem.getText().toString();
                CuaHangList = DatabaseAccess.getInstance(SearchActivity.this).timKiemQuanAnPhoBien(keyWord,provinceId,SearchActivity.this,currentLocation);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ResultRecyclerView);
                SeachResultRecycleViewAdapter recycleViewAdapter = new SeachResultRecycleViewAdapter(SearchActivity.this,CuaHangList);
                recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,1));
                recyclerView.setAdapter(recycleViewAdapter);
            }
        });
        nearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = edtTimKiem.getText().toString();
                CuaHangList = DatabaseAccess.getInstance(SearchActivity.this).timKiemQuanAnGanDay(keyWord,provinceId,SearchActivity.this,currentLocation);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ResultRecyclerView);
                SeachResultRecycleViewAdapter recycleViewAdapter = new SeachResultRecycleViewAdapter(SearchActivity.this,CuaHangList);
                recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this,1));
                recyclerView.setAdapter(recycleViewAdapter);
            }
        });
    }
}
