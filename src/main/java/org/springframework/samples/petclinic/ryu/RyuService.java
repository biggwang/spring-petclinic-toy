package org.springframework.samples.petclinic.ryu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class RyuService {

    public void displayText(String text) {
        log.info(String.format("%s", text));
    }

    @Async
    public void displayTextOnAsync(String text) throws InterruptedException {
        Thread.sleep(2000);
        log.info("async | {}", text);
    }

    @Async
    public Future<String> displayTestOnAsync2(int i) {
        return new AsyncResult<String>(String.format("async i = %s", i));
    }

    public void displayTestOnSync(String text) throws InterruptedException {
        Thread.sleep(2000);
        log.info(String.format("%s", text));
    }
}
