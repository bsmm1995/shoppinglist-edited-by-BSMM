package edu.unl.task.android.shoppinglist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.unl.task.android.shoppinglist.R;
import edu.unl.task.android.shoppinglist.database.model.ShoppingItem;

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> shoppingItems) {
        super(context, R.layout.list_shopping_item);
        this.context = context;
        this.shoppingItems = shoppingItems;
    }

    @Override
    public int getCount() {
        return shoppingItems.size();
    }

    @Override
    public ShoppingItem getItem(int position) {
        return shoppingItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return shoppingItems.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        final ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_shopping_item, parent, false);
            viewHolder.mItemName = view.findViewById(R.id.textViewItemName);
            viewHolder.mItemName.setOnClickListener(this);
            viewHolder.mItemName.setOnLongClickListener(this);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        // Set text with the item name
        viewHolder.mItemName.setText(shoppingItems.get(position).getTitle());

        return view;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this.context, "onclick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this.context, "long", Toast.LENGTH_SHORT).show();
        return false;
    }

    static class ViewHolder {
        TextView mItemName;
    }


}
