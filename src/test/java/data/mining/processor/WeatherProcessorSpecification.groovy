package data.mining.processor

import data.mining.bean.WeatherDataBean
import spock.lang.Specification
import spock.lang.Unroll

class WeatherProcessorSpecification extends Specification {

    def processor = Spy(WeatherProcessor)

    @Unroll
    "parse string value"() {
        when:
        def result = processor.parseStringValue(value)

        then:
        notThrown(NullPointerException)
        expected == result

        where:
        value  || expected
        null   || null
        ""     || null
        "76*"  || 76
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
        ["Dy MxT   MnT   AvT", "61 59 60", "82 52 67", "              ", "", ""] || ["61 59 60", "82 52 67"]
    }

    def "create data bean"() {
        when:
        def result = processor.createDataBean()

        then:
        thrown(IndexOutOfBoundsException)
        result == null

        when:
        WeatherDataBean bean = processor.createDataBean("1","88","59","74","53.8","0.00", "F", "280", "9.6", "270", "17",
                "1.6",  "93", "23", "1004.5");

        then:
        matchWeatherDataBean(bean)
    }

    void matchWeatherDataBean(bean) {
        assert bean.day == 1
        assert bean.maxTemperature == 88
        assert bean.minTemperature == 59
        assert bean.spreadTemperature == 29
    }

    def "build data beans"() {
        when:
        def data = ["10  84    64    74          57.5       0.00 F       210  6.6 050   9  3.4  84 40 1019.0",
                    "11  91    59    75          66.3       0.00 H       250  7.1 230  12  2.5  93 45 1012.6"]
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
        30 * processor.createDataBean(*_)
        result.size() == 30
    }

    def "get data"() {
        when:
        processor.getData()

        then:
        1 * processor.createData()
    }

    def "find smallest goals difference"() {
        when:
        def result = processor.findSmallestTemperatureSpread()

        then:
        1 * processor.createData()
        result == "Day: 14 spread: 2"
    }

    def "find smallest goals difference when no data"() {
        processor.getData() >> []
        when:
        def result = processor.findSmallestTemperatureSpread()

        then:
        result == "No data!"
    }

}
