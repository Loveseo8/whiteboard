package com.aad.drawingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private boolean isDrawInit;

    @Override
    protected void onResume() {
        super.onResume();

        if(!isDrawInit){

            initDraw();

            final Button clearButton = findViewById(R.id.clear);
            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    paintView.clear();

                }
            });

            final Button increase = findViewById(R.id.increase_brush_size);
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    paintView.brushSize++;

                }
            });

            final Button decrease = findViewById(R.id.decrease_brush_size);
            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(paintView.brushSize > 1) paintView.brushSize--;

                }
            });

            final Button eraser = findViewById(R.id.eraser);
            eraser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(paintView.brushColor == Color.BLACK) paintView.brushColor = Color.WHITE;
                    else paintView.brushColor = Color.BLACK;

                }
            });

            isDrawInit = true;

        }

    }

    private PaintView paintView;

    private void initDraw(){

        paintView = findViewById(R.id.paint_view);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        paintView.init(metrics);

    }

    static class FingerPath{

        int color;
        int strokeWidth;
        Path path;


        public FingerPath(int color, int strokeWidth, Path path) {
            this.color = color;
            this.strokeWidth = strokeWidth;
            this.path = path;
        }
    }
}