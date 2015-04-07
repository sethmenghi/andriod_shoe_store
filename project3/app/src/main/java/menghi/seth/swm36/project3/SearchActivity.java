package menghi.seth.swm36.project3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.Intent;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends ListActivity {
    EditText inputSearch;
    private List<Shoe> shoes = new ArrayList<Shoe>();
    private ListView listView;
    private ShoeListAdapter adapter;
    private int _xDelta;
    private int _yDelta;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(android.R.id.list);
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String searched = intent.getStringExtra("searched");
        String jsonString = intent.getStringExtra("shoes");
        ShoeList shoeList = new ShoeList(jsonString);

        if (searched == null) {
            // Display all shoes, no query made
            shoes = shoeList.getShoeList();
        }
        else {
            // Only get searched shoes
            shoes = shoeList.searchName(searched);
        }
        adapter = new ShoeListAdapter(this, shoes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String value = adapter.getItem(position).name;
                ClipData.Item item = new ClipData.Item((CharSequence) value);
                String[] mimeType =  {ClipDescription.MIMETYPE_TEXT_PLAIN};
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
                ClipData clip = new ClipData((CharSequence)value, mimeType, item);
                view.startDrag(clip, shadow, view, 0);
                view.setVisibility(View.INVISIBLE);
            }
        });
    }
}
