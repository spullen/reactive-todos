package net.scottpullen

import spock.lang.Specification

class AppTest extends Specification {
    def "application has a greeting"() {
        setup:
        def app = new Application()

        when:
        def result = app.greeting

        then:
        result != null
        result == "Hello world."
    }
}
