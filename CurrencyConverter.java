import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {
    public static void main(String[] args) {
        HashMap<Integer, String> currencyCodes = new HashMap<>();
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "HKD");
        currencyCodes.put(5, "INR");

        Scanner sc = new Scanner(System.in);
        String fromCode, toCode;
        double amount;

        System.out.println("Welcome to the Currency Converter!");
        System.out.println("Currency converting FROM?");
        System.out.println("1: USD  2: CAD  3: EUR  4: HKD  5: INR");
        fromCode = currencyCodes.get(sc.nextInt());

        System.out.println("Currency converting TO?");
        System.out.println("1: USD  2: CAD  3: EUR  4: HKD  5: INR");
        toCode = currencyCodes.get(sc.nextInt());

        System.out.print("Amount you wish to convert: ");
        amount = sc.nextDouble();

        try {
            sendHttpGETRequest(fromCode, toCode, amount);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }

        System.out.println("Thank you for using the Currency Converter!");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws Exception {
        DecimalFormat f = new DecimalFormat("00.00");

        String GET_URL = "https://api.exchangerate-api.com/v4/latest/" + fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());

            if (obj.has("rates")) {
                double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);
                double convertedAmount = amount * exchangeRate;

                System.out.println();
                System.out.println(f.format(amount) + " " + fromCode + " = " + f.format(convertedAmount) + " " + toCode);
                System.out.println("Exchange Rate: 1 " + fromCode + " = " + exchangeRate + " " + toCode);
            } else {
                System.out.println("Exchange rate data not found.");
            }
        } else {
            System.out.println("GET request failed. HTTP Response Code: " + responseCode);
        }
    }
}
