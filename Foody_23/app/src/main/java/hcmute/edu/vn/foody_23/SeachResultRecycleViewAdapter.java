package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class SeachResultRecycleViewAdapter extends RecyclerView.Adapter<SeachResultRecycleViewAdapter.MyViewHolder>  {
    private Context mContext;
    private List<CuaHang> mData;
    assetManager assetMag = new assetManager();
    public SeachResultRecycleViewAdapter(Context mContext, List<CuaHang> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_seach,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        String fileName = mData.get(position).getImage();
        Bitmap image = null;
        try {
            image = assetMag.getImage(fileName,mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.image.setImageBitmap(image);
        holder.name.setText(mData.get(position).getName());
        holder.score.setText(mData.get(position).getScore());
        holder.address.setText(mData.get(position).getAddress());
        holder.distance.setText(mData.get(position).getDistance());
        holder.type.setText(mData.get(position).getType());
        holder.imageCount.setText(mData.get(position).getImageCount());
        holder.commentCount.setText(mData.get(position).getCommentCount());
        holder.id.setText(mData.get(position).getId());
        holder.cardView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext (),DetailActivity.class);
                mContext.startActivity (intent);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id,name,score,address,distance,type,imageCount,commentCount;
        ImageView image;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            score = (TextView) itemView.findViewById(R.id.score);
            address = (TextView) itemView.findViewById(R.id.address);
            distance = (TextView) itemView.findViewById(R.id.distance);
            type = (TextView) itemView.findViewById(R.id.TypeOfStore);
            imageCount = (TextView) itemView.findViewById(R.id.photo);
            commentCount = (TextView) itemView.findViewById(R.id.comment);
            image = (ImageView) itemView.findViewById(R.id.thumb_food_image);
            cardView = (CardView) itemView.findViewById(R.id.cardviewid);
            id = (TextView) itemView.findViewById(R.id.StoreID);
        }
    }
}
