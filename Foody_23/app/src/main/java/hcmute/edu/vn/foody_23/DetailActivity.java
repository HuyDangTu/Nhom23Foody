package hcmute.edu.vn.foody_23;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DetailActivity extends AppCompatActivity implements LocationListener,OnMapReadyCallback {


    TextView textView;
    TextView txtisOpen;
    TextView txtOpenTime;
    TextView txtDiaChi;
    TextView txtTenQuan;
    TextView txtProvince;
    TextView txtWifi;
    Store Quanan;
    Button BtnContact;
    GoogleMap map;
    List<String> imageList;

    ///////// LOCATION
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    public String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Intent intent = getIntent ();
        key = intent.getExtras ().getString ( "CurrentStore" );

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.detail_view );
        Quanan = DatabaseAccess.getInstance ( DetailActivity.this ).getStore ( key );
        /// TÌM KHOẢNG CÁCH
        txtLat = (TextView) findViewById ( R.id.KhoangCach );

        locationManager = (LocationManager) getSystemService ( Context.LOCATION_SERVICE );
        if (ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION);
            return;
        }
        locationManager.requestLocationUpdates ( LocationManager.GPS_PROVIDER, 0, 0, this );


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ().findFragmentById(R.id.myMaps);
        mapFragment.getMapAsync((OnMapReadyCallback) this);


/// ÁNH XẠ
        txtDiaChi = (TextView) findViewById ( R.id.DiaChi );
        txtOpenTime = (TextView) findViewById ( R.id.textOpenTime );
        txtTenQuan = (TextView) findViewById ( R.id.txtTenQuan );
        txtProvince = (TextView) findViewById ( R.id.txtDiaChi );
        txtisOpen = (TextView) findViewById ( R.id.textOpen );
        BtnContact = (Button) findViewById ( R.id.Contact ) ;
        txtWifi = (TextView) findViewById ( R.id.wifiactionicon );

        textView = (TextView) findViewById ( R.id.menutab );
        txtDiaChi.setText ( Quanan.getAddress () );
        txtTenQuan.setText ( Quanan.getName ()  );

        txtOpenTime.setText ( Quanan.getOpenTime () + " - " + Quanan.getCloseTime () );
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler2);
        Quanan = DatabaseAccess.getInstance ( DetailActivity.this ).getStore ( key );
        imageList = DatabaseAccess.getInstance(DetailActivity.this).GetImage(Quanan.getId ().toString ());
        DetailRecycleView recycleViewAdapter = new DetailRecycleView (this,imageList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(recycleViewAdapter);
        try {
            CompareTime ( Quanan.getOpenTime (),Quanan.getCloseTime () );
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        BtnContact.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String a1= Quanan.getPhoneNum ();
                Intent intent = new Intent ( Intent.ACTION_DIAL );
                intent.setData ( Uri.parse("tel:" + a1));

                startActivity ( intent );
            }
        } );
        txtWifi.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DialogWifi();
            }
        } );
        textView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( DetailActivity.this,ThucDonActivity.class );
                startActivity ( intent );
            }
        } );
    }

    private void DialogWifi() {
        final Dialog dialog = new Dialog ( this );
        dialog.setContentView ( R.layout.wifi_dialog );
        final TextView txtWifiName = (TextView) dialog.findViewById ( R.id.WifiName );
        final TextView txtWifiPass=(TextView) dialog.findViewById ( R.id.WifiPass );
        txtWifiName.setText ( Quanan.getWifi_name () );
        txtWifiPass.setText ( Quanan.getWifi_Password () );
        txtWifiPass.setTransformationMethod ( HideReturnsTransformationMethod.getInstance () );
        dialog.show ();

        Window window = dialog.getWindow();
        window.setLayout( GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.WRAP_CONTENT);
        Button submit = (Button) dialog.findViewById ( R.id.submitwifi );
        submit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

//      Toast.makeText ( DetailActivity.this,"Mật khẩu không hợp lệ",Toast.LENGTH_SHORT ).show ();
//
            DatabaseAccess.getInstance ( DetailActivity.this ).UpdateWifi ( key,txtWifiPass.getText ().toString () );


               dialog.cancel ();
                Toast.makeText ( DetailActivity.this,"Đã cập nhật mật khẩu",Toast.LENGTH_SHORT ).show ();
                finish();
                startActivity(getIntent());

            }
        } );
    }
    ///  TÍNH GIỜ MỞ CỦA
    private void CompareTime(String Open, String Close) throws ParseException {
        Calendar now = Calendar.getInstance();
        now.setTimeZone ( TimeZone.getTimeZone ( "GMT+07" ) );
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        int NowHour = now.get(Calendar.HOUR_OF_DAY);
        int NowMinute = now.get(Calendar.MINUTE);
        Date Now = dateFormat.parse ( NowHour+":"+NowMinute );
        Date OpenTime = dateFormat.parse ( Open );
        Date CloseTime = dateFormat.parse ( Close );
//
//        int OpenTime = Integer.parseInt ( Open.replaceAll ( "\\D+","" ));
//        int CloseTime = Integer.parseInt ( Close.replaceAll ( "\\D+","" ) );
//        String Now = Integer.toString ( NowHour ) +""+ Integer.toString ( NowMinute );
//        int RightNow = Integer.parseInt ( Now );
        if ( Now.after ( OpenTime ) && Now.before ( CloseTime )) {
            txtisOpen.setText ( "ĐANG MỞ CỬA" );
            txtisOpen.setTextColor ( Color.BLACK);
        }
        else {
            txtisOpen.setText ( "ĐÃ ĐÓNG CỬA"  );
        }
    }

    /// TÍNH KHOẢNG CÁCH
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById ( R.id.KhoangCach );
            Double distance = DatabaseAccess.getInstance ( DetailActivity.this ).DistanceCalculation ( DetailActivity.this,Quanan.getAddress (),location );

            txtLat.setText (String.format ( "%.2f",distance)+" km "+"từ vị trí hiện tại");
    }



    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(DetailActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(DetailActivity.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            ////////////////////////////////
//                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
//                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            Location locationA = null;
                                Geocoder geocoder = new Geocoder(DetailActivity.this,Locale.getDefault ());
                                try {
                                    List addressList = geocoder.getFromLocationName(Quanan.getAddress(), 1);

                                    Address destination = (Address) addressList.get(0);
                                    locationA = new Location("point A");
                                    locationA.setLatitude(destination.getLatitude());
                                    locationA.setLongitude(destination.getLongitude());
                                }
                                catch (IOException e) {
                                    e.printStackTrace ();
                                }
                                double latitude = locationA.getLatitude();
                                double longitude =  locationA.getLongitude();
                                LatLng position = new LatLng(latitude, longitude);
                                map.addMarker(new MarkerOptions().position(position)
                                        .title(Quanan.getName()));
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,17));
                        }
                    }
                }, Looper.getMainLooper());
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","disable");
    }
    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","status");
    }

}
