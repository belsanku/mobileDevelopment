package ru.belonogov.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Operations
        Button Result = findViewById(R.id.Result);
        Button Clear = findViewById(R.id.ClearAll);
        Button DeleteL = findViewById(R.id.DeleteLeft);
        Button Multiply = findViewById(R.id.Multi);
        Button Divide = findViewById(R.id.Divide);
        Button Mod = findViewById(R.id.Mod);
        Button Plus = findViewById(R.id.Plus);
        Button Minus = findViewById(R.id.Minus);

        //Digits
        Button Zero = findViewById(R.id.Zero);
        Button One = findViewById(R.id.One);
        Button Two = findViewById(R.id.Two);
        Button Three = findViewById(R.id.Three);
        Button Four = findViewById(R.id.Four);
        Button Five = findViewById(R.id.Five);
        Button Six = findViewById(R.id.Six);
        Button Seven = findViewById(R.id.Seven);
        Button Eight = findViewById(R.id.Eight);
        Button Nine = findViewById(R.id.Nine);

        //Symbol
        Button Dot = findViewById(R.id.Dot);

        Zero.setOnClickListener(this);
        One.setOnClickListener(this);
        Two.setOnClickListener(this);
        Three.setOnClickListener(this);
        Four.setOnClickListener(this);
        Five.setOnClickListener(this);
        Six.setOnClickListener(this);
        Seven.setOnClickListener(this);
        Eight.setOnClickListener(this);
        Nine.setOnClickListener(this);

        Result.setOnClickListener(this);
        Clear.setOnClickListener(this);
        DeleteL.setOnClickListener(this);
        Multiply.setOnClickListener(this);
        Divide.setOnClickListener(this);
        Mod.setOnClickListener(this);
        Plus.setOnClickListener(this);
        Minus.setOnClickListener(this);

        Dot.setOnClickListener(this);
    }

    float ValueOne, ValueTwo;

    boolean Addition, mSubtract, Multiplication, Division, Modnik;

    boolean isReady;

    @Override
    public void onClick(View view) {
        //Lines
        final TextView firstN = findViewById(R.id.firstN);

        switch (view.getId())
        {
            case R.id.Zero:
                firstN.setText(firstN.getText() + "0"); break;
            case R.id.One:
                firstN.setText(firstN.getText() + "1"); break;
            case R.id.Two:
                firstN.setText(firstN.getText() + "2"); break;
            case R.id.Three:
                firstN.setText(firstN.getText() + "3"); break;
            case R.id.Four:
                firstN.setText(firstN.getText() + "4"); break;
            case R.id.Five:
                firstN.setText(firstN.getText() + "5"); break;
            case R.id.Six:
                firstN.setText(firstN.getText() + "6"); break;
            case R.id.Seven:
                firstN.setText(firstN.getText() + "7"); break;
            case R.id.Eight:
                firstN.setText(firstN.getText() + "8"); break;
            case R.id.Nine:
                firstN.setText(firstN.getText() + "9"); break;
            case R.id.Dot:
                firstN.setText(firstN.getText() + "."); break;

            case R.id.DeleteLeft:
                if (firstN.getText() != "") {
                    String Temp = (String) firstN.getText();
                    Temp = Temp.substring(0, Temp.length() - 1);
                    firstN.setText(Temp);
                }
                break;

            case R.id.ClearAll:
                firstN.setText("");
                ValueTwo = 0;
                ValueOne = 0;
                isReady = false;
                Addition = false;
                Modnik = false;
                Multiplication = false;
                mSubtract = false;
                Division = false;
                break;

            case R.id.Plus:
                if (firstN.getText() != "") {
                    ValueOne = Float.parseFloat(firstN.getText() + "");
                    Addition = true;
                    isReady = true;
                    firstN.setText(null);
                    break;
                }else
                {
                    isReady = false;
                    break;
                }

            case R.id.Minus:
                if (firstN.getText() != "") {
                    ValueOne = Float.parseFloat(firstN.getText() + "");
                    mSubtract = true;
                    isReady = true;
                    firstN.setText(null);
                    break;
                }else
                {
                    isReady = false;
                    break;
                }

            case R.id.Divide:
                if (firstN.getText() != "") {
                    ValueOne = Float.parseFloat(firstN.getText() + "");
                    Division = true;
                    isReady = true;
                    firstN.setText(null);
                    break;
                }else
                {
                    isReady = false;
                    break;
                }

            case R.id.Multi:
                if (firstN.getText() != "") {
                    ValueOne = Float.parseFloat(firstN.getText() + "");
                    Multiplication = true;
                    isReady = true;
                    firstN.setText(null);
                    break;
                }else
                {
                    isReady = false;
                    break;
                }

            case R.id.Mod:
                if (firstN.getText() != "") {
                    ValueOne = Float.parseFloat(firstN.getText() + "");
                    Modnik = true;
                    isReady = true;
                    firstN.setText(null);
                    break;
                }else
                {
                    isReady = false;
                    break;
                }

            case R.id.Result:
                if (isReady) {
                    ValueTwo = Float.parseFloat(firstN.getText() + "");
                }else break;

                if (Addition) {
                    float answer = ValueOne + ValueTwo;
                    if (answer % 1 == 0)
                    {
                        firstN.setText((int)answer + "");
                        break;
                    }else {
                        firstN.setText(answer + "");
                        Addition = false;
                        break;
                    }
                }

                if (Modnik)
                {
                    firstN.setText(ValueOne % ValueTwo + "");
                    Modnik = false;
                    break;
                }

                if (mSubtract) {
                    firstN.setText(ValueOne - ValueTwo + "");
                    mSubtract = false;
                    break;
                }

                if (Multiplication) {
                    firstN.setText(ValueOne * ValueTwo + "");
                    Multiplication = false;
                    break;
                }

                if (Division) {
                    if (ValueTwo == 0 || ValueOne == 0)
                    {
                        firstN.setText("NaN");
                        break;
                    }else {
                        firstN.setText(ValueOne / ValueTwo + "");
                        Division = false;
                        break;
                    }
                }
        }
    }
}

