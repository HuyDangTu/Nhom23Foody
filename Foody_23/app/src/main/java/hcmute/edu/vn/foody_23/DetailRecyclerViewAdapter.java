package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private List<String> mData;
    assetManager assetMag = new assetManager ();

    public DetailRecyclerViewAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate( R.layout.imagelayout_xml,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        String fileName = mData.get(position);
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


        ImageView book_thumb;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            book_thumb = (ImageView) itemView.findViewById( R.id.imageview);

        }
    }
}

