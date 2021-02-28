package com.mrina.btctracker;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.NumberFormat;
import java.util.Locale;

@SpringBootApplication
public class BtcTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtcTrackerApplication.class, args);
		// Define an endpoint for getting JSON data as a string
		String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		// Create a WebClient instance
		WebClient webClient = WebClient.create();

		// Use the WebClient to make a request processing it as a string
		String response = webClient.get()
		.uri(url)
		.retrieve()
		.bodyToMono(String.class)
		.block();

		// Print the response on the console
		System.out.println("web request response: " + response);

		// Create a new JSON object from the response above
		JSONObject jsonObject = new JSONObject(response);

		// Get the JSON object which is the USD bitcoin price
		Float rateFloat = jsonObject
				.getJSONObject("bpi")
				.getJSONObject("USD")
				.getFloat("rate_float");

		// We are formatting into something easier to read using the NumberFormat package
		String btcValueInUSD = NumberFormat
				.getCurrencyInstance(Locale.US)
				.format(rateFloat);

		System.out.println("1 Bitcoin is worth " + btcValueInUSD);
	}


}
