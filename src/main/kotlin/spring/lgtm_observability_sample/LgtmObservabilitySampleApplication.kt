package spring.lgtm_observability_sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LgtmObservabilitySampleApplication

fun main(args: Array<String>) {
	runApplication<LgtmObservabilitySampleApplication>(*args)
}
