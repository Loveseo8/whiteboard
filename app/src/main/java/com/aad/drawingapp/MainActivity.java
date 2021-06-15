package com.aad.drawingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import top.defaults.colorpicker.ColorPickerView;

public class MainActivity extends AppCompatActivity {

    private int currentColor = Color.BLACK;

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

            final Button changeBrushSize = findViewById(R.id.change_brush_size);
            changeBrushSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View dialogView = layoutInflater.inflate(R.layout.change_size_dialog, null);
                    dialogBuilder.setView(dialogView);


                    final Button buttonChange = dialogView.findViewById(R.id.change_button);
                    SeekBar seekBar = dialogView.findViewById(R.id.seekBar);

                    seekBar.setProgress(paintView.brushSize);

                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            paintView.brushSize = progress;

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    final AlertDialog b = dialogBuilder.create();
                    b.show();

                    buttonChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            b.dismiss();

                        }
                    });

                }
            });

            final Button changeBrushColor = findViewById(R.id.change_brush_color);
            changeBrushColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View dialogView = layoutInflater.inflate(R.layout.change_color_dialog, null);
                    dialogBuilder.setView(dialogView);


                    final Button buttonChange = dialogView.findViewById(R.id.change_button);
                    ColorPickerView colorPickerView = dialogView.findViewById(R.id.colorPickerView);


                    final AlertDialog b = dialogBuilder.create();
                    b.show();

                    buttonChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            paintView.brushColor = colorPickerView.getColor();
                            currentColor = colorPickerView.getColor();

                            b.dismiss();

                        }
                    });

                }
            });

            final Button eraser = findViewById(R.id.eraser);
            eraser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!(paintView.brushColor == Color.WHITE)){

                        paintView.brushColor = Color.WHITE;
                        eraser.setTextColor(Color.RED);
                    }
                    else{

                        paintView.brushColor = currentColor;
                        eraser.setTextColor(Color.WHITE);

                    }

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