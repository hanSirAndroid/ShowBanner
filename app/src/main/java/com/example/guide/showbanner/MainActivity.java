package com.example.guide.showbanner;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bannershowing.Banner;
import com.example.bannershowing.widget.adapters.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BannerPagerAdapter.IOnImageClickListener, BannerPagerAdapter.IOnImageLongClickListener {

    private Banner mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        mBanner = (Banner) findViewById(R.id.banner);

        List<ImageView> list = new ArrayList<>();

        for (int i = 0; i < 7; i++) {

            int id = getResources().getIdentifier("p" + (i + 1), "mipmap", getPackageName());

            ImageView imageView = new ImageView(this);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView.setImageResource(id);

            list.add(imageView);

        }

        String[] pathes = new String[5];
        pathes[0] = "http://img2.imgtn.bdimg.com/it/u=2242384902,1117866812&fm=21&gp=0.jpg";
        pathes[1] = "http://img5.imgtn.bdimg.com/it/u=2148378798,2755744101&fm=21&gp=0.jpg";
        pathes[2] = "http://img2.imgtn.bdimg.com/it/u=2403748927,3196049645&fm=21&gp=0.jpg";
        pathes[3] = "http://img5.imgtn.bdimg.com/it/u=2589673223,1578859920&fm=21&gp=0.jpg";
        pathes[4] = "http://img4.imgtn.bdimg.com/it/u=2929362257,2343491229&fm=21&gp=0.jpg";

        mBanner.setDefaultIndicatorColor(Color.YELLOW);
        mBanner.setSelectedIndicatorColor(Color.RED);
        mBanner.setIndicatorSize(20);
        mBanner.setPlayInterval(3000);
//        mBanner.setHolderDrawable(new BitmapDrawable(getResources(),
//                BitmapFactory.decodeResource(getResources(),R.mipmap.p1)));
        mBanner.setImages(pathes);
        mBanner.addOnViewPagerItemClickListener(this);
        mBanner.addOnViewPagerItemLongClickListener(this);

    }

    @Override
    public void onImageClick(int position) {
        Toast.makeText(this, "您点击了第\t" + (position + 1) + "\t张图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageLongClick(int position) {
        Toast.makeText(this, "长按\t" + (position+1) + "\t张图片", Toast.LENGTH_SHORT).show();
    }
}
