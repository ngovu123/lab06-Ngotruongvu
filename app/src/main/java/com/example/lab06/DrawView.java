package com.example.lab06;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrawView extends AppCompatActivity {

    Handler handlerMain;
    AtomicBoolean atomic = null;
    LinearLayout layoutdevebutton;
    Button btnOk;
    EditText edtOk;
    int[] backgroundColors = {Color.BLUE, Color.BLACK, Color.RED};
    int backgroundColorIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view);

        layoutdevebutton = findViewById(R.id.layout_draw_button);
        final Random rd = new Random();
        btnOk = findViewById(R.id.btnDrawButton);
        edtOk = findViewById(R.id.editNumber);

        handlerMain = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int v = rd.nextInt(101);
                createView(v, msg.arg1);
            }
        };

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doStart();
            }
        });
    }

    private void doStart() {
        layoutdevebutton.removeAllViews();
        atomic = new AtomicBoolean(true);
        final int sobutton;
        try {
            sobutton = Integer.parseInt(edtOk.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng điền số vào", Toast.LENGTH_SHORT).show();
            return;
        }

        Thread thCon = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < sobutton && atomic.get(); i++) {
                    SystemClock.sleep(200);
                    Message msg = handlerMain.obtainMessage();
                    msg.arg1 = i;
                    handlerMain.sendMessage(msg);
                }
            }
        });
        atomic.set(true);
        thCon.start();
    }

    private void createView(int randomNumber, int index) {
        View view;
        if (index % 2 == 0) {
            view = new Button(DrawView.this);
            ((Button) view).setText(String.valueOf(randomNumber));
            ((Button) view).setTextColor(Color.WHITE);
            view.setTag("button");
        } else {
            view = new EditText(DrawView.this);
            ((EditText) view).setText(String.valueOf(randomNumber));
            ((EditText) view).setInputType(InputType.TYPE_CLASS_NUMBER);
            ((EditText) view).setTextColor(Color.WHITE);
            view.setTag("edittext");
        }

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        view.setLayoutParams(params);

        view.setBackgroundColor(getNextBackgroundColor());

        layoutdevebutton.addView(view);
    }

    private int getNextBackgroundColor() {
        int color = backgroundColors[backgroundColorIndex];
        backgroundColorIndex = (backgroundColorIndex + 1) % backgroundColors.length;
        return color;
    }
}
