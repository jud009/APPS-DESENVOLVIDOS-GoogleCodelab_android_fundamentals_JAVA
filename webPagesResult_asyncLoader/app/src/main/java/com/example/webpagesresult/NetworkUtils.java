package com.example.webpagesresult;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static HttpURLConnection m_connection;
    private static BufferedReader reader;

    private static String DATA_RESULT = null;


    public static String getWebSiteData(String siteUrl){

        try {
            Uri uri = Uri.parse(siteUrl);
            URL url = new URL(uri.toString());

            m_connection = (HttpURLConnection) url.openConnection();
            m_connection.setRequestMethod("GET");
            m_connection.connect();

            InputStream inputStream = m_connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String readerLine;

            StringBuilder sb = new StringBuilder();

            while( (readerLine = reader.readLine()) != null){
                sb.append(readerLine);
                sb.append("\n");
            }

            if (sb.length() == 0){
                return null;
            }else{
                DATA_RESULT = sb.toString();
            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (m_connection != null){
                m_connection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return DATA_RESULT;
    }

}
