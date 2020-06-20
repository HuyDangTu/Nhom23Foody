package hcmute.edu.vn.foody_23;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ThucDonAnhActivity extends AppCompatActivity {
    List<ThucDon> thucDonList;
    List<ThucDonGroup> thucDonGroups;
    TextView txtThucDon;
    ImageView imageback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xem_thuc_don_anh);

        txtThucDon =findViewById(R.id.txtThucDon);
        imageback = findViewById(R.id.img_back);

        txtThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThucDonAnhActivity.this,ThucDonActivity.class);
//                intent.putExtra("Id",idRestaurance);
                startActivity(intent);
            }
        });

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThucDonAnhActivity.this,DetailActivity.class);
//                intent.putExtra("Id",idRestaurance);
                startActivity(intent);
            }
        });

    }
}
