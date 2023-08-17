package com.example.a02_caculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt_Show, txt_Result;
    Button btn_delete;
    Button btn_c, btn_round_brackets, btn_percent, btn_division;
    Button btn_7, btn_8, btn_9, btn_x;
    Button btn_4, btn_5, btn_6, btn_subtraction;
    Button btn_1, btn_2, btn_3, btn_summation;
    Button btn_opposite, btn_0, btn_comma, btn_equal_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_Show = findViewById(R.id.txt_Show);
        txt_Result = findViewById(R.id.txt_Result);
        btn_delete = findViewById(R.id.btn_delete);
        btn_c = findViewById(R.id.btn_c);
        btn_round_brackets = findViewById(R.id.btn_round_brackets);
        btn_percent = findViewById(R.id.btn_percent);
        btn_division = findViewById(R.id.btn_division);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_x = findViewById(R.id.btn_x);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_subtraction = findViewById(R.id.btn_subtraction);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_summation = findViewById(R.id.btn_summation);
        btn_opposite = findViewById(R.id.btn_opposite);
        btn_0 = findViewById(R.id.btn_0);
        btn_comma = findViewById(R.id.btn_comma);
        btn_equal_sign = findViewById(R.id.btn_equal_sign);
    }

    public void Click(View view){
        Button button = (Button) view;
        String data = button.getText().toString();
        String dataToCaculate = txt_Show.getText().toString(); // ==> Lấy dữ liệu được nhập vào từ trên txt_Show để thực hiện tính toán

        // Nếu như nút nhấn là nút "C" có nghĩa là xóa toàn bộ cả phần phía txt_Show và txt_Result;
        if (data.equals("C")){
            txt_Show.setText("");
            txt_Result.setText("0");
            Toast.makeText(this, "Đã xóa toàn bộ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        // Nếu như khi nhấn dấu "=" nghĩa là thực hiện đưa ra kết quả tính toán
        else if (data.equals("=")) {
            String calculation = txt_Show.getText().toString(); // Lấy chuỗi giá trị phép tính từ txt_Show rồi đưa vào calculation


            int openBracketIndex = calculation.lastIndexOf("(");
            while (openBracketIndex != -1){
                int closeBracketIndex = calculation.indexOf(")", openBracketIndex);
                if (closeBracketIndex != -1){
                    String innerExpression = calculation.substring(openBracketIndex + 1, closeBracketIndex);
                    String[] innerParts = innerExpression.split("[+\\-x:]");
                    List<String> innerNumbers = new ArrayList<>(Arrays.asList(innerParts));
                    List<String> innerOperators = new ArrayList<>();

                    for (char c : innerExpression.toCharArray()) {
                        if (c == '+' || c == '-' || c == 'x' || c == ':') {
                            innerOperators.add(String.valueOf(c));
                        }
                    }
                    for (int i = 0; i < innerOperators.size(); i++){
                        String operator = innerOperators.get(i);
                        if (operator.equals("x") || operator.equals(":")){
                            double num1 = Double.parseDouble(innerNumbers.get(i));
                            double num2 = Double.parseDouble(innerNumbers.get(i + 1));
                            double result;
                            if (operator.equals("x")) {
                                result = num1 * num2;
                            } else {
                                result = num1 / num2;
                            }
                            innerNumbers.set(i, String.valueOf(result));
                            innerNumbers.remove(i + 1);
                            innerOperators.remove(i);
                            i--;
                        }
                    }
                    double innerResult = Double.parseDouble(innerNumbers.get(0));
                    for (int i = 0; i < innerOperators.size(); i++) {
                        String operator = innerOperators.get(i);
                        double nextNumber = Double.parseDouble(innerNumbers.get(i + 1));

                        switch (operator) {
                            case "+":
                                innerResult += nextNumber;
                                break;
                            case "-":
                                innerResult -= nextNumber;
                                break;
                        }
                    }
                    calculation = calculation.substring(0, openBracketIndex) + innerResult + calculation.substring(closeBracketIndex + 1);
                }
                openBracketIndex = calculation.lastIndexOf("(");
            }

            String[] parts = calculation.split("[+\\-x:]"); // Tách theo phép tính
            List<String> numbers = new ArrayList<>(Arrays.asList(parts)); // Tạo ra một danh sách chứa các số
            List<String> operators = new ArrayList<>(); // Danh sách dùng để chứa các phép tính

            // Thực hiện lọc các phép tính từ chuỗi sau đó thêm vào trong operators
            for (char c : calculation.toCharArray()) {
                if (c == '+' || c == '-' || c == 'x' || c == ':') {
                    operators.add(String.valueOf(c));
                }
            }

            // Xử lý trường hợp phép tính nhân và chia
            for (int i = 0; i < operators.size(); i++){
                String operator = operators.get(i);
                if (operator.equals("x") || operator.equals(":")){
                    double num1 = Double.parseDouble(numbers.get(i));
                    double num2 = Double.parseDouble(numbers.get(i + 1));
                    double result;
                    if (operator.equals("x")) {
                        result = num1 * num2;
                    } else {
                        result = num1 / num2;
                    }
                    numbers.set(i, String.valueOf(result)); // Ghi kết quả vào danh sách số tại vị trí hiện tại.
                    numbers.remove(i + 1); // Loại bỏ số thứ 2
                    operators.remove(i); // Loại bỏ phép tính
                    i--; // Giảm dần chỉ số i để kiểm tra phép tính ở cùng vị trí
                }
            }

            // Thực hiện xử lý đối với phép cộng và phép trừ
            double result = Double.parseDouble(numbers.get(0));
            for (int i = 0; i < operators.size(); i++){
                String operator = operators.get(i);
                double nextNumber = Double.parseDouble(numbers.get(i + 1));
                switch (operator){
                    case "+":
                        result += nextNumber;
                        break;
                    case "-":
                        result -= nextNumber;
                        break;
                }
            }
            txt_Result.setText(String.valueOf(result));
        }
        // Nếu như khi nhấn dấu "«" có nghĩa là xóa một phần tử cuối cùng
        else if (data.equals("«")) {
            dataToCaculate = dataToCaculate.substring(0, dataToCaculate.length()-1);
            Toast.makeText(this, "Đã xóa phần tử cuối cùng rồi đó", Toast.LENGTH_SHORT).show();
        }
        // Nếu như nhấn dấu "()" thì khiểm tra xem đã tồn tại phần tử nào giống dấu "(" hay chưa, nếu chưa thì tạo đấu "(" còn nếu có rồi thì tạo dấu ")"
        else if (data.equals("()")) {
            String calculation = txt_Show.getText().toString();
            int openBracketCount = 0;
            int closeBracketCount = 0;
            for (char c : calculation.toCharArray()) {
                if (c == '(') {
                    openBracketCount++;
                } else if (c == ')') {
                    closeBracketCount++;
                }
            }
            if (openBracketCount == closeBracketCount)
                txt_Show.setText(calculation + "("); // Chưa có dấu "(" hoặc đã có cặp ngoặc, thêm dấu "("
            else if (closeBracketCount < openBracketCount)
                txt_Show.setText(calculation + ")"); // Đã có dấu "(" nhưng chưa đóng ngoặc hoàn chỉnh, thêm dấu ")"
            Toast.makeText(this, "Đang kiểm tra dấu '()'", Toast.LENGTH_SHORT).show();
            return;
        }
        // Nếu như nhấn dấu "%" thì có nghĩa là là chia số đó cho 100
        else if (data.equals("%")) {
            Toast.makeText(this, "Đang thực hiện chia số đó cho 100", Toast.LENGTH_SHORT).show();
            return;
        }
        // Nếu nhân dấu "±" thì có nghĩa là đảo chiều dấu của phần tử phía trước nó
        else if (data.equals("±")) {
            Toast.makeText(this, "Đang thực hiện đảo chiều dấu phần tử đứng trước", Toast.LENGTH_SHORT).show();
            return;
        }
        // Nếu nhấn dấu "," nghĩa là tính theo kiểu có khác
        else if (data.equals(",")) {
            Toast.makeText(this, "Đang thực tính theo kiểu khác đấy", Toast.LENGTH_SHORT).show();
            return;
        }
        // Các trường hợp khác thì thực hiện phép tính hoặc nối phép tính như thường
        else {
            dataToCaculate += data;
        }
        txt_Show.setText(dataToCaculate);
    }
}