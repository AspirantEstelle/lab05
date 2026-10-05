# Lab05 — Python TDD와 안드로이드 사칙연산

## 처음 실행하는 순서

### 1. Python 테스트

`calculator-tdd/run-tests.cmd`를 더블클릭한다. 테스트 8개 뒤에 `OK`가 나오면 성공이다.
다시 캡처하려면 그 콘솔 창을 `Win + Shift + S`로 캡처하고 **`screenshots/tdd-result.png`**로 저장한다.
현재 포함된 `tdd-result.png`는 Python IDLE에서 테스트 8개가 실제 통과한 콘솔 화면이다.

직접 명령으로 실행할 때는 `calculator-tdd` 폴더에서:

```powershell
py -m unittest -v test_FourBasicOpt
```

- `test_FourBasicOpt.py`: 먼저 작성한 테스트. 입력과 기대하는 답을 비교한다.
- `FourBasicOpt.py`: 그 테스트를 통과시키는 실제 사칙연산 구현.
- 교재: `ch04_테스트주도개발(TDD)_v1.3.pdf` 48~52페이지.
- 테스트를 먼저 작성해 클래스가 없는 상태의 실패를 확인한 뒤, 계산 클래스를 구현해 8개 모두 통과시켰다.
- 교재 테스트가 `divide(100, 0) == 0`을 요구하므로 Python 실습에서는 0을 반환한다. 수학적인 정의는 아니다.

### 2. 안드로이드 계산기

1. Android Studio를 실행하고 **Open**을 누른다.
2. 이 저장소의 **`android-calculator` 폴더**를 선택한다.
3. Gradle 동기화가 끝날 때까지 기다린다. 처음에는 인터넷 연결이 필요할 수 있다.
4. 실행 기기에서 기존 **Medium Phone API 36** 또는 연결된 안드로이드 기기를 선택한다.
5. 상단의 초록색 **Run ▶** 버튼을 누른다.
6. 숫자 `100`과 `10`을 입력한 뒤 `÷`를 누르면 `100 ÷ 10 = 10`이 표시된다.
7. 가상 기기의 캡처 기능 또는 `Win + Shift + S`로 실행 화면을 캡처한다.

`screenshots/android-app.png`는 실제 API 36 가상 기기에서 계산한 화면을 캡처한 파일이다.

- 음수와 소수를 지원하며, 빈 입력·잘못된 숫자·0으로 나누기를 화면에 안내한다.
- 안드로이드 앱에서는 0으로 나누기를 오류로 표시한다. Python 교재의 특수한 테스트 규칙과 구분한다.
- `app/src/main/java/com/example/lab05calculator/MainActivity.java`: 입력을 읽고 연산한다.
- `app/src/main/res/layout/activity_main.xml`: 입력칸, 버튼, 결과 표시 영역의 배치.
- 빌드 환경: JDK 17 이상(Android Studio 내장 JDK 사용), Android SDK 35, AGP 8.13.1, Gradle 9.2.1.
- `local.properties`는 PC별 SDK 경로이므로 Git에 올리지 않는다. Android Studio에서 열면 해당 PC의 경로를 설정한다.

명령으로 빌드할 때는 `android-calculator` 폴더에서:

```powershell
.\gradlew.bat assembleDebug
```

APK: `android-calculator/app/build/outputs/apk/debug/app-debug.apk`

추가 개발용 테스트는 Android Instrumentation으로 실제 입력칸과 버튼을 검사한다.
사칙연산, 음수, 소수, 0 나누기, 빈 입력, 잘못된 숫자를 확인하고 정상 결과 화면을 캡처한다.

```powershell
.\gradlew.bat assembleDebug assembleDebugAndroidTest
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb install -r app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb shell am instrument -w com.example.lab05calculator.test/com.example.lab05calculator.CalculatorTestRunner
```

## 제출 전 확인

- 새 GitHub 저장소 이름: `lab05` (기존 소켓/블로그 저장소와 별개).
- 두 프로젝트의 소스, Gradle Wrapper, 이 설명, 캡처 이미지 두 장을 포함한다.
- `.gradle`, `build`, `local.properties`, `__pycache__`는 `.gitignore`로 제외한다.
- 최종 제출물은 새 GitHub 저장소 URL이다.
- 소스와 실행 캡처 두 장이 준비되어 있다. 새 GitHub 저장소 생성 및 업로드는 별도 단계이다.

## 참고

- [Android 공식 명령줄 빌드 안내](https://developer.android.com/build/building-cmdline)
- [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html)
