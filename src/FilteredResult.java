import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FilteredResult extends JFrame {

    public static void main(String[] args) {

        // Create a list of objects that contain values by which we can filter
        ArrayList<FilterValues> filterValuesList = new ArrayList<>();
        addFilterListElementsToUI(filterValuesList);

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
}
