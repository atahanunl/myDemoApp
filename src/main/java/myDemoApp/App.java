package myDemoApp;

import java.util.ArrayList;

public class App {
    public static ArrayList<Integer> meaningfulMethod(int start, int end, ArrayList<Integer> data) {
        int temp1 = 0, temp2 = 1;
        int counter = 0;

        while (counter < end) {

            if (temp1 > start && temp1 < end)
                data.add(temp1);
            else if (temp1 >= end) {
                break;
            }

            int temp3 = temp2 + temp1;
            temp1 = temp2;
            temp2 = temp3;
            counter = counter + 1;
        }

        return data;
    }

    public static void main(String[] args) {

    }
}
