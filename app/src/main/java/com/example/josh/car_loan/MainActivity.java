package com.example.josh.car_loan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;



import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {

    private EditText inputCost;
    private EditText inputDown;
    private EditText inputAPR;
    private RadioButton Loan;
    private RadioButton Lease;
    private SeekBar length;
    private TextView monthNum;
    private TextView output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputCost = findViewById(R.id.inputCost);
        inputDown = findViewById(R.id.inputDown);
        inputAPR = findViewById(R.id.inputAPR);
        Loan = findViewById(R.id.radioLoan);
        Lease = findViewById(R.id.radioLease);
        length = findViewById(R.id.SeekBar);
        monthNum = findViewById(R.id.monthNum);
        output = findViewById(R.id.OutputView);
        length.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        monthNum.setText(i+"");
                        buttonPressed(seekBar);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
        inputCost.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        buttonPressed(textView);
                        return false;
                    }
                }
        );

        inputDown.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        buttonPressed(textView);
                        return false;
                    }
                }
        );

        inputAPR.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        buttonPressed(textView);
                        return false;
                    }
                }
        );


        Loan.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        buttonPressed(compoundButton);
                    }
                }
        );


        if(savedInstanceState != null){
            output.setText(savedInstanceState.getString("Payment"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Payment",output.getText().toString());

    }

    public void buttonPressed(View v){
        String input1 = inputCost.getText().toString();
        String input2 = inputDown.getText().toString();
        String input3 = inputAPR.getText().toString();
        double cost = Double.parseDouble(input1);
        double down = Double.parseDouble(input2);
        double APR = Double.parseDouble(input3);
        double months = length.getProgress();
        double total;
        if(Loan.isChecked()){
            double TAPR = APR /100;
            double MPR = TAPR / 12;
            double Tcost = cost - down;
            total = (MPR)*((Tcost)/(1-pow(1+(MPR),-months)));
        }else {
            double TAPR = APR /100;
            double MPR = TAPR / 12;
            double Tcost = (cost/3) - down;
            total = (MPR) * ((Tcost) / (1 - pow(1 + (MPR), -36)));
        }
        output.setText(String.format("%.2f",total));
    }
}
