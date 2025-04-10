package converterlogic;
import cachemanager.CacheManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverterLogic {
    private CacheManager cacheManager = new CacheManager(); // Caché

    private static final String API_KEY = "TU_API_KEY_AQUÍ"; // Clave de la API
    private static final String BASE_API_URL = "https://api.exchangerate-api.com/v4/latest/COP";

    // Obtener tasa de cambio desde la caché o la API
    public float getExchangeRate(String currency) throws Exception {
        Float rate = cacheManager.getRateFromCache(currency);
        if (rate != null) {
            System.out.println("Usando tasa desde caché: " + rate);
            return rate; // Retorna desde caché
        }
        // Llamada a la API
        String apiUrl = BASE_API_URL + "?apikey=" + API_KEY;
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 429) {
            throw new Exception("Límite de consultas alcanzado.");
        } else if (conn.getResponseCode() != 200) {
            throw new Exception("Error de la API. Código: " + conn.getResponseCode());
        }

        // Leer respuesta
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parsear JSON
        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        if (currency.equals("Euro")) {
            rate = jsonObject.getAsJsonObject("rates").get("EUR").getAsFloat();
        } else if (currency.equals("Dolar")) {
            rate = jsonObject.getAsJsonObject("rates").get("USD").getAsFloat();
        } else {
            throw new IllegalArgumentException("Moneda no soportada: " + currency);
        }

        // Actualizar caché
        cacheManager.updateCache(currency, rate);
        System.out.println("Actualizando caché con nueva tasa: " + rate);

        return rate;
    }

}
