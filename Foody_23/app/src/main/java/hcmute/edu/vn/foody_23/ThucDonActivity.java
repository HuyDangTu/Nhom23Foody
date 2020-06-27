package hcmute.edu.vn.foody_23;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThucDonActivity extends AppCompatActivity {

    ExpandableListView expandableThucAn;
    TextView txtImgThucDon,tenquanan;
    List<String> listdataHeader;
    HashMap<String, List<ThucDon>> listdataChild;
    ThucDonExpandableAdapter thucDonExpandableAdapter;
    ImageView imageback;
    String key,name,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xem_thuc_don);


        Intent intent = getIntent ();
        key = intent.getExtras ().getString ( "CurrentStore" );
        name = intent.getExtras ().getString ( "StoreName" );
        id =intent.getExtras().getString("IdStore");


        imageback = findViewById(R.id.img_back);
        txtImgThucDon = findViewById(R.id.txtAnh);
        tenquanan = findViewById(R.id.ten_quan_an);

        tenquanan.setText(name);

        AddFood();
        thucDonExpandableAdapter = new ThucDonExpandableAdapter(ThucDonActivity.this,listdataHeader,listdataChild);
        expandableThucAn.setAdapter(thucDonExpandableAdapter);
        Expand();

        txtImgThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThucDonActivity.this,ThucDonAnhActivity.class);
                intent.putExtra("StoreName",name);
                intent.putExtra("CurrentStore",key);
                intent.putExtra("IdStore",id);
                startActivity(intent);
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

    //Expandable List ThucDon
    private void Expand() {
        expandableThucAn.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                thucDonExpandableAdapter = new ThucDonExpandableAdapter(ThucDonActivity.this,listdataHeader,listdataChild){
                    @Override
                    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                        View view = super.getGroupView(groupPosition,isExpanded, convertView, parent);
                        TextView tv = view.findViewById(R.id.textviewHeader);
                        ImageView img=view.findViewById(R.id.group_food_image);
                        img.setImageResource(R.drawable.drop);
                        return view;
                    }
                };
            }
        });
        expandableThucAn.setAdapter(thucDonExpandableAdapter);
    }

    //Lấy dữ liệu thực đơn nhằm hiển thị lên
    private void AddFood() {
        expandableThucAn = (ExpandableListView) findViewById(R.id.expandlelistview_thuc_don);
        listdataHeader = new ArrayList<String>();
        listdataChild = new HashMap<String, List<ThucDon>>();


        //Lấy list<ThucDonGroup>
        List<ThucDonGroup> thucDonGroups = DatabaseAccess.getInstance(ThucDonActivity.this).getThucDonGroup(key);
        List<ThucDon> thucDons = new ArrayList<>();
        int group_id;
        for(int i=0;i<thucDonGroups.size();i++) {
            listdataHeader.add(thucDonGroups.get(i).getName());
            group_id = thucDonGroups.get(i).getId();
            thucDons = DatabaseAccess.getInstance(ThucDonActivity.this).getThucDon(group_id);
            listdataChild.put(thucDonGroups.get(i).getName(), thucDons);
        }
    }
}
