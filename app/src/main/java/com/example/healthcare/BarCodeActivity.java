package com.example.healthcare;


import com.example.healthcare.models.Code128;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;

public class BarCodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        drawBarcode();
    }

    private void drawBarcode() {
        String barcode = "7761010114033430";
        Code128 code = new Code128(BarCodeActivity.this);
        code.setData(barcode);
        Bitmap bitmap = code.getBitmap(680, 300);
        ImageView ivBarcode = (ImageView)findViewById(R.id.code128_barcode);
        ivBarcode.setImageBitmap(bitmap);
    }

}