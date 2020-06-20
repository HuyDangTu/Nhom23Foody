package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {


    private Context mContext;
    private List<MonAn> mData;
    assetManager assetMag = new assetManager();

    public RecycleViewAdapter(Context mContext, List<MonAn> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.book_title.setText(mData.get(position).getTitle());
        holder.food_des.setText(mData.get(position).getDescription());
        String fileName = mData.get(position).getThumbnail();
        Bitmap image = null;
        try {
            image = assetMag.getImage(fileName,mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.book_thumb.setImageBitmap(image);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView book_title, food_des;
        ImageView book_thumb;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title = (TextView) itemView.findViewById(R.id.title_food_id);
            book_thumb = (ImageView) itemView.findViewById(R.id.thumb_food_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            food_des = (TextView) itemView.findViewById(R.id.des_food_id);
        }
    }
}

