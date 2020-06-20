package hcmute.edu.vn.foody_23;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    int CuaHangid;
    TextView txtImgThucDon;
    List<String> listdataHeader;
    List<ThucDonGroup>listgroup;
    HashMap<String, List<ThucDon>> listdataChild;
    ThucDonExpandableAdapter thucDonExpandableAdapter;
    ImageView imageback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xem_thuc_don);

        CuaHangid = getIntent().getIntExtra("ID",1);
        imageback = findViewById(R.id.img_back);
        txtImgThucDon = findViewById(R.id.txtAnh);

        AddFood();
        thucDonExpandableAdapter = new ThucDonExpandableAdapter(ThucDonActivity.this,listdataHeader,listdataChild);
        expandableThucAn.setAdapter(thucDonExpandableAdapter);
        Expand();

        txtImgThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThucDonActivity.this,ThucDonAnhActivity.class);
                intent.putExtra("ID",CuaHangid);
                startActivity(intent);
            }
        });

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThucDonActivity.this, DetailActivity.class);
                intent.putExtra("ID",CuaHangid);
                startActivity(intent);
            }
        });


    }

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

    private void AddFood() {
        expandableThucAn = (ExpandableListView) findViewById(R.id.expandlelistview_thuc_don);
        listdataHeader = new ArrayList<String>();
        listdataChild = new HashMap<String, List<ThucDon>>();

        listdataHeader.add("Bò Mỹ nhúng ớt");
        listdataHeader.add("Bún đậu");
        listdataHeader.add("Mắm");
        listdataHeader.add("Món Thêm");

        List<ThucDon> BoMynhungot = new ArrayList<ThucDon>();
        BoMynhungot.add(new ThucDon("Bò mỹ nhúng ớt nhỏ","119,000"));
        BoMynhungot.add(new ThucDon("Bò mỹ nhúng ớt vừa","219,000"));
        BoMynhungot.add(new ThucDon("Bò mỹ nhúng ớt lớn","289,000"));

        List<ThucDon> BunDau = new ArrayList<ThucDon>();
        BunDau.add(new ThucDon("Bún đậu nhỏ","69,000"));
        BunDau.add(new ThucDon("Bún đậu lớn","89,000"));
        BunDau.add(new ThucDon("Bún đậu vừa","109,000"));

        List<ThucDon> Mam = new ArrayList<ThucDon>();
        Mam.add(new ThucDon("Mắm tôm tỏi","35,000"));
        Mam.add(new ThucDon("Mắm tép","28,000"));
        Mam.add(new ThucDon("Mắm nêm","20,000"));
        List<ThucDon> MonThem = new ArrayList<ThucDon>();
        MonThem.add(new ThucDon("Cocacola","10,000"));
        MonThem.add(new ThucDon("Pepsi","10,000"));
        MonThem.add(new ThucDon("Sting","12,000"));
        MonThem.add(new ThucDon("Khăn mặt","3,000"));

        listdataChild.put(listdataHeader.get(0),BoMynhungot);
        listdataChild.put(listdataHeader.get(1),BunDau);
        listdataChild.put(listdataHeader.get(2),Mam);
        listdataChild.put(listdataHeader.get(3),MonThem);
    }
}
