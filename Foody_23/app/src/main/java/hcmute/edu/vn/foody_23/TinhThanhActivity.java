package hcmute.edu.vn.foody_23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TinhThanhActivity extends AppCompatActivity {

    ListView listView;
    TextView txtHuyTinhThanh, txtXongTinhThanh;
    List<TinhThanh> tinhThanhList = new ArrayList<>();
    AutoCompleteTextView edtTimKiemTinhThanh;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences preferences;
    String flag = "";
    int c = 63;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_tinh_thanh);

        listView = findViewById(R.id.listTinhThanh);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        txtHuyTinhThanh = findViewById(R.id.txtHuyTinhThanh);
        txtXongTinhThanh = findViewById(R.id.txtXongTinhThanh);
        txtXongTinhThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TinhThanhActivity.this,MainActivity.class);
                intent.putExtra("Province",flag);
                startActivity(intent);
            }
        });
        txtHuyTinhThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TinhThanhActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });


        edtTimKiemTinhThanh = findViewById(R.id.search_Tinh);



        // Use Adapter to get data
        tinhThanhList = DatabaseAccess.getInstance(TinhThanhActivity.this).getTinhThanh();
        final TinhThanhAdapter tinhThanhAdapter = new TinhThanhAdapter(TinhThanhActivity.this,tinhThanhList);
        listView.setAdapter(tinhThanhAdapter);

        edtTimKiemTinhThanh.setAdapter(tinhThanhAdapter);

        LuuTenTinh();

        AdapterView.OnItemClickListener list  =new AdapterView.OnItemClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Save data
                preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("Position",position);
                editor.commit();

                // Change color of Item on List view when Click
                listView.setItemChecked(position, true);

                Object o = listView.getItemAtPosition(position);
                flag = o.toString();
               // TextView textView = (TextView) view.findViewById(R.id.idItemTinhThanh);
               // textView.setTextColor(getResources().getColor(R.color.pressed_color, null));
                tinhThanhAdapter.notifyDataSetChanged();

            }
        };
        listView.setOnItemClickListener(list);


    }
    public void LuuTenTinh() {
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        c = preferences.getInt("Position", 0);
        listView.setItemChecked(c, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }
}
