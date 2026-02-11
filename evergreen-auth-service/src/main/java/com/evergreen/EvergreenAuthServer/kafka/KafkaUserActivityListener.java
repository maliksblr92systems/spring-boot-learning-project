package com.evergreen.EvergreenAuthServer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.models.UserActivityModel;
import com.evergreen.EvergreenAuthServer.repositories.AppUserRepository;
import com.evergreen.EvergreenAuthServer.repositories.UserActivityRepository;
import com.evergreen.lib.constants.enums.UserActivityStatus;
import com.evergreen.lib.constants.enums.UserActivityType;
import com.evergreen.lib.dtos.UserActivityEventDto;
import com.evergreen.lib.dtos.appuser.AuthUser;

@Component
public class KafkaUserActivityListener {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserActivityRepository userActivityRepository;

    // 4 means n-1 tries exactly three retries
    // exclude failure for RuntimeException.class
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 700, maxDelay = 12000, multiplier = 3), exclude = { RuntimeException.class })
    @KafkaListener(topics = "user-activity-topic", containerFactory = "kafkaListenerContainer")
    public void consumer(UserActivityEventDto userActivityEventDto, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) int offset) {
        AuthUser authUser = userActivityEventDto.user();
        UserActivityType type = userActivityEventDto.type();
        UserActivityStatus status = userActivityEventDto.status();
        AppUserModel appUser = appUserRepository.findById(authUser.id()).orElse(null);
        if (appUser != null) {
            UserActivityModel userActivity = new UserActivityModel();
            userActivity.setType(type);
            userActivity.setStatus(status);
            userActivity.setType(type);
            userActivity.setUser(appUser);
            userActivityRepository.save(userActivity);
        }

    }

    @DltHandler
    public void dltHandler(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) int offset) {

    }

}
