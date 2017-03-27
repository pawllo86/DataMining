package data.mining.processor;

import data.mining.bean.FootballDataBean;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FootballProcessor extends  Processor<FootballDataBean> {

    public static final String DATA_FILE_NAME = "football.dat";

    public String findSmallestGoalsDifference() {
        if (getData() != null && !getData().isEmpty()) {
            List<FootballDataBean> sortedFootballDataBeans = getData().stream()
                    .sorted(Comparator.comparing(FootballDataBean::getGoalsDifference).reversed()).collect(Collectors.toList());

            FootballDataBean bean = sortedFootballDataBeans.get(0);

            return "Club: " + bean.getClub() + " difference: " + bean.getGoalsDifference();
        } else {
            return "No data!";
        }
    }

    @Override
    protected List<String> filterLines(List<String> lines) {
        List<String> filteredLines = super.filterLines(lines);

        if (filteredLines != null && !filteredLines.isEmpty()) {
            filteredLines.remove(0);
            filteredLines.remove(filteredLines.size() - 4);
        }
        return filteredLines;
    }

    @Override
    protected FootballDataBean createDataBean(String[] lineWords) {
        FootballDataBean bean = new FootballDataBean();
        bean.setClub(lineWords[1]);
        bean.setGoalsScored(parseStringValue(lineWords[6]));
        bean.setGoalsLost(parseStringValue(lineWords[8]));

        return bean;
    }

    @Override
    protected String getFileName() {
        return DATA_FILE_NAME;
    }

}
