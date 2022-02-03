package com.justin.currencyconverter;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView spinnerFrom, spinnerTo, convertedValue;
    EditText currencyToConvert;
    ArrayList<String> arrayList;
    Dialog fromDialog;
    Dialog toDialog;
    Button buttonConvert;
    String convertFromValue, convertToValue, conversionValue;
    String[] currency = {"ALL", "XCD", "EUR", "BBD", "BTN", "BND", "XAF", "CUP", "USD", "FKP", "GIP", "HUF", "IRR", "JMD", "AUD", "LAK", "SVC",
            "LYD", "MKD", "XOF", "NZD", "OMR", "PGK", "RWF", "WST", "RSD", "SEK", "TZS", "AMD", "BSD", "BAM", "CVE", "CNY", "CRC", "GBP", "ZMK",
            "CZK", "ERN", "GEL", "HTG", "INR", "JOD", "KRW", "LBP", "MWK", "MRO", "MZN", "ANG", "PEN", "QAR", "STD", "SLL", "SOS", "ZMW", "XAG",
            "SDG", "SYP", "AOA", "AWG", "BHD", "BZD", "BWP", "BIF", "KYD", "COP", "DKK", "GTQ", "HNL", "IDR", "ILS", "KZT", "KWD", "BTC", "ZWL",
            "LSL", "MYR", "MUR", "MNT", "MMK", "NGN", "PAB", "PHP", "RON", "SAR", "SGD", "ZAR", "SRD", "TWD", "TOP", "VEF", "DZD", "BYN", "JEP",
            "ARS", "AZN", "BYR", "BOB", "BGN", "CAD", "CLP", "CDF", "DOP", "FJD", "GMD", "GYD", "ISK", "IQD", "JPY", "KPW", "LVL", "BMD", "IMP",
            "CHF", "MGA", "MDL", "MAD", "NPR", "NIO", "PKR", "PYG", "SHP", "SCR", "SBD", "LKR", "THB", "TRY", "AED", "VUV", "YER", "GGP", "CUC",
            "AFN", "BDT", "BRL", "KHR", "KMF", "HRK", "DJF", "EGP", "ETB", "XPF", "GHS", "GNF", "HKD", "XDR", "KES", "KGS", "LRD", "CLF", "TMT",
            "MOP", "MVR", "MXN", "NAD", "NOK", "PLN", "RUB", "SZL", "TJS", "TTD", "UGX", "UYU", "VND", "TND", "UAH", "UZS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = findViewById(R.id.spinner_from);
        spinnerTo = findViewById(R.id.spinner_to);
        buttonConvert = findViewById(R.id.btConvert);
        convertedValue = findViewById(R.id.converted_value);
        currencyToConvert = findViewById(R.id.currency_to_convert);

        arrayList = new ArrayList<>();
        for(String i : currency){
            arrayList.add(i);
        }

        spinnerFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDialog = new Dialog(MainActivity.this);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(650, 800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.edit_text);
                ListView listView = fromDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinnerFrom.setText(adapter.getItem(position));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(position);
                    }
                });
            }
        });

        spinnerTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDialog = new Dialog(MainActivity.this);
                toDialog.setContentView(R.layout.to_spinner);
                toDialog.getWindow().setLayout(650, 800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.edit_text);
                ListView listView = toDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinnerTo.setText(adapter.getItem(position));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(position);
                    }
                });
            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Double currencyToConvert = Double.valueOf(MainActivity.this.currencyToConvert.getText().toString());
                    getConversionValue(convertFromValue, convertToValue, currencyToConvert);
                } catch (Exception e) {

                }
            }
        });
    }

    public String getConversionValue(String convertFrom, String convertTo, Double currencyToConvert) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertTo+"&compact=ultra&apiKey=a848d0ed3868d1e531eb";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double conversionRateValue = round(((Double) jsonObject.get(convertFrom+"_"+convertTo)),2);
                    conversionValue = "" + round((conversionRateValue * currencyToConvert), 2);
                    convertedValue.setText(conversionValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
        return null;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

