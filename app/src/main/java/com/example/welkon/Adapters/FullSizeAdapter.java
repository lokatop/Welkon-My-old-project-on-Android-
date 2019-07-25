package com.example.welkon.Adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.welkon.R;
import com.example.welkon.Utils.HackyViewPager;

public class FullSizeAdapter extends PagerAdapter {

    Context context;
    String[] images;
    LayoutInflater layoutInflater;

    public FullSizeAdapter(Context context, String[] images){
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.full_item, null);

        ImageView imageView = (ImageView)v.findViewById(R.id.img);
        //Glide.with(context).load(images[position]).apply(new RequestOptions().centerInside()).into(imageView);
        loadImageFromData(images[position],imageView);
        HackyViewPager vp = (HackyViewPager)container;
        vp.addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        HackyViewPager viewPager = (HackyViewPager)container;
        View v = (View)object;
        viewPager.removeView(v);
    }
    public void loadImageFromData(String namePhoto, ImageView imageView1) {
        String path = Environment.getExternalStorageDirectory().toString();
        String imagePath = path + "/AudioArmy/PhotoForDB/"+namePhoto+".jpg";
        imageView1.setImageURI(Uri.parse(imagePath));
    }
}
