package com.example.lab06;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class Danhsachsanpham extends AppCompatActivity {

    private productHelper pdtHelper;
    private ListView listView;
    private ArrayAdapter<product> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachsanpham);

        pdtHelper = new productHelper(this);

        pdtHelper.addproduct(new product("SP-123", "iPhone 5S", 10000));
        pdtHelper.addproduct(new product("SP-124", "Vertu Constellation", 10000));
        pdtHelper.addproduct(new product("SP-125", "Nokia Lumia 925", 10000));
        pdtHelper.addproduct(new product("SP-126", "SamSung Galaxy S4", 10000));
        pdtHelper.addproduct(new product("SP-127", "HTC Desire 600", 10000));
        pdtHelper.addproduct(new product("SP-128", "HKPhone Revo LEAD", 10000));

        listView = findViewById(R.id.listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                product pd = arrayAdapter.getItem(position);
                detailproduct(pd);
                return true;
            }
        });

        loadproducts();
    }

    public void loadproducts() {
        List<product> products = pdtHelper.getAllproduct();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        listView.setAdapter(arrayAdapter);
    }
    public void detailproduct(product pd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.activity_detailsp);
        builder.setNeutralButton("Xóa sản phẩm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pdtHelper.deleteproduct(pd);
                Toast.makeText(Danhsachsanpham.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                loadproducts();
            }
        });

        builder.setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText editTextID = alertDialog.findViewById(R.id.editTextId);
        EditText editTextName = alertDialog.findViewById(R.id.editTextName);
        EditText editTextPrice = alertDialog.findViewById(R.id.editTextPrice);

        editTextID.setText(pd.getId());
        editTextName.setText(pd.getName());
        editTextPrice.setText(String.valueOf(pd.getPrice()));
    }
}
