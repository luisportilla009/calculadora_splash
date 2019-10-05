package com.example.calculadora_01;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fathzer.soft.javaluator.DoubleEvaluator;


public class MainActivity extends AppCompatActivity{

    private TextView put_Into;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        put_Into = findViewById(R.id.chain);

    }

    /*public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }*/


    //@Override
    public void onClick(View view) {
        int count_Par = 0;
        String total = put_Into.getText().toString();
        switch (view.getId()){
            case R.id.button_0:
                total = total + '0';
                break;
            case R.id.button_1:
                total = total + '1';
                break;
            case R.id.button_2:
                total = total + '2';
                break;
            case R.id.button_3:
                total = total + '3';
                break;
            case R.id.button_4:
                total = total + '4';
                break;
            case R.id.button_5:
                total = total + '5';
                break;
            case R.id.button_6:
                total = total + '6';
                break;
            case R.id.button_7:
                total = total + '7';
                break;
            case R.id.button_8:
                total = total + '8';
                break;
            case R.id.button_9:
                total = total + '9';
                break;
            case R.id.button_div:
                if(compare(total)) {
                    total = total + '/';
                    findViewById(R.id.button_point).setEnabled(true);
                }
                break;
            case R.id.button_mul:
                if(compare(total)) {
                    total = total + '*';
                    findViewById(R.id.button_point).setEnabled(true);
                }
                break;
            case R.id.button_minus:
                if(total.length() == 0) {
                    total = total + '-';
                    break;
                }
                if(compare2(total)) {
                    total = total + '-';
                    findViewById(R.id.button_point).setEnabled(true);
                }
                break;
            case R.id.button_plus:
                if(compare(total)) {
                    total = total + '+';
                    findViewById(R.id.button_point).setEnabled(true
                    );
                }
                break;
            case R.id.button_point:
                if(compare(total)) {
                    total = total + '.';
                    findViewById(R.id.button_point).setEnabled(false);
                }

                break;
            case R.id.button_equal:
                //if(compare(total)) total = String.valueOf(eval(total));
                if(compare(total)) {
                    DoubleEvaluator evaluator = new DoubleEvaluator();
                    total = evaluator.evaluate(total).toString();
                }
                break;
            case R.id.button_about:
                Intent about = new Intent(this,AboutActivity.class);
                startActivity(about);
                break;
            case R.id.button_clear:
                if(total.length() != 0) total = total.substring(0, total.length() - 1);
                break;
            case R.id.button_clear_all:
                while (total.length() != 0) total = total.substring(0, total.length() - 1);
                break;
            case R.id.button_par0:
                if(!compare(total)){
                    count_Par ++;
                    total = total + '(';
                    findViewById(R.id.button_point).setEnabled(true);
                }
                break;
            case R.id.button_par1:
                if(compare(total) && count_Par > 0){
                    count_Par --;
                    total = total + ')';
                    findViewById(R.id.button_point).setEnabled(true);
                }
                break;
        }
        if (total.equals("Infinity")) total = "";
        put_Into.setText(total);
    }

    public Boolean compare(String total){
        String list_0 = "0123456789";
        int i = 1;
        if (total.length() != 0) {
            while (total.charAt(total.length() - 1) != list_0.charAt(list_0.length() - i)
                    && i != 10 ) i++;
            return total.charAt(total.length() - 1) == list_0.charAt(list_0.length() - i);

        }
        return false;
    }
    public Boolean compare2(String total){
        String list_0 = "0123456789/*()";
        int i = 1;
        if (total.length() != 0) {
            while (total.charAt(total.length() - 1) != list_0.charAt(list_0.length() - i)
                    && i != 14 ) i++;
            return total.charAt(total.length() - 1) == list_0.charAt(list_0.length() - i);

        }
        return false;
    }
}
