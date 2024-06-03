package com.fitmate.myfit

import com.fitmate.myfit.common.GlobalStatus
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class MyFitApplication

fun main(args: Array<String>) {

    runApplication<MyFitApplication>(*args) {
        val active = System.getProperty(GlobalStatus.SPRING_PROFILES_ACTIVE)
        if (active == null) {
            System.setProperty(GlobalStatus.SPRING_PROFILES_ACTIVE, GlobalStatus.SPRING_PROFILES_ACTIVE_DEFAULT)
        }
        System.setProperty(
            GlobalStatus.SPRING_PROFILES_ACTIVE,
            System.getProperty(GlobalStatus.SPRING_PROFILES_ACTIVE, GlobalStatus.SPRING_PROFILES_ACTIVE_DEFAULT)
        )
    }
}
