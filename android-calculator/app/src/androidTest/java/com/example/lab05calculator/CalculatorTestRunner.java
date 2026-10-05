package com.example.lab05calculator;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;

/** 실제 가상 기기의 입력칸과 버튼을 검증하는 추가 개발용 테스트. */
public class CalculatorTestRunner extends Instrumentation {
    private Activity activity;
    private int passed;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        start();
    }

    private void check(String left, String right, int button, String expected) {
        runOnMainSync(() -> {
            ((EditText) activity.findViewById(R.id.firstNumber)).setText(left);
            ((EditText) activity.findViewById(R.id.secondNumber)).setText(right);
            activity.findViewById(button).performClick();
            String actual = ((TextView) activity.findViewById(R.id.resultText)).getText().toString();
            if (!expected.equals(actual)) {
                throw new AssertionError("Expected " + expected + ", got " + actual);
            }
        });
        passed++;
    }

    @Override
    public void onStart() {
        Bundle result = new Bundle();
        try {
            Intent intent = new Intent(getTargetContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity = startActivitySync(intent);
            waitForIdleSync();
            check("100", "10", R.id.addButton, "100 + 10 = 110");
            check("100", "-10", R.id.subtractButton, "100 − -10 = 110");
            check("1.5", "0.2", R.id.multiplyButton, "1.5 × 0.2 = 0.3");
            check("100", "10", R.id.divideButton, "100 ÷ 10 = 10");
            check("-9", "3", R.id.divideButton, "-9 ÷ 3 = -3");
            check("100", "0", R.id.divideButton, "0으로 나눌 수 없습니다.");
            check("", "10", R.id.addButton, "올바른 숫자를 입력하세요.");
            check("-", "10", R.id.addButton, "올바른 숫자를 입력하세요.");
            // 캡처에는 실제 버튼으로 계산된 정상 결과를 남긴다.
            check("100", "10", R.id.divideButton, "100 ÷ 10 = 10");
            runOnMainSync(() -> {
                if (((EditText) activity.findViewById(R.id.firstNumber)).getError() != null) {
                    throw new AssertionError("Valid input must clear the previous error");
                }
            });
            waitForIdleSync();
            Thread.sleep(800);
            Bitmap screenshot = getUiAutomation().takeScreenshot();
            if (screenshot == null) throw new IllegalStateException("Screenshot unavailable");
            File target = new File(getTargetContext().getExternalFilesDir(null), "android-app.png");
            try (FileOutputStream stream = new FileOutputStream(target)) {
                screenshot.compress(Bitmap.CompressFormat.PNG, 100, stream);
            }
            screenshot.recycle();
            result.putString("stream", "\nPASS: " + passed + " UI checks\nScreenshot: " + target + "\n");
            finish(Activity.RESULT_OK, result);
        } catch (Throwable error) {
            result.putString("stream", "\nFAIL: " + error + "\n");
            finish(Activity.RESULT_CANCELED, result);
        }
    }
}
