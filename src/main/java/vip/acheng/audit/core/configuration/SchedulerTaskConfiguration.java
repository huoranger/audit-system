package vip.acheng.audit.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author 清风徐来
 * @since 2022/9/1 11:46
 */
@Configuration
@EnableScheduling
public class SchedulerTaskConfiguration {

    @Bean(name = "ThreadPoolTaskScheduler")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
