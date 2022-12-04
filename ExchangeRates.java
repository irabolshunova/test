import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ExchangeRates {
    public static void main(String[] args) throws IOException {
        String page1 = downloadWebPage("https://cbr.ru/scripts/XML_dynamic.asp?date_req1=01/11/2021&date_req2=13/11/2021&VAL_NM_RQ=R01235");
        int startIndex1 = page1.indexOf("<Value>");
        int endIndex1 = page1.indexOf("</Value>");
        String courseStr1 = page1.substring(startIndex1 +7, endIndex1);

        String page2 = downloadWebPage("https://cbr.ru/scripts/XML_dynamic.asp?date_req1=01/11/2020&date_req2=13/11/2020&VAL_NM_RQ=R01235");
        int startIndex2 = page2.indexOf("<Value>");
        int endIndex2 = page2.indexOf("</Value>");
        String courseStr2 = page2.substring(startIndex2 +7, endIndex2);

        System.out.println(courseStr1);
        System.out.println(courseStr2);

        double course1 = Double.parseDouble(courseStr1.replace(',','.'));
        double course2 = Double.parseDouble(courseStr2.replace(',','.'));

        if (course1 > course2) {
            System.out.println("Курс вырос на ");
            System.out.println(course1-course2);
        } else {
            System.out.println("Год назад курс был больше на");
            System.out.println(course2-course1);
        }


    }
    private static String downloadWebPage(String url) throws IOException {

        StringBuilder result = new StringBuilder();
        String line;


        URLConnection urlConnection = new URL(url).openConnection();
        try (InputStream is = urlConnection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }
}
