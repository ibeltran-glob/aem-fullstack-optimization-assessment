package com.assessment.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.core.services.WeatherService;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Model( adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WeatherModel {

    private static final String DESCRIPTION = "description";

    private static final String TEMPERATURE = "temperature";

    private static final Logger LOG = LoggerFactory.getLogger(WeatherModel.class);


    @Inject
    private String city;

    @Inject
    private Page currentPage;
    
    @OSGiService
    private WeatherService weatherService;

    private String weatherJson;
    private String cityTemperature;
    
    private String cityDescription;
    
    
    @PostConstruct
    protected void init() {
        try {
            weatherJson = weatherService.getForecast(getCity());
            JsonObject jsonObject = JsonParser.parseString(weatherJson).getAsJsonObject();
            cityTemperature = (jsonObject.has(TEMPERATURE)) ? jsonObject.get(TEMPERATURE).getAsString() : "";
            cityDescription = (jsonObject.has(DESCRIPTION)) ? jsonObject.get(DESCRIPTION).getAsString() : "";
        } catch (Exception e) {
            LOG.error("Error on Weather model", e);
        }
    }

    public String getCity() {
        return city != null ? city : "Bogota";
    }

    public String getWeatherJson() {
        return weatherJson;
    }

    public String getPageTitle() {
        return currentPage != null ? currentPage.getTitle() : "Weather Page";
    }

    public String getDescription() {
        return cityDescription;
    }

    public String getTemperature() {
        return cityTemperature;
    }
}
