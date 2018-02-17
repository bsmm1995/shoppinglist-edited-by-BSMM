package edu.unl.task.android.shoppinglist;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import edu.unl.task.android.shoppinglist.database.entities.ShoppingItemDB;
import edu.unl.task.android.shoppinglist.database.model.ShoppingItem;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout txTitleProduct;
    private boolean boNuevoProducto = true;
    private TextView tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        FloatingActionButton fbCancel = findViewById(R.id.fbCancel);
        FloatingActionButton fbSave = findViewById(R.id.fbSave);
        txTitleProduct = findViewById(R.id.txProductoTitle);
        tvId = findViewById(R.id.tvId);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boNuevoProducto = false;
            tvId.setText(bundle.getString(ShoppingItemDB.ShoppingElementEntry._ID));
            txTitleProduct.getEditText().setText(bundle.getString(ShoppingItemDB.ShoppingElementEntry.COLUMN_NAME_TITLE));
        }

        fbCancel.setOnClickListener(this);
        fbSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbSave:
                saveProductInDB();
                break;
            case R.id.fbCancel:
                this.finish();
                break;
        }
    }

    private void saveProductInDB() {
        String stTitle = txTitleProduct.getEditText().getText().toString().trim();
        if (stTitle.isEmpty()) {
            txTitleProduct.setError("Complete the field...");
        } else if (boNuevoProducto) {
            ShoppingItemDB obj = new ShoppingItemDB(getApplicationContext());
            obj.insertElement(stTitle);
            this.finish();
        } else {
            long loId = Long.parseLong(tvId.getText().toString());
            new ShoppingItemDB(this).updateItem(new ShoppingItem(loId, stTitle));
            this.finish();
        }
    }

}
