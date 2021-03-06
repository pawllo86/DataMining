package data.mining.processor

import data.mining.bean.FootballDataBean
import spock.lang.Specification
import spock.lang.Unroll

class FootballProcessorSpecification extends Specification {

    def processor = Spy(FootballProcessor)

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
        result == null

        when:
        FootballDataBean bean = processor.createDataBean("1.", "Arsenal", "38", "26", "9", "3", "79", "-", "36", "87")

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
        2 * processor.createDataBean(*_)
        result.size() == 2
    }

    def "create data from not existing file"() {
        processor.getFileName() >> "file_not_exists.dat"
        when:
        def result = processor.createData()

        then:
        1 * processor.filterLines(*_)
//        1 * processor.buildDataBeans(*_)
        0 * processor.createDataBean(*_)
        result.size() == 0
    }

    def "create data from existing file"() {
        when:
        def result = processor.createData()

        then:
        1 * processor.filterLines(*_)
//        1 * processor.buildDataBeans(*_)
        20 * processor.createDataBean(*_)
        result.size() == 20
    }

    def "get data"() {
        when:
        processor.getData()

        then:
        1 * processor.createData()
    }

    def "find smallest goals difference"() {
        when:
        def result = processor.findSmallestGoalsDifference()

        then:
        1 * processor.createData()
        result == "Club: Aston_Villa difference: 1"
    }

    def "find smallest goals difference when no data"() {
        processor.getData() >> []
        when:
        def result = processor.findSmallestGoalsDifference()

        then:
        result == "No data!"
    }

}
