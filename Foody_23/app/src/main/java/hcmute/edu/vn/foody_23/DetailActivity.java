package hcmute.edu.vn.foody_23;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Layout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class DetailActivity extends AppCompatActivity implements LocationListener {


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


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMaps);
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
            }
        } );
    }
    ///  TÍNH GIỜ MỞ CỦA
    private void CompareTime(String Open, String Close) throws ParseException {
        Calendar now = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        int NowHour = now.get(Calendar.HOUR_OF_DAY);
        int NowMinute = now.get(Calendar.MINUTE);

        int OpenTime = Integer.parseInt ( Open.replaceAll ( "\\D+","" ));
        int CloseTime = Integer.parseInt ( Close.replaceAll ( "\\D+","" ) );
        String Now = Integer.toString ( NowHour ) + Integer.toString ( NowMinute );
        int RightNow = Integer.parseInt ( Now );
        if ( RightNow > OpenTime && RightNow < CloseTime) {
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
//        txtLat.setText ( location.getLatitude ()+"-"+location.getLongitude (  ) );
        Geocoder geocoder = new Geocoder(DetailActivity.this,Locale.getDefault ());
        try {
            List addressList = geocoder.getFromLocationName(Quanan.getAddress (), 1);

            Address destination = (Address) addressList.get ( 0 ) ;
            Location locationA = new Location("point A");

            locationA.setLatitude(destination.getLatitude ());
            locationA.setLongitude(destination.getLongitude ());

            Location locationB = new Location("point B");

            locationB.setLatitude(location.getLatitude ());
            locationB.setLongitude(location.getLongitude ());

            Double distance = Double.valueOf ( locationA.distanceTo(locationB)/1000);
//            RelativeLayout detailLayout = (RelativeLayout) findViewById ( R.id.relativeLay.out6 );
//            String url = "http://maps.google.com/maps/api/staticmap?center=" + destination.getLatitude () + "," + destination.getLongitude () + "&zoom=15&size=200x200&sensor=false&key=AIzaSyAyYhjRiaVj5QCwb37NNh4LUipClZMxmD0";
//            Bitmap bitmap = getBitmapFromURL ( url );
//            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
//            detailLayout.setBackground ( ob );

            txtLat.setText (String.format ( "%.2f",distance)+" km "+"từ vị trí hiện tại");
        }
        catch (IOException e) {
            e.printStackTrace ();

        }
    }


//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            // Log exception
//            return null;
//        }
//    }

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
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
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
