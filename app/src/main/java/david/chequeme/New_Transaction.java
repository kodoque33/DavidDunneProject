package david.chequeme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class New_Transaction extends AppCompatActivity {

    // DATABASE CONTROLS
    private DBHelper mydb;

    // Local Variables for Designer Control
    EditText transactionNameView;
    EditText typeView;
    EditText dateView;
    EditText priceView;
    EditText plusOrMinusView;

    // Declare Date Settings
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());

    // Method that is used to initialize a new transaction
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__transaction);

        // CREATE AND USE DATABASE
        mydb = new DBHelper(this);

        // Add Current Date
        dateView = (EditText) findViewById(R.id.dateView);
        dateView.setText(formattedDate);
        dateView.setEnabled(false);
    }


    public void backButton (View view) {
        Intent intent = new Intent(New_Transaction.this, Main_Menu.class);
        startActivity(intent);
    }

    public void submitData(View view)                                                         //this method is being called by the Sumbit button View
    {
        transactionNameView = (EditText) findViewById(R.id.transactionNameView);                        //finding the views
        typeView = (EditText) findViewById(R.id.typeView);
        dateView = (EditText) findViewById(R.id.dateView);
        priceView = (EditText) findViewById(R.id.priceView);
        plusOrMinusView = (EditText) findViewById(R.id.plusOrMinusView);

        String transactionName = new String();                                                         //creating a string from the text in the views
        transactionName = transactionNameView.getText().toString();

        String type = new String();
        type = typeView.getText().toString();

        String date = new String();
        date = dateView.getText().toString();

        String price = new String();
        price =  priceView.getText().toString();
        BigDecimal money;

        // Checks if Price is ONLY numerics and not null
        // Converts String to BigDecimal making sure the value is correct
        if (price.matches("[0-9-\\-]+") && price.length() > 2 && price != null)
        {
            price = price.trim();
            money = price.length() == 0 ? BigDecimal.ZERO : new BigDecimal(price);
        }
        else
        {
            money = BigDecimal.ZERO;
        }

        String dbcr = new String();
        dbcr = plusOrMinusView.getText().toString();

        // If no data entered
        if(transactionName.isEmpty() || type.isEmpty() || price.isEmpty())
        {
            // Return to Main_Menu
            backButton(view);
        }
        else
        {
            try{
                // Else - Try to add data to database
                mydb.insertTransaction(transactionName, type, date, money, dbcr);
                Intent intent = new Intent(New_Transaction.this, New_Transaction.class);    //this reloads the New_Transactions View (page)
                startActivity(intent);
            }
            catch(Exception e) { }
        }





    }


}
