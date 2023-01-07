package be.ehb.trends_littermeup;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

public class LatLngDeserializer extends JsonDeserializer<LatLng> {
    @Override
    public LatLng deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // Parse the JSON object in the JsonParser to get the latitude and longitude values
        JsonNode node = p.getCodec().readTree(p);
        double lat = node.get("latitude").doubleValue();
        double lng = node.get("longitude").doubleValue();

        // Return a new LatLng object with the latitude and longitude values
        return new LatLng(lat, lng);
    }
}

