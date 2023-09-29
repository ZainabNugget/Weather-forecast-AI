package com.chatbot.chatbot;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class Chatbot {

	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	public void run() {
		try {

			String resourcesPath = getResourcesPath();
			System.out.println(resourcesPath);
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcesPath);
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();
			String textLine = "";

			/*
			 * 
			 * ChatBot action happens here
			 * 
			 */
			while (true) {
				System.out.print("You : "); //change to you
				textLine = IOUtils.readInputTextLine(); //userInput
				if ((textLine == null) || (textLine.length() < 1))
					textLine = MagicStrings.null_input; //user input
				if (textLine.equals("q")) {
					System.exit(0);
				} else if (textLine.equalsIgnoreCase("bye")) {//change to bye
					bot.writeQuit();
					System.exit(0);
				} else {
					/*
					 * The text from the user is taken in
					 * and a response is issued.
					 * 
					 */
					String request = textLine; //user input
					String response ="";
					if(request.contains("London")||request.contains("london")) { //will be changed next review.
						response = whichWeather(London()); //testing whether input works
					} else if(request.contains("Abuja")||request.contains("abuja")) { //will be changed next review.
						response = whichWeather(Abuja()); //testing whether input works
					} else if(request.contains("Chicago")||request.contains("chicago")) { //will be changed next review.
						response = whichWeather(Chicago()); //testing whether input works
					} else {
						response = response(textLine, chatSession);//general chatbot convo, from programAb resource
					}
					System.out.println("Franky : " + response); //name of bot + the response
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 
	 * 
	 * Added London method gets the Weather specific to London
	 * 
	 * 
	 */
	protected String London() throws IOException, InterruptedException {
		/*
		 * Will load slowly or fast based on the internet
		 */
		HttpRequest HTTPrequest = HttpRequest.newBuilder()
				.uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=London"))
				.header("X-RapidAPI-Key", "766f87ad1bmshc810b80aac1626dp1d5940jsn70d2e349711c")
				.header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> HTTPresponse = HttpClient.newHttpClient().send(HTTPrequest, HttpResponse.BodyHandlers.ofString());

		//Store all information in an arraylist
		ArrayList<String> City = new ArrayList<>(Arrays.asList(HTTPresponse.body().split(",")));
		String[] trim = new String[2];//stores the string
		String temp_c = ""; //string to store temp_c
		String weather_condition = ""; //string to store desc

		for(String i : City) { //goes through info gathered from the API
			if(i.contains("temp_c")) {
				trim = i.split(":");//splits into two parts
				temp_c = trim[1];//trims any whitespace

			} else if(i.contains("text")) {
				trim = i.split(":");
				weather_condition = trim[2].replace('"',' ');//splits into two parts
				weather_condition = weather_condition.trim();//trims any whitespace
				//get the weather_condition

			}

		}
		System.out.println(weather_condition); //testing purposes
		return temp_c + "," + weather_condition; //returns a string with the 2 weather conditions
	}

	protected String Chicago() throws IOException, InterruptedException {

		HttpRequest HTTPrequest = HttpRequest.newBuilder()
				.uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=Chicago"))
				.header("X-RapidAPI-Key", "766f87ad1bmshc810b80aac1626dp1d5940jsn70d2e349711c")
				.header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> HTTPresponse = HttpClient.newHttpClient().send(HTTPrequest, HttpResponse.BodyHandlers.ofString());

		ArrayList<String> City = new ArrayList<>(Arrays.asList(HTTPresponse.body().split(",")));

		String temp_c = ""; 
		String desc = "";
		for(String i : City) {
			if(i.contains("temp_c")) {
				String[] trim = new String[2];
				trim = i.split(":");
				temp_c = trim[1];

			} else if(i.contains("text")) {
				String[] trim = new String[2];
				trim = i.split(":");
				desc = trim[2].replace('"',' ');
				desc = desc.trim();

			}  

		}
		System.out.println(desc); //testing purposes
		return  temp_c + "," + desc;

	}

	protected String Abuja() throws IOException, InterruptedException {

		HttpRequest HTTPrequest = HttpRequest.newBuilder()
				.uri(URI.create("https://weatherapi-com.p.rapidapi.com/current.json?q=Abuja"))
				.header("X-RapidAPI-Key", "766f87ad1bmshc810b80aac1626dp1d5940jsn70d2e349711c")
				.header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> HTTPresponse = HttpClient.newHttpClient().send(HTTPrequest, HttpResponse.BodyHandlers.ofString());
		//code to print out the whole response
		//System.out.println(HTTPresponse.body());
		ArrayList<String> City = new ArrayList<>(Arrays.asList(HTTPresponse.body().split(",")));

		String temp_c = ""; //string to 
		String condition = "";
		for(String i : City) {
			if(i.contains("temp_c")) {
				String[] trim = new String[2];
				trim = i.split(":");
				temp_c = trim[1];
				//System.out.println(temp_c);
			} else if(i.contains("text")) {
				String[] trim = new String[2];
				trim = i.split(":");
				condition = trim[2].replace('"',' ');
				condition = condition.trim();
				//System.out.println(desc);
			}
		}
		System.out.println(condition); //testing purposes
		return temp_c + "," + condition;
	}
	/*
	 * 
	 * 
	 * Create a method that shows which weather condition method
	 * to choose, based on temperature or weather condition
	 * 
	 * 
	 */
	protected String whichWeather(String weather) {
		//Temp and desc
		//Switch statement of all weather desc 
		String[] trim = new String[2];
		trim = weather.split(","); //split the information
		double temp_c = Double.parseDouble(trim[0]); //change to double so we can manipulate
		String weather_condition = trim[1];

		if(weather_condition.contains("Clear")|| weather_condition.contains("clear")||weather_condition.contains("Sunny")) {
			return clear();
		} else if(weather_condition.contains("Rain")|| weather_condition.contains("rain")||weather_condition.contains("Drizzle")||weather_condition.contains("drizzle")) {
			return Rainy();
		} else if (weather_condition.contains("Cloudy")|| weather_condition.contains("cloudy")) {
			return partlyCloudy();
		} else if (weather_condition.contains("Snow")|| weather_condition.contains("snow")) {
			return Snow();
		} else if (weather_condition.contains("overcast")|| weather_condition.contains("Overcast")) {
			return overcast();
		}
		//idea: create a txt file that contains all the weather conditions
		return null;

	}

	//clear method
	protected String clear(){
		String weather = " on a clear weather day it is not too cold and it is not too hot, here are a few clothing selection"
				+"\nClothing Materials: cotton, jersey, denim (with light color fabrics)"
				+"\nGeneral description: short sleeves/sweat shirt; No tight/ constraining clothes;"
				+"\nShoes: comfortable shoes like sneakers. "
				+"\nFemale Clothings examples may be: Tops:  off-shoulder blouses; puff-sleeve blouses; Short-sleeve button-ups; unlined skirts; dresses"
				+"\nMale Clothings examples are very simple as no many options:t-shirts; buttoned t- shirts; shorts knickers; light cargos; sweat pants; ";
		return weather;
	}

	//sunny method
	protected String sunny(){
		String weather = " on a sunny weather day it is usually hot, here are a few clothing selection"
				+"\nClothing Materials: cotton, jersey, denim (with light color fabrics)"
				+"\nGeneral description: short sleeves/ sleeveless shirt; No tight/ constraining clothes;"
				+"\nShoes: breatheable and comfortable shoes like sandals or sneakers. "
				+"\nFemale Clothings examples may be: Tops: crop-top, accessories like glasses; A hat; sleeveless camis;"
				+"\nMale Clothings examples are very simple as no many options:sleeveless t-shirts; buttoned t- shirts; shorts; knickers; light cargos; sweat pants; ";
		return weather;
	}


	protected String partlyCloudy() {
		String condition = "The weather is a bit chilly, possiblities of it raining is high"
				+ "/n Advisable to carry an Umberella or a Rain coat"
				+ "/n Clothes to wear: Hoodies, Jumpers, Sweaters, Coats, Fleeces"
				+ "/n Shoes: Boots and trainers";
		return condition;

	}
	protected String Rainy() {
		String condition = "The weather is windy"
				+ "/n Advisable to carry an Umberella or a Rain coat"
				+ "/n Clothes to wear: Hooded Jackets, Jumpers, Sweaters, Coats, Fleeces"
				+ "/n Shoes: Shoes that have leather soles as they ensure a firm grip";
		return condition;

	}
	protected String Snow() {
		String condition = "It is a bit slippery"
				+ "/n Advisable to carry an Umberella or a Rain coat"
				+ "/n Clothes to wear: Hooded Jackets, Jumpers, Sweaters, Coats, Fleeces"
				+ "/n Shoes: Shoes that have leather soles as they ensure a firm grip";
		return condition;

	}
	protected String overcast() {
		String condition = "It is overcast, meaning it could rain at any moment!"
				+ "/nPlease dont forget your raincoat!" ;
		return condition;

	}


	//takes in the tempurature and returns how cold/hot
	//incomplete method (for now)
	protected String temperature(double a) { 
		//can later replace with methods/clothing requirements
		if(a < 10) {
			return "freezing";
		} else if (a >= 10 && a < 17) {
			return "cold";
		} else if (a >= 17 && a < 25) {
			return "chilly";
		} else if (a >= 25 && a < 30) {
			return "warm";
		} else if (a >= 30) {
			return "hot";
		}
		return null;

	}

	/*
	 * 
	 * 
	 * Response from saved AIML files
	 * Stored in response method for simplicity
	 * Similarly, getResourcesPath was also generated for simplicity
	 * 
	 * 
	 */
	private static String response(String textLine, Chat chatSession) {
		String request = textLine;
		if (MagicBooleans.trace_mode)
			System.out.println(
					"STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
					+ ":TOPIC=" + chatSession.predicates.get("topic"));
		String response = chatSession.multisentenceRespond(request);
		while (response.contains("&lt;"))
			response = response.replace("&lt;", "<");
		while (response.contains("&gt;"))
			response = response.replace("&gt;", ">");
		return response;
	}

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

}

