package myDemoApp;

import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

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
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hello, World");

        post("/compute", (req, res) -> {
            System.out.println(req.queryParams("input1"));
            System.out.println(req.queryParams("input2"));
            System.out.println(req.queryParams("input3"));

            String input1 = req.queryParams("input1").replaceAll("\\s","");
            int parsedInput1 = Integer.parseInt(input1);

            String input2 = req.queryParams("input2").replaceAll("\\s","");
            int parsedInput2 = Integer.parseInt(input2);

            String input3 = req.queryParams("input3");
            /* as the third input is not needed
            java.util.Scanner sc1 = new java.util.Scanner(input3);
            sc1.useDelimiter("[;\r\n]+");
             */
            java.util.ArrayList<Integer> parsedInput3 = new java.util.ArrayList<>();
            /* as the third input is not needed
            while (sc1.hasNext())
            {
                int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
                parsedInput3.add(value);
            }
            sc1.close();
             */

            ArrayList<Integer> result = App.meaningfulMethod(parsedInput1, parsedInput2, parsedInput3);

            Map<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();

            map.put("result", result);

            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
                (rq, rs) -> {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("result", "not computed yet!");
                    return new ModelAndView(map, "compute.mustache");
                },
                new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
