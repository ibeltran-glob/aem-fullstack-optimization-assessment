package com.assessment.core.services.impl;

import com.assessment.core.services.WeatherService;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = WeatherService.class, immediate = true)
@Designate(ocd=WeatherServiceImpl.Config.class)
public class WeatherServiceImpl implements WeatherService {

    private Config configuration;

    @ObjectClassDefinition(name="weather API Configuration")
    public static @interface Config {

        @AttributeDefinition(name = "Endpoint",description = "URL to to use for connecting")
        String url() default "";
        
        @AttributeDefinition(name = "API Key",description = "API KEy to use for connecting")
        String key() default "";
    }

    @Activate
    protected void activate(final Config config) {
        this.configuration = config;
    }

    @Override
    public String getForecast(String city) throws Exception {
        if ("".equals(configuration.url()) || "".equals(configuration.key()))
            return "{}";
        String urlString = configuration.url() + "%s?apikey=%s";
        URL url = new URL(String.format(
                urlString,
                URLEncoder.encode(city, StandardCharsets.UTF_8),
                configuration.key()));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
