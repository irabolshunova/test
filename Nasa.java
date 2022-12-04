import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Nasa {
    public static void main(String[] args) throws IOException {
        for (int day = 1; day <= 30; day++) {
            String page = downloadWebPage("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=2022-01-" + day);
            int urlBegin = page.lastIndexOf("url");
            int urlEnd = page.lastIndexOf("}");
            String url = page.substring(urlBegin + 6, urlEnd - 1);
            try (InputStream in = new URL(url).openStream()) {
                Files.copy(in, Paths.get("day" + day + ".jpg"));
            }
            System.out.println("day " + day + "saved");
        }
        System.out.println("Picture saved!");
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
