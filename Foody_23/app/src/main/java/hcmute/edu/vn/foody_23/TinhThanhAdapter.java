package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;


public class TinhThanhAdapter extends ArrayAdapter<TinhThanh> {

    Context mData;

    public TinhThanhAdapter(@NonNull Context context, List<TinhThanh> tinhThanhs) {
        super(context, 0,tinhThanhs);
        this.mData = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = View.inflate(mData,R.layout.item_tinh_thanh,null);



        // Get the data item for this position
        TinhThanh tinhThanh = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tinh_thanh, parent,false);
        }
        // Lookup view for data population
        TextView txtTinhThanh =  convertView.findViewById(R.id.idItemTinhThanh);

      //  txtTinhThanh.setTextColor(mData.getResources().getColor(R.color.pressed_color, null));
        // Populate the data into the template view using the data object
        txtTinhThanh.setText(tinhThanh.getTenTinhThanh());
        // Return the completed view to render on screen
        return convertView;

    }
}
