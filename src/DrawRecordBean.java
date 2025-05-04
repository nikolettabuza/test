import java.time.LocalDate;
import java.util.Arrays;

public class DrawRecordBean {
    int year;
    int week;
    LocalDate date;
    int[] numbers = new int[5];
    int hm5;
    String hm5Prize;
    int hm4;
    String hm4Prize;
    int hm3;
    String hm3Prize;
    int hm2;
    String hm2Prize;

    public DrawRecordBean() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    public int getHm5() {
        return hm5;
    }

    public void setHm5(int hm5) {
        this.hm5 = hm5;
    }

    public String getHm5Prize() {
        return hm5Prize;
    }

    public void setHm5Prize(String hm5Prize) {
        this.hm5Prize = hm5Prize;
    }

    public int getHm4() {
        return hm4;
    }

    public void setHm4(int hm4) {
        this.hm4 = hm4;
    }

    public String getHm4Prize() {
        return hm4Prize;
    }

    public void setHm4Prize(String hm4Prize) {
        this.hm4Prize = hm4Prize;
    }

    public int getHm3() {
        return hm3;
    }

    public void setHm3(int hm3) {
        this.hm3 = hm3;
    }

    public String getHm3Prize() {
        return hm3Prize;
    }

    public void setHm3Prize(String hm3Prize) {
        this.hm3Prize = hm3Prize;
    }

    public int getHm2() {
        return hm2;
    }

    public void setHm2(int hm2) {
        this.hm2 = hm2;
    }

    public String getHm2Prize() {
        return hm2Prize;
    }

    public void setHm2Prize(String hm2Prize) {
        this.hm2Prize = hm2Prize;
    }

    @Override
    public String toString() {
        return ("year: " + year
                + ",\nweek: " + week
                + ", \ndate: " + date
                + ", \nwinning numbers: " + Arrays.toString(numbers)
                + ", \n5 hits, : " + hm5 + ", 5 hit prize: " + hm5Prize
                + ", \n4 hits: " + hm4 + ", : " + hm4Prize
                + ", \n3 hits, : " + hm3 + ", 3 hit prize: " + hm3Prize
                + ", \n2 hits: " + hm2 + ", 2 hit prize: " + hm2Prize);
    }
}
