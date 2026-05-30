package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SalaryInfo {
    private static final int DATE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int HOURS_INDEX = 2;
    private static final int INCOME_INDEX = 3;
    private static final int EXPECTED_PARTS_COUNT = 4;

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
        LocalDate localFrom = LocalDate.parse(dateFrom, formatter);
        LocalDate localTo = LocalDate.parse(dateTo, formatter);
        Map<String, Integer> salaryByName = new HashMap<>();

        StringBuilder result = new StringBuilder();
        result.append("Report for period ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo);

        for (String name : names) {
            salaryByName.put(name, 0);
        }

        if (!localTo.isBefore(localFrom)) {
            for (String record: data) {
                if (record != null) {
                    String[] recordParts = record.split(" ");

                    if (recordParts.length == EXPECTED_PARTS_COUNT) {
                        LocalDate recordDate = LocalDate.parse(recordParts[DATE_INDEX], formatter);
                        String recordName = recordParts[NAME_INDEX];
                        String recordHours = recordParts[HOURS_INDEX];
                        String recordIncome = recordParts[INCOME_INDEX];

                        if (!recordDate.isBefore(localFrom) && !recordDate.isAfter(localTo)) {
                            Integer calculatedIncome = salaryByName.get(recordName)
                                    + Integer.parseInt(recordHours)
                                    * Integer.parseInt(recordIncome);
                            salaryByName.put(recordName, calculatedIncome);
                        }
                    }
                }
            }
        }

        for (String name : names) {
            result.append(System.lineSeparator())
                    .append(name)
                    .append(" - ")
                    .append(salaryByName.get(name));
        }

        return result.toString();
    }
}
