package com.example.wally;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    WallpaperManager wallpaperManager;
    ImageView image;
    String url;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        // initializing all variables on below line.
        url = getIntent().getStringExtra("imgUrl");
        image = findViewById(R.id.image);
        loadingPB = findViewById(R.id.idPBLoading);

        // calling glide to load image from url on below line.
        Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                // making progress bar visibility
                // to gone on below line.
                loadingPB.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                loadingPB.setVisibility(View.GONE);
                return false;
            }
        }).into(image);

        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Button setWallpaper = findViewById(R.id.idBtnSetWallpaper);


        // listener to our set wallpaper button.
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(WallpaperActivity.this)
                        .asBitmap().load(url)
                        .listener(new RequestListener<Bitmap>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                          Toast.makeText(WallpaperActivity.this, "Fail to load image..", Toast.LENGTH_SHORT).show();
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                          // on below line we are setting wallpaper using wallpaper manager .
                                          try {
                                              wallpaperManager.setBitmap(bitmap);
                                              Toast.makeText(WallpaperActivity.this, "Wallpaper Set to Home screen.", Toast.LENGTH_SHORT).show();
                                          } catch (IOException e) {
                                              // on below line we are handling exception.
                                              Toast.makeText(WallpaperActivity.this, "Fail to set wallpaper", Toast.LENGTH_SHORT).show();
                                              e.printStackTrace();
                                          }
                                          return false;
                                      }
                                  }
                        ).submit();

                Toast.makeText(WallpaperActivity.this, "Wallpaper Set to Home Screen", Toast.LENGTH_LONG).show();
            }
        });
    }
}