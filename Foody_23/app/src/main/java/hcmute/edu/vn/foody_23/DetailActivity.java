package hcmute.edu.vn.foody_23;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

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
        TextView txtWifiName = (TextView) dialog.findViewById ( R.id.WifiName );
        TextView txtWifiPass=(TextView) dialog.findViewById ( R.id.WifiPass );

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
            txtisOpen.setText ( "ĐANG MỞ CỬA" + " " );
            txtisOpen.setTextColor ( Color.BLACK);
        }
        else {
            txtisOpen.setText ( "ĐÃ ĐÓNG CỬA" + " ");
        }

    }
    /// TÍNH KHOẢNG CÁCH
    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById ( R.id.KhoangCach );
        txtLat.setText ( location.getLatitude ()+"-"+location.getLongitude (  ) );
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
//            List addressList2 = geocoder.getFromLocation (destination.getLatitude (),destination.getLongitude (),1);
//            Address diachi = (Address) addressList2.get ( 0 );
//String Add = diachi.toString ();

            txtLat.setText (String.format ( "%.2f",distance)+" km "+"từ vị trí hiện tại");
        }
        catch (IOException e) {
            e.printStackTrace ();
        }
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
