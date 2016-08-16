package ru.alexangan.developer.clickchances;

/**
 * Created by Administrator on 12.08.16.
 */

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class ButtonsLineFragment extends Fragment {

    final String LOG_TAG = "df";
    int btnCount = 0;
    Context context;
    FlexboxLayout dynamicLayout;

    ArrayList<Button> btnArray;
    FlexboxLayout.LayoutParams elemLParams;

    final static int wrapContent = FlexboxLayout.LayoutParams.WRAP_CONTENT;

    public interface onFragEventListener
    {
        public void fragEvent(int id);
    }

    onFragEventListener fragEventListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity().getApplicationContext();
        elemLParams = new FlexboxLayout.LayoutParams(wrapContent,wrapContent);

        btnArray = new ArrayList<>();

        Bundle args=getArguments();

        btnCount = args.getInt("btnCount", 0);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            fragEventListener = (onFragEventListener)getActivity();
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement fragEventListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dynamicLayout = new FlexboxLayout(context);
        dynamicLayout.setBackgroundColor(Color.GREEN);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;
        //float screenHeight = displayMetrics.heightPixels;

        for (int i = 1; i <= btnCount; i++) {

            final Button btnNew = new Button(context);

            btnNew.setText(String.valueOf(i-1));

            final int id_ = i;
            btnNew.setId(id_);
            btnArray.add(btnNew);

            btnNew.setLayoutParams(elemLParams);

            FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) btnNew.getLayoutParams();

            lp.width = (int)(screenWidth / btnCount);
            //lp.height = (int)(screenHeight / (MainActivity.turnsTotal + 2));

            btnNew.setLayoutParams(lp);

            dynamicLayout.addView(btnNew, elemLParams);

            btnNew.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    final int id_ = btnNew.getId();

                    fragEventListener.fragEvent(id_);

                    for (int k = 0; k < btnArray.size() ; k++)
                    {
                        btnArray.get(k).setId(0);
                    }

                    dynamicLayout.setBackgroundColor(Color.DKGRAY);

                    Log.d(LOG_TAG, "Button click in Fragment with id: " + id_);
                }
            });
        }

        return dynamicLayout;
    }
}