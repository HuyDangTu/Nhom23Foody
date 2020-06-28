package hcmute.edu.vn.foody_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtTimKiem;
    List<CuaHang> monAnList = new ArrayList<>();
    TextView txtTinhThanh;
    TextView txtThucDon;
    Cursor cursor;
    int tinhThanhID = 63;
    Location currentLocation;

    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static final int REQUEST_WRITE_PERMISSION = 2;
    public static final int REQUEST_CALL_PERMISSION = 3;
    public static Database database;
    String[] appPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
    };
    private static final int PERMISSION_REQUEST_CODE =1240;

//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(final Location location) {
//            //your code here
//            currentLocation = location;
//        }
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    };
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkAndRequestPermissions())
        {
            initApp();
        }



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

        Filter();

//        edtTimKiem = (EditText) findViewById(R.id.search_Index);
//        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    Intent intent = new Intent(MainActivity.this,SearchActivity.class);
//                    intent.putExtra("Keyword",edtTimKiem.getText().toString());
//                    intent.putExtra("provinceId",tinhThanhID);
//                    intent.putExtra("long",String.valueOf(currentLocation.getLongitude()));
//                    intent.putExtra("lat",String.valueOf(currentLocation.getLatitude()));
//                    intent.putExtra("provinceName",txtTinhThanh.getText().toString());
//                    startActivity(intent);
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    public void Filter(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
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
    }

    @SuppressLint("MissingPermission")
    private void initApp() {
        final LocationListener mLocationListener = new LocationListener() {
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
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, mLocationListener);

        edtTimKiem = (EditText) findViewById(R.id.search_Index);
        edtTimKiem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                    intent.putExtra("Keyword",edtTimKiem.getText().toString());
                    intent.putExtra("provinceId",tinhThanhID);
                    intent.putExtra("long",String.valueOf(currentLocation.getLongitude()));
                    intent.putExtra("lat",String.valueOf(currentLocation.getLatitude()));
                    intent.putExtra("provinceName",txtTinhThanh.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_REQUEST_CODE)
        {
            HashMap<String,Integer> permissionRequests = new HashMap<> (  );
            int deniedCount = 0;
            for(int i=0; i<grantResults.length;i++)
            {
                if(grantResults[i]==PackageManager.PERMISSION_DENIED)
                {
                    permissionRequests.put ( permissions[i],grantResults[i] );
                    deniedCount++;
                }
            }
            if(deniedCount ==0)
            {
                initApp ();
            }
            else
            {
                for(Map.Entry<String,Integer> entry : permissionRequests.entrySet ())
                {
                    String permName = entry.getKey ();
                    int permResult= entry.getValue ();
                    if (ActivityCompat.shouldShowRequestPermissionRationale ( this,permName ))
                    {
                        showDialog ( "","This app needs Location and Storage and Call permissions to work without and problems.",
                                "Yes, Grant Permissions",
                                new DialogInterface.OnClickListener ( ){
                            @Override
                            public void onClick(DialogInterface dialogInterface,int i) {
                                dialogInterface.dismiss ();
                                checkAndRequestPermissions ();
                                 }
                            },
                                    "No, Exit app",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss ();
                                        finish ();
                                    }
                                },false);
                     }
                    else {
                        showDialog ( "", "You have denied some permissions.Allow all permissions at [Settings] > [Permissions]",
                                "Go to Settings",
                                new DialogInterface.OnClickListener () {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss ();
                                        Intent intent = new Intent ( Settings.ACTION_APPLICATION_DETAILS_SETTINGS ,
                                        Uri.fromParts ( "package", getPackageName (), null) );
                                        intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                                        startActivity ( intent );
                                        finish ();
                                    }
                                    },
                                    "No, Exit App", new DialogInterface.OnClickListener (){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        dialogInterface.dismiss ();
                                        finish ();
                                    }
                                },false
                        );
                        break;
                    }
                }
            }
        }

        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
    }

    private AlertDialog showDialog(String title, String msg, String positiveLabel, DialogInterface.OnClickListener positiveOnClick, String negativeLabel, DialogInterface.OnClickListener negativeOnClick, boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder ( this );
        builder.setTitle ( title );
        builder.setCancelable ( isCancelAble );
        builder.setMessage ( msg );
        builder.setPositiveButton ( positiveLabel,positiveOnClick );
        builder.setNegativeButton ( negativeLabel,negativeOnClick );
        AlertDialog alert = builder.create ();
        alert.show ();
        return alert;
    }
}
