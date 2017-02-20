package data.mining.bean;

public class FootballDataBean {

    private String club;

    private Integer goalsScored;

    private Integer goalsLost;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public Integer getGoalsLost() {
        return goalsLost;
    }

    public void setGoalsLost(Integer goalsLost) {
        this.goalsLost = goalsLost;
    }

    public Integer getGoalsDifference() {
        Integer result = null;
        if (getGoalsScored() != null && getGoalsLost() != null) {
            result = Math.abs(getGoalsScored().shortValue() - getGoalsLost().shortValue());
        }
        return result;
    }

}
