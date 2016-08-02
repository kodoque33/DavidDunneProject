package david.chequeme;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.math.BigDecimal;

public class Charts_Screen extends AppCompatActivity {

    private DBHelper mydb;

    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts__screen);

        mydb = new DBHelper(this);
        // Getting reference to the button btn_chart
        Button btnChart = (Button) findViewById(R.id.btn_chart);

        // Defining click event listener for the button btn_chart
        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Draw the Income vs Expense Chart
                openChart();
            }
        };
        // Setting event click listener for the button btn_chart of the MainActivity layout
        btnChart.setOnClickListener(clickListener);
    }

    // Creates a Line Chart which displays the latest 10 records
    private void openChart(){
        int[] x = { 1,2,3,4,5,6,7,8,9,10};
        int[] income = { 2000,2500,2700,3000,2800,3500,3700,3800};
    //    int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

        BigDecimal[] balance = mydb.getLineChartTransactions();
        BigDecimal[] transactions;

        // Creating an  XYSeries for Income
        XYSeries incomeSeries = new XYSeries("Balance");
        // Creating an  XYSeries for Expense
        XYSeries expenseSeries = new XYSeries("Transaction");
        // Adding data to Income and Expense Series
        for(int i=0;i<x.length;i++){
            incomeSeries.add(x[i], balance[i].doubleValue()); // Casts to a double because BigDecimal ain't a double
         //   expenseSeries.add(x[i],expense[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(incomeSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.BLUE);
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(true);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.RED);
        expenseRenderer.setPointStyle(PointStyle.CIRCLE);
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Transactions over Balance");
        multiRenderer.setXTitle("Last 10 Transactions");
        multiRenderer.setYTitle("Transaction Graph");
        multiRenderer.setZoomButtonsVisible(true);
        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, mMonth[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);

        // Creating an intent to plot line chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);

        // Start Activity
        startActivity(intent);
    }

    public void backButton (View view) {
        Intent intent = new Intent(Charts_Screen.this, View_Data.class);
        startActivity(intent);
    }
}
