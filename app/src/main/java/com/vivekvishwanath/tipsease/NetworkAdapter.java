package com.vivekvishwanath.tipsease;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class NetworkAdapter {
    public static final String GET     = "GET";
    public static final String POST    = "POST";
    public static final String HEAD    = "HEAD";
    public static final String OPTIONS = "OPTIONS";
    public static final String PUT     = "PUT";
    public static final String DELETE  = "DELETE";
    public static final String TRACE   = "TRACE";
    public static final int TIMEOUT = 10000;

    static ArrayList<String> httpRequest(String urlString, String requestMethod, JSONObject requestBody, HashMap<String, String> headerProperties) {
        ArrayList<String> results = new ArrayList<>();
        String             result      = "";
        results.add(result);
        InputStream inputStream = null;
        HttpsURLConnection connection  = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod(requestMethod);

            if (headerProperties != null) {
                for (Map.Entry<String, String> property : headerProperties.entrySet()) {

                    connection.setRequestProperty(property.getKey(), property.getValue());
                }
            }

            // S03M03-10 add support for different types of request
            if((requestMethod.equals(POST) || requestMethod.equals(PUT)) && requestBody != null) {
                // S03M03-11 write body of post request
                connection.setDoInput(true);
                final OutputStream outputStream = connection.getOutputStream();
                String data = requestBody.toString();
                outputStream.write(data.getBytes());
                outputStream.close();
            } else {
                connection.connect();
            }

            final int responseCode = connection.getResponseCode();
            results.add(Integer.toString(responseCode));

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                if (inputStream != null) {
                    BufferedReader reader  = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder  builder = new StringBuilder();

                    String line;
                    do {
                        line = reader.readLine();
                        builder.append(line);
                    } while (line != null);
                    result = builder.toString();
                    results.set(0, result);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
        return results;
    }

    public static Bitmap getBitmapFromUrl(String stringUrl) {
        Bitmap result = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(stringUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);

            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = connection.getInputStream();
                if(stream != null) {
                    result = BitmapFactory.decodeStream(stream);
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }

            if(stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static int getResponseCode(String urlString, String requestMethod, JSONObject requestBody, HashMap<String, String> headerProperties) {
        String             result      = "";
        InputStream inputStream = null;
        HttpsURLConnection connection  = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod(requestMethod);

            if (headerProperties != null) {
                for (Map.Entry<String, String> property : headerProperties.entrySet()) {
                    connection.setRequestProperty(property.getKey(), property.getValue());
                }
            }

            final int responseCode = connection.getResponseCode();
            return responseCode;
        }  catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
        return 0;
    }


}