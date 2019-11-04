package nl.knaake.erik.datasourcelayer;

import nl.knaake.erik.crosscuttingconcerns.dtos.SessionExpirationDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.TrackCollectionDTO;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;

public class CacheFactory {

    private static CacheFactory instance = new CacheFactory();
    private CacheManager cacheManager;

    private CacheFactory() {

        URL url = getClass().getClassLoader().getResource("cache-config");
        Configuration config = new XmlConfiguration(url);
        cacheManager = CacheManagerBuilder.newCacheManager(config);
        cacheManager.init();
    }

    public static CacheFactory getInstance() {
        return instance;
    }

    public Cache createCache(CacheType type) {
        switch (type) {
            case SESSION:
                return cacheManager.getCache("session", String.class, SessionExpirationDTO.class);
            case TrackInPlaylist:
                return cacheManager.getCache("track-in-playlist", Integer.class, TrackCollectionDTO.class);
        }
        return null;
    }

    public enum CacheType {
        SESSION,
        TrackInPlaylist
    }
}
