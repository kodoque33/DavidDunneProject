package david.chequeme;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class Main_Menu extends AppCompatActivity {

    // DATABASE CONTROLS
    private DBHelper mydb;

    TextView currentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        // CREATE AND USE DATABASE
        mydb = new DBHelper(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Timer autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateBalance();
                    }
                });
            }
        }, 0, 10000); // updates each 40 secs
    }

    public void updateBalance() {

        String temp;
        temp = mydb.getBalance();
        if (temp == null || temp == "") {
            temp = "0";
        }
        currentBalance = (EditText) findViewById(R.id.currentBalance);
        currentBalance.setText(temp);
    }

    public void viewData(View view) {
        Intent intent = new Intent(Main_Menu.this, View_Data.class);
        startActivity(intent);
    }

    public void newTransaction(View view) {
        Intent intent = new Intent(Main_Menu.this, New_Transaction.class);
        startActivity(intent);
    }

}



