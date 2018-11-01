package com.github.kanon.monitor.config;

import com.github.kanon.monitor.notifier.StatusChangeNotifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * <p>通知配置</p>
 *
 * @author PengCheng
 * @date 2018/10/31
 */
@Configuration
@EnableScheduling
public class NotifierConfiguration {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MonitorPropertiesConfig monitorPropertiesConfig;

    @Bean
    @Primary
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(mobileNotifier());
        remindingNotifier.setReminderPeriod(TimeUnit.MINUTES.toMillis(1));
        return remindingNotifier;
    }

    @Bean
    public StatusChangeNotifier mobileNotifier(){
        return new StatusChangeNotifier(monitorPropertiesConfig,rabbitTemplate);
    }

    /**
     *  每1分钟发送一次
     */
    @Scheduled(fixedRate = 60_000L)
    public void remind() {
        remindingNotifier().sendReminders();
    }
}
