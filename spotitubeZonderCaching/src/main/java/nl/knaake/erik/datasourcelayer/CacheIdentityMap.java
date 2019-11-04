package nl.knaake.erik.datasourcelayer;

import nl.knaake.erik.crosscuttingconcerns.utils.DateConverter;

import java.util.*;

/*
* Houses all logic to cache any given combination of 2 datatypes
* Cleans the cache every interval
* When cleaning the cache only parts of that cache that (rounded to nearest 5 minute interval) are older then 5 minutes
* */
public class CacheIdentityMap<K, V> {

    private final static int CACHE_INVALIDATION_INTERVAL = 300000;
    private final static int MAX_CACHE_AGE_MINUTES = 5;

    //Het gebruik van Date leek problemen te geven, vandaar dat er tussen Date en String wordt geconverteerd
    private HashMap<String, HashMap<K, V>> map;
    private Class<?> k;
    private Class<?> v;

    private static LinkedList<CacheIdentityMap> instances = new LinkedList<>();

    /**
     * Gets a instance of a CacheIdentityMap for a given combination of datatypes
     * This can be done by reusing a instance that already corresponds to this set of datatypes or
     * by creating a new instance for the datatypes
     * @param keyType Datatype of the key
     * @param valueType Datatype of the value
     * @param <KeyType> Keytype that the CacheIdentityMap instance has, equals <code>keyType</code>
     * @param <ValueType> ValueType that the CacheIdentityMap instance has, equals <code>valueType</code>
     * @return Instance of CacheIdentityMap for the given datatypes
     */
    public static <KeyType, ValueType> CacheIdentityMap getInstance(Class<?> keyType, Class<?> valueType) {
        for(CacheIdentityMap bim : instances) {
            if(bim.k.equals(keyType) && bim.v.equals(valueType)) {
                return bim;
            }
        }
        CacheIdentityMap<KeyType, ValueType> newInstance = new CacheIdentityMap<KeyType, ValueType>(keyType, valueType);
        instances.add(newInstance);
        return newInstance;
    }

    /**
     * Initializes cach and its timer to clean the cache
     * @param k Keytype
     * @param v Valuetype
     */
    private CacheIdentityMap(Class<?> k, Class<?> v) {
        this.k = k;
        this.v = v;
        map = new HashMap<>();
        startTimer();
    }

    /**
     * Starts the timer that is used to clean the cache
     */
    private void startTimer() {
        Timer timerForInvalidatingOldCaches = new Timer();
        timerForInvalidatingOldCaches.scheduleAtFixedRate(new CacheInvalidator(), CACHE_INVALIDATION_INTERVAL, CACHE_INVALIDATION_INTERVAL);
    }

    /**
     * Task that is called periodically to clean the cache
     */
    class CacheInvalidator extends TimerTask {
        /**
         * Method that is called when cache needs cleaning, will forward it to <code>CacheIdentityMap</code>
         */
        @Override
        public void run() {
            cleanCache();
        }
    }

    /**
     * Cleans the cache
     * Only cleans cache parts that (rounded to nearest <code>MAX_CACHE_AGE_MINUTES</code> minutes interval)are older then <code>MAX_CACHE_AGE_MINUTES</code>
     */
    public void cleanCache() {
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, HashMap<K, V>> o = (Map.Entry<String, HashMap<K, V>>)it.next();
            Date currentKey = DateConverter.convertStringToDateTime(o.getKey());
            if(currentKey != null && currentKey.before(DateConverter.addMinutes(new Date(), -MAX_CACHE_AGE_MINUTES))) {
                it.remove();
                map.remove(o.getKey());
            }
        }
    }

    /**
     * Get the value that is stored in combination with the given key
     * @param key Identifier to get the value for
     * @return Value stored with teh key or null if no matching key is found
     */
    public V get(K key) {
        for (Map.Entry<String, HashMap<K, V>> o : map.entrySet()) {
            if(o != null) {
                HashMap<K, V> temp = o.getValue();
                V currentObject = temp.get(key);
                if(currentObject != null)
                    return currentObject;
            }
        }
        return null;
    }

    /**
     * Delete the key and its associated value
     * @param key Identifier to delete
     */
    public void remove(K key) {
        for (Map.Entry<String, HashMap<K, V>> o : map.entrySet()) {
            if(o != null)
                o.getValue().remove(key);
        }
    }

    /**
     * Puts a new entry into the cache
     * @param key Identifier for the value
     * @param value Value to store
     */
    public void put(K key, V value) {
        Set editableSet = getEditableSet();
        HashMap<K, V> values = editableSet.getValue();
        map.remove(editableSet.getKey());
        values.put(key, value);
        map.put(editableSet.getKey(), values);
    }

    /**
     * Update value on the key, if the key doesnt exist a new key is created
     * @param key key to insert
     * @param newValue value to insert with the key
     */
    public void update(K key, V newValue) {
        remove(key);
        put(key, newValue);
    }

    /**
     * @return Set - Set with the correct datekey, so the hashmap can be edited and then the set can be inserted into map
     */
    private Set getEditableSet() {
        String dateKey = getCurrentDateIntervalForMap();
        HashMap<K, V> editableMap = map.get(dateKey);
        if(editableMap == null) {
            editableMap = new HashMap<K, V>();
        }
        return new Set(dateKey, editableMap);
    }

    /**
     * Gets the current date interval as string
     * @return String - String representation of the current datetime rounded to the lowest interval defined by MAX_CACHE_AGE_MINUTES
     */
    private String getCurrentDateIntervalForMap() {
        return DateConverter.convertDateToDateTimeString(DateConverter.roundToNearestMinutes(new Date(), MAX_CACHE_AGE_MINUTES));
    }

    /**
     * Contains a String key (date) and a hashmap. The String key will be used for cleaning of the cache
     */
    private class Set {
        private String key;
        private HashMap<K, V> value;

        private Set(String key, HashMap<K, V> value) {
            this.key = key;
            this.value = value;
        }

        private String getKey() {
            return key;
        }

        private HashMap<K, V> getValue() {
            return value;
        }

    }

    /**
     * Get the number of instances
     * @return number of current instances of cachemaps
     */
    public static int getNumberOfInstances() {
        return instances.size();
    }

    /**
     * Clears all caches and delete existing instances
     */
    public static void clearInstances() {
        for(int i = getNumberOfInstances() - 1; i >= 0; i--) {
            instances.get(i).map.clear();
            instances.remove(i);
        }
    }

}
