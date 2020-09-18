package org.springframework.samples.petclinic.ryu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ryu")
public class RyuController {

    @Autowired
    private RyuService ryuService;

    @GetMapping("/async/sample")
    public String asyncSample() throws InterruptedException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ryuService.displayText("1 번째 실행입니다.");
        ryuService.displayTextOnAsync("2 번째 실행입니다.");
        ryuService.displayText("3번째 실행입니다.");

        stopWatch.stop();
        log.warn("### time: {}", stopWatch.shortSummary());

        return "gogogo";
    }

    @GetMapping("/sync/sample")
    public String syncSample() throws InterruptedException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ryuService.displayText("1 번째 실행입니다.");
        ryuService.displayTestOnSync("2 번째 실행입니다.");
        ryuService.displayText("3번째 실행입니다.");

        stopWatch.stop();
        log.warn("### time: {}", stopWatch.shortSummary());

        return "";
    }
}
