package pt.ulisboa.tecnico.meic.cmu.p2photo.api;

import android.util.Log;

import java.util.HashMap;

/**
 * Class for describing Address Resolution Protocol over Wi-Fi Direct P2Photo
 */
public class WiFiDARP {
    private static final String TAG = WiFiDARP.class.getName();
    private HashMap<String, String> mapping;

    public WiFiDARP() {
        this.mapping = new HashMap<>();
    }

    public void addEntry(String username, String ip) {
        synchronized (this.mapping) {
            Log.d(TAG, String.format("Added a new entry: User %s is at %s", username, ip));
            this.mapping.put(username, ip);
        }
    }

    public String resolve(String username) {
        String ip = this.mapping.get(username);
        Log.d(TAG, String.format("Where is %d? Is at %s", username, ip));
        return ip;
    }

    public void removeEntry(String username) {
        synchronized (this.mapping) {
            Log.d(TAG, String.format("Removed the entry for user %s", username));
            this.mapping.remove(username);
        }
    }

    public void removeAllEntries() {
        synchronized (this.mapping) {
            Log.d(TAG, "WiFi Direct P2Photo ARP Cache was reset!");
            this.mapping.clear();
        }
    }

}