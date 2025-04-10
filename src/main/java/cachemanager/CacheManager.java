package cachemanager;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    //Implementacion de cache local
    private static final long EXPIRATION_TIME_MS = 60000; // 1 minuto
    private Map<String, Float> cache = new HashMap<>();
    private Map<String, Long> timestamps = new HashMap<>();

    public Float getRateFromCache(String currency) {
        if (cache.containsKey(currency)) {
            long storedTime = timestamps.get(currency);
            if (System.currentTimeMillis() - storedTime < EXPIRATION_TIME_MS) {
                return cache.get(currency); // Retornar desde caché
            } else {
                cache.remove(currency); // Expirar entrada
                timestamps.remove(currency);
            }
        }
        return null; // No disponible en caché
    }

    public void updateCache(String currency, Float rate) {
        cache.put(currency, rate);
        timestamps.put(currency, System.currentTimeMillis()); // Almacenar timestamp
    }

}
