package com.pan.mib

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MibApplication

fun main(args: Array<String>) {
	runApplication<MibApplication>(*args)
}
