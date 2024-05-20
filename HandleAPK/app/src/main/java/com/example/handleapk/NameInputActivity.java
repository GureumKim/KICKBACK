package com.example.handleapk;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class NameInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameinput); // XML 레이아웃 파일 이름을 your_activity_layout로 교체하세요.

        // TextInputEditText의 인스턴스를 찾습니다.
        TextInputEditText textInputEditText = findViewById(R.id.inputView);

        // 여기에 버튼 클릭 리스너를 추가하거나 특정 이벤트를 처리하는 코드를 추가할 수 있습니다.
        // 예를 들어, 버튼 클릭 시 다음 액티비티로 이동하도록 설정합니다.
        findViewById(R.id.nextBtn).setOnClickListener(v -> { // XML에서 버튼의 ID를 yourButtonId로 교체하세요.
            // 사용자가 입력한 텍스트를 가져옵니다.
            String inputText = Objects.requireNonNull(textInputEditText.getText()).toString();

            // 새로운 액티비티를 시작하기 위한 인텐트를 생성합니다.
            Intent intent = new Intent(NameInputActivity.this, HandleActivity.class);

            // 인텐트에 사용자가 입력한 텍스트를 추가합니다.
            intent.putExtra("userInput", inputText);

            // 인텐트를 사용하여 새로운 액티비티를 시작합니다.
            startActivity(intent);
        });
    }
}
