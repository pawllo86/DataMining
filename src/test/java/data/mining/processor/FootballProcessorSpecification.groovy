package data.mining.processor

import data.mining.bean.FootballDataBean
import data.mining.util.FileUtils
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
    "filter data"() {
        when:
        def result = processor.filterLines(value)

        then:
        notThrown(NullPointerException)
        expected == result

        where:
        value || expected
        null  || null
        []    || []
        ["LP PNT G+ G-", "10 20 30 40", "10 11 23 12", "---------", "", ""] || ["10 20 30 40", "10 11 23 12"]
    }

    def "create data bean"() {
        when:
        def result = processor.createDataBean()

        then:
        thrown(IndexOutOfBoundsException)
        result == null;

        when:
        FootballDataBean bean = processor.createDataBean("1.", "Arsenal", "38", "26", "9", "3", "79", "-", "36", "87");

        then:
        matchFootballDataBean(bean)
    }

    void matchFootballDataBean(bean) {
        assert bean.club == "Arsenal"
        assert bean.goalsScored == 79
        assert bean.goalsLost == 36
        assert bean.goalsDifference == 43
    }

    def "build data beans"() {
        when:
        def data = ["1. Arsenal         38    26   9   3    79  -  36    87",
                    "2. Liverpool       38    24   8   6    67  -  30    80]"]
        def result = processor.buildDataBeans(data)

        then:
        result.size() == 2
    }

    def "create data"() {
        GroovyMock(FileUtils, global: true)
        FileUtils.getResourceContent(_) >> ["1. Arsenal         38    26   9   3    79  -  36    87",
                                            "2. Liverpool       38    24   8   6    67  -  30    80]"]
        when:
        def result = processor.createData();

        then:
        result.size() == 2
    }
}
