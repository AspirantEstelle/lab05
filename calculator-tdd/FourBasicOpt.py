"""강의 48~52페이지의 사칙연산 클래스."""


class FourBasicOpt:
    def add(self, x, y):
        return x + y

    def subtract(self, x, y):
        return x - y

    def divide(self, x, y):
        # 강의 51페이지의 divide(100, 0) == 0 테스트를 따르는 실습 규칙.
        # 수학적으로 0으로 나눈 값이 0이라는 의미는 아니다.
        if y == 0:
            return 0
        return x / y

    def multiply(self, x, y):
        return x * y
