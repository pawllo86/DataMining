package data.mining.processor;

import data.mining.bean.FootballDataBean;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FootballProcessor extends  Processor<FootballDataBean> {

    public static final String DATA_FILE_NAME = "football.dat";

    public String findSmallestGoalsDifference() {
        if (getData() != null && !getData().isEmpty()) {
            Optional<FootballDataBean> bean = getData().stream().min(Comparator.comparing(FootballDataBean::getGoalsDifference));

            if (bean.isPresent()) {
                return "Club: " + bean.get().getClub() + " difference: " + bean.get().getGoalsDifference();
            }
        }
        return "No data!";
    }

    @Override
    protected List<String> filterLines(List<String> lines) {
        List<String> filteredLines = super.filterLines(lines);

        if (filteredLines != null && !filteredLines.isEmpty()) {
            filteredLines.remove(0);
            filteredLines = filteredLines.stream().filter(line -> line.trim().split("\\s").length > 1).collect(Collectors.toList());
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
