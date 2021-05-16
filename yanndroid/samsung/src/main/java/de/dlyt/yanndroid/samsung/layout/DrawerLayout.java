package de.dlyt.yanndroid.samsung.layout;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import de.dlyt.yanndroid.samsung.R;

public class DrawerLayout extends LinearLayout {


    private Drawable mDrawerIcon;
    private String mToolbarTitle;
    private String mToolbarSubtitle;


    private ImageView drawerIcon;
    private ToolbarLayout toolbarLayout;
    private TextView drawerIcon_badge;


    private LinearLayout main_container;
    private LinearLayout drawer_container;


    public DrawerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray attr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DrawerLayout, 0, 0);

        try {
            mToolbarTitle = attr.getString(R.styleable.DrawerLayout_toolbar_title);
            mToolbarSubtitle = attr.getString(R.styleable.DrawerLayout_toolbar_subtitle);
            mDrawerIcon = attr.getDrawable(R.styleable.DrawerLayout_drawer_icon);
        } finally {
            attr.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.samsung_drawerlayout, this, true);

        main_container = findViewById(R.id.main_container);
        drawer_container = findViewById(R.id.drawer_container);
        toolbarLayout = findViewById(R.id.drawer_toolbarlayout);
        drawerIcon_badge = findViewById(R.id.drawerIcon_badge);


        toolbarLayout.setTitle(mToolbarTitle);
        toolbarLayout.setSubtitle(mToolbarSubtitle);
        drawerIcon = findViewById(R.id.drawerIcon);
        drawerIcon.setImageDrawable(mDrawerIcon);




        /*drawer logic*/
        View content = findViewById(R.id.main_content);
        androidx.drawerlayout.widget.DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        View drawer = findViewById(R.id.drawer);

        ViewGroup.LayoutParams layoutParams = drawer.getLayoutParams();
        layoutParams.width = (int) ((double) this.getResources().getDisplayMetrics().widthPixels / 1.19);
        drawerLayout.setScrimColor(ContextCompat.getColor(getContext(), R.color.drawer_dim_color));
        drawerLayout.setDrawerElevation(0);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.opend, R.string.closed) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        ImageView navigationIcon = findViewById(R.id.navigationIcon);
        navigationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawer, true);
            }
        });


        /*View drawer_settings = findViewById(R.id.drawer_icon);
        drawer_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent().setClass(getApplicationContext(), SettingsActivity.class));
            }
        });*/

    }


    public void setToolbarTitle(String title) {
        toolbarLayout.setTitle(title);
    }

    public void setToolbarSubtitle(String subtitle) {
        toolbarLayout.setSubtitle(subtitle);
    }

    public void setToolbarExpanded(boolean expanded, boolean animate) {
        toolbarLayout.setExpanded(expanded, animate);
    }

    public void setToolbarExpandable(boolean expandable) {
        toolbarLayout.setExpandable(expandable);
    }



    public void showIconNotification(boolean navigationIcon, boolean drawerIcon){
        toolbarLayout.showNavIconNotification(navigationIcon);
        drawerIcon_badge.setVisibility(drawerIcon ? VISIBLE : GONE);
    }




    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (main_container == null) {
            super.addView(child, index, params);
        } else {
            //drawer_container.addView(child, index, params);
            main_container.addView(child, index, params);
        }
    }


}
