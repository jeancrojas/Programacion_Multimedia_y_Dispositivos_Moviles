package com.example.david.colorpicker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    SeekBar sbRed,sbGreen,sbBlue;
    TextView tvRed,tvGreen,tvBlue;
    ImageView ivMuestra;
    TextView tvCodigo;
    Switch modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRed=(TextView)findViewById(R.id.tv_red);
        sbRed=(SeekBar)findViewById(R.id.sb_red);
        tvBlue=(TextView)findViewById(R.id.tv_blue);
        sbBlue=(SeekBar)findViewById(R.id.sb_blue);
        tvGreen=(TextView)findViewById(R.id.tv_green);
        sbGreen=(SeekBar)findViewById(R.id.sb_green);
        ivMuestra=(ImageView)findViewById(R.id.iv_muestra);
        tvCodigo=(TextView)findViewById(R.id.tv_codigo);
        modo=(Switch)findViewById(R.id.modo);
        sbRed.setOnSeekBarChangeListener(this);
        sbGreen.setOnSeekBarChangeListener(this);
        sbBlue.setOnSeekBarChangeListener(this);
        modo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    modo.setText(R.string.txtOn);
                }
                else{
                    modo.setText(R.string.txtOff);
                    sbGreen.setProgress(sbRed.getProgress());
                    sbBlue.setProgress(sbRed.getProgress());
                }
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar==sbRed){
            tvRed.setText(String.valueOf(i));
            if (!modo.isChecked()){
                sbGreen.setProgress(sbRed.getProgress());
                sbBlue.setProgress(sbRed.getProgress());
            }
        }
        if (seekBar==sbGreen){
            tvGreen.setText(String.valueOf(i));
            if (!modo.isChecked()){
                sbRed.setProgress(sbGreen.getProgress());
                sbBlue.setProgress(sbGreen.getProgress());
            }
        }
        if (seekBar==sbBlue){
            tvBlue.setText(String.valueOf(i));
            if (!modo.isChecked()){
                sbGreen.setProgress(sbBlue.getProgress());
                sbRed.setProgress(sbBlue.getProgress());
            }
        }
        int c= Color.rgb(
                sbRed.getProgress(),
                sbGreen.getProgress(),
                sbBlue.getProgress());
        ivMuestra.setBackgroundColor(c);
        tvCodigo.setText(
                String.format("%02X", sbRed.getProgress()).toUpperCase()+
                String.format("%02X", sbGreen.getProgress()).toUpperCase()+
                String.format("%02X", sbBlue.getProgress()).toUpperCase()
        );
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
}
