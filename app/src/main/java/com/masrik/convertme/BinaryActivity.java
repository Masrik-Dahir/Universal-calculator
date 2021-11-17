package com.masrik.convertme;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BinaryActivity extends AppCompatActivity {
    TextView output, input;
    Button b1;
    Button b0;
    Button b_equal;
    Button b_multi;
    Button b_divide;
    Button b_add;
    Button r_shift;
    Button l_shift;
    Button to_dec;
    Button to_hex;
    Button b_not;
    Button b_and;
    Button b_or;
    Button b_xor;
    Button b_ieee;
    Button b_del;
    Button b_sub;
    Button b_clear;
    Button b_dot;
    Button b_comp1;
    Button b_comp2;
    Button b_adv;
    char ACTION;
    double val1 = Double.NaN;
    String operation;
    double value, valueone, result;
    int subs = 1;
    boolean negative = false;
    private double val2;

    /**
     * Called when the activity is first created.
     */
    private void ifErrorOnOutput() {
        if (output.getText().toString().equals("Error")) {
            output.setText("");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary);

        setTitle("Basic Calculator");
        output = findViewById(R.id.output);
        input = findViewById(R.id.input);
        b0 = findViewById(R.id.button0);
        b1 = findViewById(R.id.button1);
        b_dot = findViewById(R.id.button_dot);
        b_add = findViewById(R.id.button_add);
        b_sub = findViewById(R.id.button_sub);
        b_multi = findViewById(R.id.button_multi);
        b_divide = findViewById(R.id.button_divide);
        b_equal = findViewById(R.id.button_equal);
        b_clear = findViewById(R.id.button_clear);
        b_del = findViewById(R.id.button_del);
        r_shift = findViewById(R.id.button_r_shift);
        l_shift = findViewById(R.id.button_l_shift);
        to_dec = findViewById(R.id.button_dec);
        to_hex = findViewById(R.id.button_hex);
        b_not = findViewById(R.id.button_not);
        b_and = findViewById(R.id.button_and);
        b_or = findViewById(R.id.button_or);
        b_xor = findViewById(R.id.button_xor);
        b_ieee = findViewById(R.id.button_ieee);
        b_comp1 = findViewById(R.id.button_comp1);


        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (negative == false) {
                    input.setText(input.getText() + "0");
                } else {
                    input.setText(input.getText() + "0");
                    negative = false;
                }

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (negative == false) {
                    input.setText(input.getText() + "1");
                } else {
                    input.setText(input.getText() + "-1");
                    negative = false;
                }
            }
        });


        b_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().toString().contains("."))
                    if (input.getText().equals("")) {
                        input.setText("0.");
                    } else {
                        input.setText(input.getText() + ".");
                    }
            }
        });

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().equals("")) {
                    operation = "+";
                    input();
                }
            }
        });

        b_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().equals("")) {
                    operation = "-";
                    input();
                }
            }
        });

        b_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().equals("")) {
                    operation = "*";
                    input();
                }
            }
        });

        b_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().equals("")) {
                    operation = "/";
                    input();
                }
            }
        });

        b_comp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().equals("")) {
                    value = Double.parseDouble(input.getText().toString());
                    String result_1 = String.valueOf((int) value);
                    String news = Func.comp_1(result_1);

                    input.setText(news);
                    output.setText(value + " (1's-C)");
                }

            }
        });


        b_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!(output.getText().equals("") && input.getText().equals(""))) {
                        valueone = Double.parseDouble(input.getText().toString());
                        if (operation.equals("+")) {
                            value = value + valueone;
                            result = value;
                            output.setText(output.getText() + String.valueOf(valueone));
                            input.setText(String.valueOf(result));
                        }
                        if (operation.equals(" (P) ")) {
                            value = Func.permutation(value, valueone);
                            result = value;
                            output.setText(output.getText() + String.valueOf(valueone));
                            input.setText(String.valueOf(result));
                        }
                        if (operation.equals(" (C) ")) {
                            value = Func.combination(value, valueone);
                            result = value;
                            output.setText(output.getText() + String.valueOf(valueone));
                            input.setText(String.valueOf(result));
                        }
                        if (operation.equals("-")) {
                            value = value - valueone;
                            result = value;
                            output.setText(output.getText() + String.valueOf(valueone));
                            input.setText(String.valueOf(result));
                        }
                        if (operation.equals("*")) {
                            value = value * valueone;
                            result = value;
                            output.setText(output.getText() + String.valueOf(valueone));
                            input.setText(String.valueOf(result));
                        }
                        if (operation.equals("/")) {
                            value = value / valueone;
                            result = value;
                            output.setText(output.getText() + String.valueOf(valueone));
                            input.setText(String.valueOf(result));
                        }
//                        if (operation.equals("1_s")) {
////                            value = value / valueone;
//
//                        }


                    }
                } catch (Exception e) {
                    output.setText("Error");
                }
            }
        });

        b_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = 0;
                valueone = 0;
                result = 0;
                input.setText("");
                output.setText("");
            }
        });

        b_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().length() > 0) {
                    CharSequence name = input.getText().toString();
                    input.setText(name.subSequence(0, name.length() - 1));
                } else {
                    value = Double.NaN;
                    valueone = Double.NaN;
                    input.setText("");
                    output.setText("");
                }
            }
        });


        // Advanced button
        Button next = findViewById(R.id.button_adv);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Scroll.class);
                startActivityForResult(myIntent, 0);
            }

        });

    }

    void input() {
        value = Double.parseDouble(input.getText().toString());
        input.setText("");
        output.setText(value + operation);
    }

}
