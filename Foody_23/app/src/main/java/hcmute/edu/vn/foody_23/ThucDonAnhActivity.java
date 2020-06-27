package hcmute.edu.vn.foody_23;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ThucDonAnhActivity extends AppCompatActivity {
    TextView txtThucDon,tenquanan;
    ImageView imageback;
    String name,id;
    List<String> imageList;
    public String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xem_thuc_don_anh);

        Intent intent = getIntent ();
        name = intent.getExtras ().getString ( "StoreName" );
        key = intent.getExtras ().getString ( "CurrentStore" );
        id = intent.getExtras().getString("IdStore");

        tenquanan =findViewById(R.id.ten_quan_an);
        txtThucDon =findViewById(R.id.txtThucDon);
        imageback = findViewById(R.id.img_back);

        tenquanan.setText(name);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_menu_image);
        imageList = DatabaseAccess.getInstance(ThucDonAnhActivity.this).GetImage(id,"menu");
        ThucDonAnhRecyclerViewAdapter recycleViewAdapter = new ThucDonAnhRecyclerViewAdapter  (this,imageList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(recycleViewAdapter);

        txtThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
