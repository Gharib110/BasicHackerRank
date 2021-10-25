package ir.alirezagharib.hackerrank;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.*;

class Result {

    /*
     * Complete the 'getTotalGoals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING team
     *  2. INTEGER year
     */

    public static int getTotalGoals(String team, int year)  {
        final String EndPoint = "https://jsonmock.hackerrank.com/api/football_matches";

        int totalGoalsAtHome = 0, totalGoalsAtVisiting = 0;
        try{
            totalGoalsAtHome = getPageTotalGoals( String.format(EndPoint + "?team1=%s&year=%d", URLEncoder.encode(team), year), "team1", 1);
            totalGoalsAtVisiting = getPageTotalGoals( String.format(EndPoint + "?team2=%s&year=%d", URLEncoder.encode(team), year), "team2", 1);

        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return totalGoalsAtHome + totalGoalsAtVisiting;
    }

    private static int getPageTotalGoals(String request, String team, int page) throws IOException, ScriptException {
        URL url = new URL(request + "&page=" + page);

        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();

        httpUrl.setRequestMethod("GET");
        httpUrl.setConnectTimeout(5000);
        httpUrl.setReadTimeout(5000);

        httpUrl.setRequestProperty("Content-Type", "application/json");
        int status = httpUrl.getResponseCode();

        InputStream in = (status < 200 || status > 200) ? httpUrl.getErrorStream() : httpUrl.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String responseline;
        StringBuffer responseContent = new StringBuffer();
        while ((responseline = br.readLine()) != null){
            responseContent.append(responseline);
        }

        br.close();

        httpUrl.disconnect();

        ScriptEngineManager manager =   new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        String script = "var obj = JSON.parse('" + responseContent.toString() + "');";
        script += "var total_pages = obj.total_pages;";
        script += "var total_goals  = obj.data.reduce(function(accumulator, current) {return accumulator + parseInt(current."
                + team + "goals);}, 0);";

        engine.eval(script);
        if (engine.get("total_pages") == null ) {
            throw new RuntimeException("Cannot retrieve data from server.");
        }

        int totalPages = (int) engine.get("total_pages");
        int totalGoals = (int) Double.parseDouble(engine.get("total_goals").toString());

        return (page < totalPages) ? getPageTotalGoals(request, team, page + 1) : totalGoals;
    }

}


//class Result {
//
//    /*
//     * Complete the 'getNumDraws' function below.
//     *
//     * The function is expected to return an INTEGER.
//     * The function accepts INTEGER year as parameter.
//     */
//
//    public static int getNumDraws(int year) throws IOException {
//        final String EndPoint = "https://jsonmock.hackerrank.com/api/football_matches?year=" + year;
//        final int MaxScore = 10;
//
//        int TotalNumDraws = 0;
//        for (int score = 0; score <= MaxScore; score++) {
//            TotalNumDraws += getTotalNumDraws(String.format(EndPoint + "&team1goals=%d&team2goals=%d", score, score));
//        }
//    }
//
//    private static int getTotalNumDraws(String request) throws IOException {
//        URL url = new URL(request);
//
//        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
//
//        httpUrl.setRequestMethod("GET");
//        httpUrl.setConnectTimeout(5000);
//        httpUrl.setReadTimeout(5000);
//
//        httpUrl.setRequestProperty("Content-Type", "application/json");
//        int status = httpUrl.getResponseCode();
//
//        InputStream in = (status < 200 || status > 200) ? httpUrl.getErrorStream() : httpUrl.getInputStream();
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//        String responseline;
//        StringBuffer responseContent = new StringBuffer();
//        while ((responseline = br.readLine()) != null){
//            responseContent.append(responseline);
//        }
//
//        br.close();
//
//        httpUrl.disconnect();
//
//        ScriptEngineManager manager =   new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("javascript");
//
//        String script = "var obj = JSON.parse('" + responseContent.toString() + "');";
//        script += "var total = obj.total;";
//
//        try {
//            engine.eval(script);
//        } catch (ScriptException ex) {
//            ex.printStackTrace();
//        }
//
//        if (engine.get("total") == null) {
//            throw new RuntimeException("Cannot retrieve data from the server");
//        }
//
//        return (int) engine.get("total");
//    }
//
//}
