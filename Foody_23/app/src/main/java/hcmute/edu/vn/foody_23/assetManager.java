package hcmute.edu.vn.foody_23;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;

public class assetManager extends AppCompatActivity {

    private AssetManager manager;

    public assetManager()
    {
    }

    public AssetManager getManager(){return manager;}

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public Bitmap getImage(String fileName, Context c) throws IOException {
        manager =  c.getAssets();
        InputStream is = manager.open(fileName);
        Bitmap  bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
