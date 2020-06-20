package hcmute.edu.vn.foody_23;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    List<MonAn> monAnList;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.detail_view );

        monAnList = new ArrayList<> ();
//        monAnList.add(new MonAn("Highlands Coffee","Category Book","Mã khuyến mại, có người mới đặt giao tận nơi",R.drawable.highland));
//        monAnList.add(new MonAn("The Alley","Category Book","Mã khuyến mại, có người mới đặt giao tận nơi",R.drawable.thealley));
//        monAnList.add(new MonAn("Sherlock Holmes 7","Category Book","Description",R.drawable.chaovit));
//        monAnList.add(new MonAn("Bún Bò Huế","Category Book","Mã khuyến mại, có người mới đặt giao tận nơi",R.drawable.bunbohue));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setNestedScrollingEnabled ( false );
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this,monAnList);
        recyclerView.setLayoutManager(new GridLayoutManager (this,2));
        recyclerView.setAdapter(recycleViewAdapter);
        textView = (TextView) findViewById ( R.id.menutab );
        textView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( DetailActivity.this,ThucDonActivity.class );
                startActivity ( intent );
            }
        } );
    }
}
