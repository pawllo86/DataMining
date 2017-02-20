package data.mining.processor;

import data.mining.bean.FootballDataBean;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FootballProcessor extends  Processor<FootballDataBean> {

    private static final String DATA_FILE_NAME = "football.dat";

    public void showSmallestGoalsDifference() {
        List<FootballDataBean> sortedFootballDataBeans = getData().stream()
                .sorted(Comparator.comparing(FootballDataBean::getGoalsDifference).reversed()).collect(Collectors.toList());

        FootballDataBean bean = sortedFootballDataBeans.get(0);
        if (bean != null) {
            System.out.println("The Goals Difference:");
            System.out.println("Club: " + bean.getClub() + " difference: " + bean.getGoalsDifference());
        } else {
            System.out.println("No data!");
        }
    }

    @Override
    protected List<String> filterLines(List<String> lines) {
        List<String> filteredLines = super.filterLines(lines);
        filteredLines.remove(0);
        filteredLines.remove(filteredLines.size() - 4);

        return filteredLines;
    }

    @Override
    protected String getFileName() {
        return DATA_FILE_NAME;
    }

    @Override
    protected FootballDataBean createDataBean(String[] lineWords) {
        FootballDataBean bean = new FootballDataBean();
        bean.setClub(lineWords[1]);
        bean.setGoalsScored(parseStringValue(lineWords[6]));
        bean.setGoalsLost(parseStringValue(lineWords[8]));

        return bean;
    }

}
