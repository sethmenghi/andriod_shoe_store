package menghi.seth.swm36.project3;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import menghi.seth.swm36.project3.Shoe;
import java.util.List;


// Explanation of ListAdapters = http://commonsware.com/Android/excerpt.pdf
//
public class ShoeListAdapter extends BaseAdapter{
    private Activity act;
    private LayoutInflater inflater;
    private List<Shoe> shoes;

    public ShoeListAdapter(Activity act, List<Shoe> shoes) {
        this.act = act;
        this.shoes = shoes;
    }

    @Override
    public int getCount() {
        return shoes.size();
    }

    @Override
    public Shoe getItem(int location) {
        return shoes.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent){
        if (inflater == null) {
            inflater = (LayoutInflater) act.getLayoutInflater();
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row, null);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.shoeImage);
        TextView name = (TextView) convertView.findViewById(R.id.shoeName);
        TextView color = (TextView) convertView.findViewById(R.id.shoeColor);
        TextView price = (TextView) convertView.findViewById(R.id.shoePrice);

        Shoe currentShoe = shoes.get(index);
        int imageResource = act.getResources().getIdentifier(currentShoe.image,
                                                             "drawable", act.getPackageName());
        img.setImageResource(imageResource);
        name.setText(currentShoe.name);
        color.setText(currentShoe.color);

        String priceString = "$" + currentShoe.price;
        price.setText(String.valueOf(priceString));

        convertView.setOnDragListener(new View.OnDragListener() {
            Drawable enterShape = act.getResources().getDrawable(R.drawable.row_bg);
            Drawable normalShape = act.getResources().getDrawable(R.drawable.row_bg);
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setBackground(enterShape);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackground(normalShape);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        RelativeLayout container = (RelativeLayout) v;
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackground(normalShape);
                    default:
                        break;
                }
                return true;
            }
        });

        return convertView;
    }
}
