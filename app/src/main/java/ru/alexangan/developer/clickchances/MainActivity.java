package ru.alexangan.developer.clickchances;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ButtonsLineFragment.onFragEventListener {

    static private FragmentManager mFragmentManager;
    static private android.support.v4.app.FragmentManager fManager;
    static private FragmentTransaction mFragmentTransaction;
    static int turnsLeft;
    public final static int turnsTotal = 10;
    static int btnBadLuckId = 0;
    static int score = 0;
    static TextView tvGameInfo, tvScore;
    static Button btnStart;
    final static String LOG_TAG = "df";
    static ArrayList <ButtonsLineFragment> fragList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            mFragmentManager = getFragmentManager();
        }

        tvGameInfo = (TextView) findViewById(R.id.tvGameInfo);
        tvScore = (TextView) findViewById(R.id.tvScore);
        btnStart = (Button)findViewById(R.id.btnStart);

        turnsLeft = turnsTotal;
        score = 0;
        fragList = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setFragmentBtnQuant(ButtonsLineFragment frag, int turnsLeft)
    {
        if (frag == null)
                        return;

        Bundle fragArgs = new Bundle();
        fragArgs.putInt("btnCount", turnsLeft);
        frag.setArguments(fragArgs);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.btnStart)
        {
            if (turnsLeft == turnsTotal)
            {
                btnStart.setText(R.string.txt_Exit);
                createTurn();
            }
            else
            {
                finish();
            }
        }
    }

    public void createTurn()
    {
        ButtonsLineFragment frag = new ButtonsLineFragment();
        fragList.add(frag);
        tvGameInfo.setText(getString(R.string.txt_Chance)+ (turnsLeft-1) + getString(R.string.txt_out_of) + turnsLeft);
        setFragmentBtnQuant(frag, turnsLeft--);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.llfragments, frag);
        mFragmentTransaction.addToBackStack("frag");
        mFragmentTransaction.commit();

        Random r = new Random();
        btnBadLuckId = r.nextInt(turnsLeft+1) + 1;

        Log.d(LOG_TAG, "btnBadLuckId: " + btnBadLuckId);
    }

    @Override
    public void fragEvent(int id) {

        if (id == 0)
                    return;


        if (id != btnBadLuckId)
        {
            score += turnsTotal - turnsLeft;
            tvScore.setText(getString(R.string.txt_Score) + score);
        }

        if (id == btnBadLuckId || turnsLeft == 1)
        {
            btnStart.setText(R.string.txt_Start);
            finishGame();
            return;
        }

        createTurn();
    }

    public void finishGame() {

        for (int i = fragList.size()-1; i > -1 ; i--)
        {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.remove(fragList.get(i));
            mFragmentTransaction.commit();
        }

        showResultsDialog(getString(R.string.txt_Your_score_is) + score + getString(R.string.txt_Try_one_more_time));

        turnsLeft = turnsTotal;
        score = 0;
        tvGameInfo.setText("");
        tvScore.setText("");
    }

    public void showResultsDialog(String alertText)
    {
        fManager = getSupportFragmentManager();
        Bundle messageArgs = new Bundle();
        String dialog_title = getResources().getString(R.string.results_dialog_title);

        messageArgs.putString(AlertDialogFragment.titleText, dialog_title);
        messageArgs.putString(AlertDialogFragment.messageText, alertText);

        messageArgs.putBoolean(AlertDialogFragment.Enable_Yes_Btn, true);
        messageArgs.putBoolean(AlertDialogFragment.Enable_No_Btn, true);

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.setArguments(messageArgs);
        dialog.show(fManager, "");
    }

    public void onDialogYesClick()
    {
        createTurn();
        btnStart.setText(R.string.txt_Exit);
    }

    public void onDialogNoClick() {
        finish();
    }
}