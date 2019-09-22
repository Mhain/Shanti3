package com.example.mhainulhoque.shanti3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class slideApdater extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    public int[] lst_image = {
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3

    };
    public String[] img_details={
        "slide1",
            "slide2",
            "slide3"

    };
public slideApdater(Context context){
    this.context=context;
}

    @Override
    public int getCount() {
        return img_details.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(RelativeLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.slide,container,false);
        RelativeLayout layoutslide=view.findViewById(R.id.Relative);
        ImageView imgslide = (ImageView) view.findViewById(R.id.slideImg);
        TextView txtTitle=(TextView)view.findViewById(R.id.loc_name);
        imgslide.setImageResource(lst_image[position]);
        txtTitle.setText(img_details[position]);
        container.addView(view);
        return view;
        }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
