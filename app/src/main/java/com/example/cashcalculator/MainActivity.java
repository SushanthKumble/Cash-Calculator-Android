package com.example.cashcalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText et2000, et500, et200, et100, et50, et20, et10, et5, et2, et1;
    TextView txt2000, txt500, txt200, txt100, txt50, txt20, txt10, txt5, txt2, txt1;
    TextView txtfinalcash, finalcashinwords;
    Button btreset;

    ArrayList<Long> fluctuatecash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUi();
        fluctuatecash = new ArrayList<>();
        et2000.addTextChangedListener(new CashTextWatcher());
        et500.addTextChangedListener(new CashTextWatcher());
        et200.addTextChangedListener(new CashTextWatcher());
        et100.addTextChangedListener(new CashTextWatcher());
        et50.addTextChangedListener(new CashTextWatcher());
        et20.addTextChangedListener(new CashTextWatcher());
        et10.addTextChangedListener(new CashTextWatcher());
        et5.addTextChangedListener(new CashTextWatcher());
        et2.addTextChangedListener(new CashTextWatcher());
        et1.addTextChangedListener(new CashTextWatcher());

        btreset.setOnClickListener(view -> resetFields());
    }

    private void setUpUi() {
        txtfinalcash = findViewById(R.id.txtfinalcash);
        finalcashinwords = findViewById(R.id.txtfinalcashinwords);

        et2000 = findViewById(R.id.et2000);
        et200 = findViewById(R.id.et200);
        et100 = findViewById(R.id.et100);
        et50 = findViewById(R.id.et50);
        et20 = findViewById(R.id.et20);
        et10 = findViewById(R.id.et10);
        et5 = findViewById(R.id.et5);
        et2 = findViewById(R.id.et2);
        et500 = findViewById(R.id.et500);
        et1 = findViewById(R.id.et1);

        txt2000 = findViewById(R.id.txt2000);
        txt500 = findViewById(R.id.txt500);
        txt100 = findViewById(R.id.txt100);
        txt50 = findViewById(R.id.txt50);
        txt20 = findViewById(R.id.txt20);
        txt10 = findViewById(R.id.txt10);
        txt5 = findViewById(R.id.txt5);
        txt2 = findViewById(R.id.txt2);
        txt1 = findViewById(R.id.txt1);
        txt200 = findViewById(R.id.txt200);
        btreset = findViewById(R.id.btnreset);
    }

    private class CashTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            cashCalculate();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private void cashCalculate() {
        DecimalFormat df = new DecimalFormat("0");
        long totalCash = 0;
        fluctuatecash.clear();

        totalCash += calculateDenomination(et2000, txt2000, 2000, df);
        totalCash += calculateDenomination(et500, txt500, 500, df);
        totalCash += calculateDenomination(et200, txt200, 200, df);
        totalCash += calculateDenomination(et100, txt100, 100, df);
        totalCash += calculateDenomination(et50, txt50, 50, df);
        totalCash += calculateDenomination(et20, txt20, 20, df);
        totalCash += calculateDenomination(et10, txt10, 10, df);
        totalCash += calculateDenomination(et5, txt5, 5, df);
        totalCash += calculateDenomination(et2, txt2, 2, df);
        totalCash += calculateDenomination(et1, txt1, 1, df);

        txtfinalcash.setText("Total amount: " + df.format(totalCash));
        finalcashinwords.setText(convertNumberToWords(totalCash));
    }

    private long calculateDenomination(EditText et, TextView txt, long denomination, DecimalFormat df) {
        long num = 0;
        if (!et.getText().toString().isEmpty()) {
            num = Long.parseLong(et.getText().toString());
            long rowValue = num * denomination;
            txt.setText(df.format(rowValue));
            fluctuatecash.add(rowValue);
        }
        return num * denomination;
    }

    private void resetFields() {
        et2000.setText("");
        et500.setText("");
        et200.setText("");
        et100.setText("");
        et50.setText("");
        et20.setText("");
        et10.setText("");
        et5.setText("");
        et2.setText("");
        et1.setText("");

        txt2000.setText("0");
        txt500.setText("0");
        txt200.setText("0");
        txt100.setText("0");
        txt50.setText("0");
        txt20.setText("0");
        txt10.setText("0");
        txt5.setText("0");
        txt2.setText("0");
        txt1.setText("0");

        txtfinalcash.setText("Total amount: 0");
        finalcashinwords.setText("");
    }

    private String convertNumberToWords(long number) {
        if (number == 0) {
            return "zero";
        }

        final String[] units = {
                "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
                "seventeen", "eighteen", "nineteen"
        };

        final String[] tens = {
                "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
        };

        StringBuilder words = new StringBuilder();

        if (number >= 1000000) {
            words.append(convertNumberToWords(number / 1000000)).append(" million ");
            number %= 1000000;
        }
        if (number >= 1000) {
            words.append(convertNumberToWords(number / 1000)).append(" thousand ");
            number %= 1000;
        }
        if (number >= 100) {
            words.append(convertNumberToWords(number / 100)).append(" hundred ");
            number %= 100;
        }
        if (number >= 20) {
            words.append(tens[(int) number / 10]).append(" ");
            number %= 10;
        }
        if (number > 0) {
            words.append(units[(int) number]);
        }

        return words.toString().trim();
    }
}
