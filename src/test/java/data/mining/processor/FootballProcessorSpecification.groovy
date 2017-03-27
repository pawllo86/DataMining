package data.mining.processor

import spock.lang.*

class FootballProcessorSpecification extends Specification {

    def processor = new FootballProcessor()

    @Unroll
    "parse string value"() {
        when:
        def result = processor.parseStringValue(value)

        then:
        notThrown(NullPointerException)
        expected == result

        where:
        value || expected
        null  || null
        ""    || null
        "ala" || null
        "100" || 100
        "10A" || 10
    }

    @Unroll
    "fiter data"() {
        when:
        def result = processor.filterLines(value)

        then:
        notThrown(NullPointerException)
        expected == result

        where:
        value || expected
        null  || null
        ["10 20 30 40", "10 11 23 12", "---------", "", ""] || []
    }
}
