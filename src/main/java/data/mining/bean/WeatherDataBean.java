package data.mining.bean;

public class WeatherDataBean {

    private Integer day;

    private Integer maxTemperature;

    private Integer minTemperature;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getSpreadTemperature() {
        Integer result = null;
        if (getMaxTemperature() != null && getMinTemperature() != null) {
            result = getMaxTemperature().shortValue() - getMinTemperature().shortValue();
        }
        return result;
    }

}
