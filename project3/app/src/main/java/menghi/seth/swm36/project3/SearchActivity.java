package menghi.seth.swm36.project3;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity {
    private List<Shoe> shoes = new ArrayList<Shoe>();
    private List<Shoe> shoesInCart = new ArrayList<Shoe>();
    private ShoeList shoeList;

    private ListView searchList;
    private ListView cartList;

    private ShoeListAdapter searchAdapter;
    private ShoeListAdapter cartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchList = (ListView) findViewById(R.id.searchList);
        cartList = (ListView) findViewById(R.id.cartList);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String searched = intent.getStringExtra("searched");
        String jsonString = intent.getStringExtra("shoes");
        shoeList = new ShoeList(jsonString);

        if (searched == null) {
            // Display all shoes, no query made
            shoes = shoeList.getShoeList();
        }
        else {
            // Only get searched shoes
            shoes = shoeList.searchName(searched);
        }
        searchAdapter = new ShoeListAdapter(this, shoes);
        searchList.setAdapter(searchAdapter);

        cartAdapter = new ShoeListAdapter(this, shoesInCart);
        cartList.setAdapter(cartAdapter);


        searchList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String value = searchAdapter.getItem(position).name;
                ClipData.Item item = new ClipData.Item((CharSequence) value);
                String[] mimeType = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
                ClipData clip = new ClipData((CharSequence) value, mimeType, item);
                view.startDrag(clip, shadow, view, 0);
                //view.setVisibility(View.INVISIBLE);
                shoesInCart.add(shoes.get(position));
                shoes.remove(shoes.get(position));
                cartAdapter.updateShoeList(shoesInCart);
                searchAdapter.updateShoeList(shoes);
                searchList.invalidateViews();
                cartList.invalidateViews();
                return true;
            }
        });

        cartList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String value = cartAdapter.getItem(position).name;
                ClipData.Item item = new ClipData.Item((CharSequence) value);
                String[] mimeType = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
                ClipData clip = new ClipData((CharSequence) value, mimeType, item);
                view.startDrag(clip, shadow, view, 0);
                //view.setVisibility(View.INVISIBLE);
                shoes.add(shoesInCart.get(position));
                shoesInCart.remove(shoesInCart.get(position));
                cartAdapter.updateShoeList(shoesInCart);
                searchAdapter.updateShoeList(shoes);
                searchList.invalidateViews();
                cartList.invalidateViews();
                return true;
            }
        });

    }
}
