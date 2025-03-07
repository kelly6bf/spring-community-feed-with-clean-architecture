package site.study.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositiveIntegerCounterTest {

    @DisplayName("정수값 증가 테스트")
    @Test
    void givenCreatedWhenIncreaseThenCountIsOne() {
        // given
        PositiveIntegerCounter count = new PositiveIntegerCounter();

        // when
        count.increase();

        // then
        assertEquals(1, count.getCount());
    }

    @DisplayName("정수값 감소 테스트")
    @Test
    void givenCreatedAndLikedWhenDecreaseThenCountIsOne() {
        // given
        PositiveIntegerCounter count = new PositiveIntegerCounter();
        count.increase();

        // when
        count.decrease();

        // then
        assertEquals(0, count.getCount());
    }

    @DisplayName("정수값이 0 이하로 감소되지 않는지 테스트")
    @Test
    void givenCreatedWhenDecreaseThenCountIsZero() {
        // given
        PositiveIntegerCounter count = new PositiveIntegerCounter();

        // when
        count.decrease();

        // then
        assertEquals(0, count.getCount());
    }
}
