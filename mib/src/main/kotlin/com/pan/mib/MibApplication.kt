package com.pan.mib

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class MibApplication

fun main(args: Array<String>) {
	runApplication<MibApplication>(*args)
}
