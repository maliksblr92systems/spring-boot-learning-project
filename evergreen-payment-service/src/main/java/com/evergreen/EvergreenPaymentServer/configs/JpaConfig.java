package com.evergreen.EvergreenPaymentServer.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * this will make below work by automcatically providing field values
 * 
 * @Column(name = "created_at", nullable = false, updatable = false)
 * @CreatedDate private Instant createdAt;
 * 
 * @Column(name = "updated_at", nullable = false, updatable = true)
 * @LastModifiedDate private Instant updatedAt;
 * 
 *                   you also need to annotate enity @EntityListeners(AuditingEntityListener.class)
 * 
 */

@Configuration
@EnableJpaAuditing
public class JpaConfig {

}
