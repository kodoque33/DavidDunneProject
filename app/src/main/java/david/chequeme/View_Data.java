package david.chequeme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class View_Data extends AppCompatActivity {

    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__data);

        mydb = new DBHelper(this);
    }

    public void backButton (View view) {
        Intent intent = new Intent(View_Data.this, Main_Menu.class);
        startActivity(intent);
    }

    public void viewTable (View view) {
        Intent intent = new Intent(View_Data.this, Statistic_List.class);
        startActivity(intent);
    }

    public void viewCharts (View view) {
        Intent intent = new Intent(View_Data.this, Charts_Screen.class);
        startActivity(intent);
    }

    public void alertReset(final View view)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(View_Data.this);
        builder1.setMessage("Are you sure you wish to wipe transaction database?");
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mydb.dropTransactions();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }




}
