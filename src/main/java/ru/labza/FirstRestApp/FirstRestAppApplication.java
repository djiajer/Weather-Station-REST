package ru.labza.FirstRestApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.labza.FirstRestApp.dto.SensorDTO;
import ru.labza.FirstRestApp.models.Sensor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class FirstRestAppApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(FirstRestAppApplication.class, args);

		RestTemplate restTemplate = new RestTemplate();
		String value;
		String raining;
		Map<String, String> sensor = new HashMap<>();
		sensor.put("name", "sensor1");
		Random random = new Random();

		for (int i = 0; i < 100; i++) {
			value = String.valueOf((random.nextInt(200) - 100));
			raining = String.valueOf(random.nextInt(2));
			switch (raining) {
				case "0" -> raining = "true";
				case "1" -> raining = "false";
			}
			Map<String, Object> jsonToSend = new HashMap<>();
				jsonToSend.put("value", value);
				jsonToSend.put("raining", raining);
				jsonToSend.put("sensor", sensor);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonToSend);
			restTemplate.postForObject("http://localhost:8080/measurements/add", request, String.class);
		}



	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
