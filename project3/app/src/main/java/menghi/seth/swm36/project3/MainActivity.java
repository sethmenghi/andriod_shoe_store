//
// Project 3
// Name: Seth Menghi
// E-mail: swm36@georgetown.edu
// Instructor: Singh
// COSC 150
//
// In accordance with the class policies and Georgetown's Honor Code,
// I certify that, with the exceptions of the lecture notes and those
// items noted below, I have neither given nor received any assistance
// on this project.
//
// Description: An Shoe Store android app that can
//              search for and add shoes to a cart

package menghi.seth.swm36.project3;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends ActionBarActivity {

    EditText searchInput;
    Button searchButton;
    Button browseAllButton;
    String jsonShoeString;
    Context context;
    List<Shoe> shoeList = new ArrayList<Shoe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Link the layout View to MainActivity
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        loadJsonString();

        // Link the browseAllButton to XML
        browseAllButton = (Button) findViewById(R.id.viewAllButton);
        // Set a Listener for the browseAllButton
        browseAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent so that SearchActivity can display all
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                // Use a null for searched because it will then display all
                intent.putExtra("searched", "");
                intent.putExtra("shoes", jsonShoeString);
                // Start the searchActivity
                startActivity(intent);
            }
        });

        // Link the SearchButton to XML
        searchButton = (Button) findViewById(R.id.searchButton);
        // Set a Listener for the searchButton
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // Get the inputSearchString
                searchInput = (EditText) findViewById(R.id.searchInput);

                // Create intent so that SearchActivity can use the EditText
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searched", searchInput.getText().toString());
                intent.putExtra("shoes", jsonShoeString);
                // Start the searchActivity
                startActivity(intent);
            }
        });
    }

    public void loadJsonString(){
        try {
            // Get the Assets folder
            AssetManager am = context.getAssets();
            // Open with InputStream
            InputStream is = am.open("shoes.json");
            // Use apache.commons.IOUtils to get as a string
            jsonShoeString = IOUtils.toString(is);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

