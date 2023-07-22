package com.t.listmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkService {
    private NetworkService() {
        throw new IllegalStateException("NetworkService class");
    }
    
    public static boolean getConnectivityStatus(Context mContext) {
        boolean result = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                    result = true;
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    result = true;
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
                    result = true;
            }
        }

        return result && isInternetAvailable();
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.toString().equals("");
        } catch (UnknownHostException ignored) {}
        return false;
    }
}
