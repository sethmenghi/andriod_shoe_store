package menghi.seth.swm36.project3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

public class ShoeListAdapter extends BaseAdapter{
    private Activity act;
    private LayoutInflater inflater;
    private List<Shoe> shoes;

    public ShoeListAdapter(Activity act, List<Shoe> shoes) {
        if (shoes.size() == 0){
            shoes.add(new Shoe("", "", "", 0));
        }
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

    public void updateShoeList(List<Shoe> newList){
        shoes.clear();
        shoes.addAll(newList);
        if (newList.size() == 0){
            shoes.add(new Shoe("","","",0));
        }
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int index, View rowView, ViewGroup parent){
        if (inflater == null) {
            inflater = (LayoutInflater) act.getLayoutInflater();
        }
        if (rowView == null){
            rowView = inflater.inflate(R.layout.row, null);
        }
        TextView name = (TextView) rowView.findViewById(R.id.shoeName);
        TextView color = (TextView) rowView.findViewById(R.id.shoeColor);
        TextView price = (TextView) rowView.findViewById(R.id.shoePrice);
        ImageView img = (ImageView) rowView.findViewById(R.id.shoeImage);

        Shoe currentShoe = shoes.get(index);
        if (currentShoe.image != "") {
            int imageResource = act.getResources().getIdentifier(currentShoe.image,
                    "drawable", act.getPackageName());
            img.setImageResource(imageResource);
            rowView.setOnDragListener(new ShoeDragListener());
            price.setText("$" + String.valueOf(currentShoe.price));

        }else{
            img.setImageResource(Color.TRANSPARENT);
            price.setText("");
            rowView.setBackgroundColor(Color.TRANSPARENT);
            rowView.setOnDragListener(new ShoeDragListener());
        }

        name.setText(currentShoe.name);
        color.setText(currentShoe.color);

        return rowView;
    }

    public class ShoeDragListener implements View.OnDragListener {
        Drawable bg = act.getResources().getDrawable(R.drawable.row_bg);
        //Drawable normalShape = act.getResources().getDrawable(R.drawable.row_bg);

        public ShoeListAdapter outer(){
            return ShoeListAdapter.this;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackground(enterShape);
                    v.setBackground(bg);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup parent = (ViewGroup) view.getParent();
                    parent.removeViewInLayout(view);
                    RelativeLayout container = (RelativeLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackground(normalShape);
                    return true;
                default:
                    break;
            }
            return true;
        }
    }
}
