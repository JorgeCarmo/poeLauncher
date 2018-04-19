package sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GithubAPI {

    String link;
    String latestVersion;
    URL downloadURL;
    String fileName;

    public GithubAPI(String link) throws IOException {
        this.link = link;
        getValues();
    }

    public void getValues() throws IOException {
        URL url = new URL("https://api.github.com/repos/" + link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Scanner s = new Scanner(bufferedReader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        JsonObject jsonObj = new JsonParser().parse(result).getAsJsonObject();
        JsonArray jsonArray = jsonObj.getAsJsonArray("assets");
        JsonObject aux = jsonArray.get(0).getAsJsonObject();

        String name = aux.get("name").toString();
        String dUrl = aux.get("browser_download_url").toString();

        fileName = name.replace("\"", "");
        latestVersion = jsonObj.get("name").toString();
        downloadURL = new URL(dUrl.replace("\"", ""));

    }

    public String getLatestVersion(){
        return latestVersion;
    }

    public URL getDownloadURL(){
        return downloadURL;

    }


    public String getFileName(){
        return fileName;
    }

}
