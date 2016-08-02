package david.chequeme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Statistic_List extends AppCompatActivity {

    private DBHelper mydb;
    ListView items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic__list);

        mydb = new DBHelper(this);

        items = (ListView) findViewById(R.id.listView);

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                mydb.getAllTransactions());

        items.setAdapter(arrayAdapter);
    }

    public void backButton (View view) {
        Intent intent = new Intent(Statistic_List.this, View_Data.class);
        startActivity(intent);
    }


}
