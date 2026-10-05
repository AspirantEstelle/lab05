package com.example.lab05calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.MathContext;

public class MainActivity extends Activity {
    private EditText firstNumber;
    private EditText secondNumber;
    private TextView resultText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.root).setOnApplyWindowInsetsListener((view, insets) -> {
            view.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            return insets;
        });
        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        resultText = findViewById(R.id.resultText);
        findViewById(R.id.addButton).setOnClickListener(view -> calculate('+'));
        findViewById(R.id.subtractButton).setOnClickListener(view -> calculate('-'));
        findViewById(R.id.multiplyButton).setOnClickListener(view -> calculate('*'));
        findViewById(R.id.divideButton).setOnClickListener(view -> calculate('/'));
        if (savedInstanceState != null) {
            resultText.setText(savedInstanceState.getString("result"));
        }
    }

    private BigDecimal readNumber(EditText input) {
        input.setError(null);
        String text = input.getText().toString().trim();
        if (text.isEmpty()) {
            input.setError(getString(R.string.missing_number));
            return null;
        }
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException error) {
            input.setError(getString(R.string.invalid_number));
            return null;
        }
    }

    private String format(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }

    private void calculate(char operation) {
        BigDecimal left = readNumber(firstNumber);
        BigDecimal right = readNumber(secondNumber);
        if (left == null || right == null) {
            resultText.setText(R.string.invalid_number);
            return;
        }
        BigDecimal result;
        String symbol;
        switch (operation) {
            case '+': result = left.add(right); symbol = "+"; break;
            case '-': result = left.subtract(right); symbol = "−"; break;
            case '*': result = left.multiply(right); symbol = "×"; break;
            case '/':
                if (right.signum() == 0) {
                    resultText.setText(R.string.division_zero);
                    return;
                }
                result = left.divide(right, MathContext.DECIMAL64);
                symbol = "÷";
                break;
            default: throw new IllegalArgumentException("Unknown operation");
        }
        resultText.setText(format(left) + " " + symbol + " " + format(right) + " = " + format(result));
        InputMethodManager keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(secondNumber.getWindowToken(), 0);
        firstNumber.clearFocus();
        secondNumber.clearFocus();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("result", resultText.getText().toString());
    }
}
