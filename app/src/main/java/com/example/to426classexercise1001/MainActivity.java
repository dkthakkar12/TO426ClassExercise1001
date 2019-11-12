package com.example.to426classexercise1001;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.to426classexercise1001.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TextView.OnEditorActionListener,
        SeekBar.OnSeekBarChangeListener,
        RadioButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener {

    EditText editTextAmount;
    TextView textViewTipPercent, textViewTip, textViewTotal, textViewPerPerson;
    Spinner spinnerSplit;
    RadioButton radioButtonNoRound, radioButtonTipRound, radioButtonTotalRound;
    SeekBar seekBarTipPercent;

    Double dblPreTipAmount = 100.00;
    Double dblTipPercent = 0.20;
    Double dblTipAmount = 20.00;
    Double dblTotalAmount = 120.00;
    Double dblPerPersonAmount = 120.00;
    Double dblNumberOfPersons = 1.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAmount = findViewById(R.id.editTextAmount);
        textViewTipPercent = findViewById(R.id.textViewTipPercent);
        textViewTip = findViewById(R.id.textViewTip);
        textViewTotal = findViewById(R.id.textViewTotal);
        textViewPerPerson = findViewById(R.id.textViewPerPerson);
        spinnerSplit = findViewById(R.id.spinnerSplit);
        radioButtonNoRound = findViewById(R.id.radioButtonNoRound);
        radioButtonTipRound = findViewById(R.id.radioButtonTipRound);
        radioButtonTotalRound = findViewById(R.id.radioButtonTotalRound);
        seekBarTipPercent = findViewById(R.id.seekBarTipPercent);

        editTextAmount.setOnEditorActionListener(this);
        seekBarTipPercent.setOnSeekBarChangeListener(this);
        radioButtonNoRound.setOnCheckedChangeListener(this);
        radioButtonTipRound.setOnCheckedChangeListener(this);
        radioButtonTotalRound.setOnCheckedChangeListener(this);
        spinnerSplit.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSplit.setAdapter(dataAdapter);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        dblPreTipAmount = Double.parseDouble(editTextAmount.getText().toString());
        Toast.makeText(this, dblPreTipAmount.toString(), Toast.LENGTH_SHORT).show();
        updateEverything();
        return false;
    }

    public void updateEverything() {
        dblTipAmount = dblTipPercent * dblPreTipAmount;
        if(radioButtonTipRound.isChecked()) dblTipAmount = Math.ceil(dblTipAmount);
        dblTotalAmount = dblTipAmount + dblPreTipAmount;
        if(radioButtonTotalRound.isChecked()) dblTotalAmount = Math.ceil(dblTotalAmount);

        dblPerPersonAmount = dblTotalAmount / dblNumberOfPersons;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        textViewTip.setText(formatter.format(dblTipAmount));
        textViewTotal.setText(formatter.format(dblTotalAmount));
        textViewPerPerson.setText(formatter.format(dblPerPersonAmount));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        dblTipPercent = i / 100.00;
        textViewTipPercent.setText(i + "%");
        updateEverything();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Not doing anything
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Not doing anything
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        updateEverything();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String strSelection = adapterView.getItemAtPosition(i).toString();
        dblNumberOfPersons = Double.parseDouble(strSelection);
        updateEverything();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Not doing anything
    }
}
