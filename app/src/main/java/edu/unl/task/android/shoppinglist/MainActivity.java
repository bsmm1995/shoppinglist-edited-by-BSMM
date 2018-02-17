package edu.unl.task.android.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.unl.task.android.shoppinglist.adapter.ShoppingItemAdapter;
import edu.unl.task.android.shoppinglist.database.entities.ShoppingItemDB;
import edu.unl.task.android.shoppinglist.database.model.ShoppingItem;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private ShoppingItemDB mShoppingItemDB;
    private ShoppingItemAdapter shoppingItemAdapter;
    private ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mShoppingItemDB = new ShoppingItemDB(this);
        shoppingItems.addAll(mShoppingItemDB.getAllItems());
        shoppingItemAdapter = new ShoppingItemAdapter(this, shoppingItems);
        mListView.setAdapter(shoppingItemAdapter);

        registerForContextMenu(mListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            deleteAllProductos();
            return true;
        } else if (id == R.id.action_create_item) {
            Intent intent = new Intent(this, ItemActivity.class);
            startActivity(intent);
        }
        updateList();

        return super.onOptionsItemSelected(item);
    }

    private void updateList() {
        shoppingItems.clear();
        shoppingItems.addAll(mShoppingItemDB.getAllItems());
        shoppingItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id = v.getId();

        MenuInflater inflater = getMenuInflater();
        if (id == mListView.getId()) {
            inflater.inflate(R.menu.menu_item, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.itUpdate:
                updateProducto(info.position);
                return true;
            case R.id.itDelete:
                deleteProduct(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteProduct(int position) {
        final Context context = getApplicationContext();
        final ShoppingItem obj = shoppingItems.get(position);

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Esta seguro de borrar este item ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                new ShoppingItemDB(context).deleteItem(obj);
                updateList();
            }
        });
        dialogo1.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();

    }

    private void deleteAllProductos() {
        final Context context = getApplicationContext();

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Esta seguro de ELIMINAR todos los elementos ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                new ShoppingItemDB(context).clearAllItems();
                updateList();
            }
        });
        dialogo1.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                updateList();
            }
        });
        dialogo1.show();
    }

    private void updateProducto(int position) {
        ShoppingItem obj = shoppingItems.get(position);
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(ShoppingItemDB.ShoppingElementEntry._ID, obj.getId() + "");
        intent.putExtra(ShoppingItemDB.ShoppingElementEntry.COLUMN_NAME_TITLE, obj.getTitle());
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateList();
    }
}
