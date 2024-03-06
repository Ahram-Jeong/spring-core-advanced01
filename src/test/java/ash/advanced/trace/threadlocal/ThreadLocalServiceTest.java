package ash.advanced.trace.threadlocal;

import ash.advanced.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {
    private ThreadLocalService service = new ThreadLocalService();

    @Test
    void field() {
        log.info("main start");
        // 스레드 생성
        Runnable userA = () -> {
            service.logic("WOODZ");
        };
        Runnable userB = () -> {
            service.logic("MOODZ");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
//        sleep(2000); // ThreadLocal 적용X -> 동시성 문제 발생X, ThreadLocal 적용O -> 동시성 문제 발생X
        sleep(100); // ThreadLocal 적용X -> 동시성 문제 발생O, ThreadLocal 적용O -> 동시성 문제 발생X
        threadB.start();

        sleep(3000); // 메인 스레드 종료 대기
        log.info("main exit");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
