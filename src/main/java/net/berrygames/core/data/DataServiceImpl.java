package net.berrygames.core.data;

import com.google.gson.JsonElement;
import net.berrygames.cloudberry.data.DataService;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataServiceImpl implements DataService {

    private final HttpClient client;

    public DataServiceImpl() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public JsonElement getPlayer(String name) throws IOException, InterruptedException, ParseException {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/player/" + name + "/"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Basic amVyZW15OlN1bm50aXRhbmUy")
                .GET()
                .build();
        var jsonParser = new JSONParser();
        var body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return (JsonElement) jsonParser.parse(body);
    }

    @Override
    public void updatePlayer(String data) {
    }
}
