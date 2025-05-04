import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.XMLEncoder;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FilteredResult extends JFrame {

    private JPanel jPanel;
    private JCheckBox selectYearCheckBox;
    private JList list1;
    private JCheckBox selectWeekCheckBox;
    private JList list2;
    private JCheckBox selectDateCheckBox;

    public FilteredResult() {
        setTitle("Filtered Lottery Results");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        selectYearCheckBox.setSelected(true);
        selectWeekCheckBox.setEnabled(true);
        selectWeekCheckBox.setEnabled(false);
        selectDateCheckBox.setEnabled(true);

        selectYearCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // When the year checkbox is selected,
                //      the week checkbox should be enabled but not yet selected;
                //      the date checkbox should be enabled but not selected;
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectWeekCheckBox.setEnabled(true);
                    selectWeekCheckBox.setSelected(false);
                    selectDateCheckBox.setEnabled(true);
                    selectDateCheckBox.setSelected(false);
                }
                // When the year checkbox is deselected,
                //      the week checkbox should be deselected and disabled
                //      the date checkbox should be enabled and deselected
                else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    selectWeekCheckBox.setEnabled(false);
                    selectWeekCheckBox.setSelected(false);
                    selectDateCheckBox.setEnabled(true);
                    selectDateCheckBox.setSelected(false);
                }
            }
        });

        selectDateCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // When the date checkbox is selected
                //      the year checkbox should be disabled and deselected
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectYearCheckBox.setEnabled(false);
                    selectYearCheckBox.setSelected(false);
                    selectWeekCheckBox.setEnabled(false);
                    selectWeekCheckBox.setSelected(false);
                }
                // When the date checkbox is deselected
                //      the year checkbox should be enabled and deselected
                else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    selectYearCheckBox.setEnabled(true);
                    selectYearCheckBox.setSelected(false);
                    selectWeekCheckBox.setEnabled(false);
                    selectWeekCheckBox.setSelected(false);
                }
            }
        });
    }

    public static void main(String[] args) {

        // Create a list of objects that contain values by which we can filter
        ArrayList<FilterValues> filterValuesList = new ArrayList<>();
        addFilterListElementsToUI(filterValuesList);

        // Render UI
        // UI is disabled
//        new FilteredResult();

        // Get filter value(s) from the UI after hitting the "Search" button
        // TODO ui.getFilterBy ==> year / year & week / date
        //     e.g. filterByYear = ui.getFilterBy;
        boolean filterByYear = false;
        boolean filterByWeek = false;
        boolean filterByDate = false;

        Scanner scanner = new Scanner(System.in);

        boolean isValid = false;
        while (!isValid) {
            System.out.println("Select how to filter results. Write one of the three options as follows:" +
                    " year / year and week / date");
            String inputValue = scanner.nextLine();

            if (inputValue.equals("year")) {
                filterByYear = true;
                isValid = true;

                System.out.println("Copy a year from the list above e.g. 2016");
            } else if (inputValue.equals("year and week")) {
                filterByYear = true;
                filterByWeek = true;
                isValid = true;

                System.out.println("Copy a year and a week from the list above e.g. 2016 1");
            } else if (inputValue.equals("date")) {
                filterByDate = true;
                isValid = true;

                System.out.println("Copy a date from the list above e.g. 2016.01.09.");
            } else {
                System.out.println("Select date OR year OR year and week!");
            }
        }

        // Default values for filters
        int filterValueYear = 0;
        int filterValueWeek = 0;
        String filterValueDateString = null;

        // TODO assign UI values to filterValue* instead of hard-coded values
        // Assign values from the UI
        String[] values = new String[2];
        String value = "";
        if (filterByWeek) {
            values = scanner.nextLine().split(" ");
            System.out.println("you selected year: " + values[0] + " and week: " + values[1]);
        } else if (filterByYear) {
            value = scanner.nextLine();
        } else {
            value = scanner.nextLine();
        }

        if (filterByYear) {
            if (filterByWeek) {
                try {
                    filterValueYear = Integer.parseInt(values[0]);
                    filterValueWeek = Integer.parseInt(values[1]);
                } catch (Exception exception) {
                    System.out.println("Select date OR year OR year and week in the format of the examples!");
                }
            } else {
                try {
                    filterValueYear = Integer.parseInt(value);
                } catch (Exception exception) {
                    System.out.println("Select date OR year OR year and week in the format of the examples!");
                }
            }
        } else if (filterByDate) {
            filterValueDateString = value;
        } else {
            System.out.println("Select date OR year OR year and week in the format of the examples!");
        }

        if (filterValueYear == 0 && filterValueWeek == 0
                && (filterValueDateString == null || filterValueDateString.equals(""))) {
            System.out.println("Incorrect filter format. Use the format provided in the example. " +
                    "You need to restart the program.");
            System.exit(0);
        }

        // Collect the already filtered DrawRecordBean objects inputValue a list
        ArrayList<DrawRecordBean> filteredResults = new ArrayList();
        filterResults(filteredResults, filterValueYear, filterValueWeek, filterValueDateString);
    }

    private static void addFilterListElementsToUI(ArrayList<FilterValues> filterValuesList) {
        BufferedReader bufferedReader = null;
        String line = "";

        try {
            InputStream inputStream = FilteredResult.class.getResourceAsStream("/otos.csv");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(";");
                FilterValues filterValues = new FilterValues();

                if(!(row[0].equalsIgnoreCase("year"))) {
                   filterValues.setYear(row[0]);
                }

                if(!(row[1].equalsIgnoreCase("week"))) {
                    filterValues.setWeek(row[1]);
                }

                if(!(row[2].equalsIgnoreCase("date"))
                    && !(row[2].equals(""))) {
                    filterValues.setDate(row[2]);
                }
                System.out.println(filterValues);
                System.out.println();

                filterValuesList.add(filterValues);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void filterResults(ArrayList<DrawRecordBean> filteredResults, int filterValueYear, int filterValueWeek, String filterValueDateString) {
        BufferedReader bufferedReader = null;
        String line = "";

        try {
            InputStream inputStream = FilteredResult.class.getResourceAsStream("/otos.csv");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(";");
                DrawRecordBean drawRecord = new DrawRecordBean();

                // filterByYearAndWeek
                if (filterValueYear != 0 && !(row[0].equalsIgnoreCase("year"))
                        && filterValueWeek != 0 && !(row[1].equalsIgnoreCase("week"))) {
                    int year = Integer.parseInt(row[0]);
                    int week = Integer.parseInt(row[1]);
                    if (filterValueYear == year && filterValueWeek == week) {
                        // assign object values and collect to array list
                        filteredResults.add(assignObjectValues(drawRecord, row));
                    }
                } // filterByYear
                else if (filterValueYear != 0 && !(row[0].equalsIgnoreCase("year"))) {
                    int year = Integer.parseInt(row[0]);
                    if (filterValueYear == year) {
                        // assign object values and collect to array list
                        filteredResults.add(assignObjectValues(drawRecord, row));
                    }
                } // filterByDate
                else if (filterValueDateString != null && !(row[2].equalsIgnoreCase("date"))) {
                    String dateString = row[2];
                    if (!dateString.equals("") && filterValueDateString.equals(dateString)) {
                        // assign object values and collect to array list
                        filteredResults.add(assignObjectValues(drawRecord, row));
                    }
                }
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // convert to correct data type and assign values
    private static DrawRecordBean assignObjectValues(DrawRecordBean drawRecord, String[] row) {
        if(!(row[0].equalsIgnoreCase("year"))) {
            int year = Integer.parseInt(row[0]);
            drawRecord.setYear(year);
        }

        if(!(row[1].equalsIgnoreCase("week"))) {
            drawRecord.setWeek(Integer.parseInt(row[1]));
        }

        if(!(row[2].equalsIgnoreCase("date"))) {
            String dateString = row[2];
            if (!dateString.equals("")) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
                LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);

                drawRecord.setDate(date);
            }
        }

        if (!(row[3].contains("hm")) && !(row[5].contains("hm"))
                && !(row[7].contains("hm")) && !(row[9].contains("hm"))) {
            setHitAndPrize(drawRecord, 5, Integer.parseInt(row[3]), row[4]);
            setHitAndPrize(drawRecord, 4, Integer.parseInt(row[5]), row[6]);
            setHitAndPrize(drawRecord, 3, Integer.parseInt(row[7]), row[8]);
            setHitAndPrize(drawRecord, 2, Integer.parseInt(row[9]), row[10]);
        }

        if(!(row[11].equalsIgnoreCase("n1"))
                && !(row[12].equalsIgnoreCase("n2"))
                && !(row[13].equalsIgnoreCase("n3"))
                && !(row[14].equalsIgnoreCase("n4"))
                && !(row[15].equalsIgnoreCase("n5"))) {
            int[] numbers = drawRecord.getNumbers();
            numbers[0] = Integer.parseInt(row[11]);
            numbers[1] = Integer.parseInt(row[12]);
            numbers[2] = Integer.parseInt(row[13]);
            numbers[3] = Integer.parseInt(row[14]);
            numbers[4] = Integer.parseInt(row[15]);
        }
        System.out.println(drawRecord);
        System.out.println();

        return drawRecord;
    }

    private static void setHitAndPrize(DrawRecordBean drawRecord, int numberOfHits, int hit, String prize) {
        switch (numberOfHits) {
            case 5:
                drawRecord.setHm5(hit);
                drawRecord.setHm5Prize(prize);
                break;
            case 4:
                drawRecord.setHm4(hit);
                drawRecord.setHm4Prize(prize);
                break;
            case 3:
                drawRecord.setHm3(hit);
                drawRecord.setHm3Prize(prize);
                break;
            case 2:
                drawRecord.setHm2(hit);
                drawRecord.setHm2Prize(prize);
        }
    }
}
