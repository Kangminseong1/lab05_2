package com.cookandroid.lab_05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private EditText editInput;
    private TextView textResult;
    private Button btnCalculate;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editInput = findViewById(R.id.edit_input);
        textResult = findViewById(R.id.text_result);
        btnCalculate = findViewById(R.id.btn_calculate);
        calculator = new Calculator();

        // 버튼 클릭 시 계산 수행
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(editInput.getText().toString());
            }
        });
    }

    private void calculate(String input) {
        String expression = input.replace(" ", ""); // 공백 제거
        if (expression.isEmpty()) {
            textResult.setText("결과: ");
            return;
        }

        try {
            // 연산자(+, -, *, /)를 포함하여 토큰화
            StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/", true);
            List<String> tokens = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                tokens.add(tokenizer.nextToken());
            }

            if (tokens.isEmpty()) return;

            // 첫 번째 숫자 설정
            double result = Double.parseDouble(tokens.get(0));

            // 순차적으로 연산 수행 (Left-to-Right)
            for (int i = 1; i < tokens.size(); i += 2) {
                if (i + 1 >= tokens.size()) break;
                
                String operator = tokens.get(i);
                double nextValue = Double.parseDouble(tokens.get(i + 1));

                switch (operator) {
                    case "+":
                        result = calculator.add(result, nextValue);
                        break;
                    case "-":
                        result = calculator.subtract(result, nextValue);
                        break;
                    case "*":
                        result = calculator.multiply(result, nextValue);
                        break;
                    case "/":
                        result = calculator.divide(result, nextValue);
                        break;
                }
            }

            // 결과 포맷팅: 소수점 뒷자리가 0이면 제거하여 정수 형태로 표시
            String finalResult;
            if (result == (long) result) {
                finalResult = String.valueOf((long) result);
            } else {
                finalResult = String.valueOf(result);
            }
            
            textResult.setText("결과: " + finalResult);
        } catch (Exception e) {
            textResult.setText("결과: 오류");
        }
    }
}
