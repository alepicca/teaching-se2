/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.timer.business.weatherclient;

import it.polimi.timer.business.weather.entity.Forecast;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author miglie
 */
@Singleton
public class WeatherChecker {

    private static final Logger logger = Logger.getLogger(WeatherChecker.class.getName());
    
    private Client client;
    
    @PostConstruct
    private void constructed() {
        logger.log(Level.INFO, "WeatherChecker created");
        client = ClientBuilder.newClient();
    }

    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void checkWeather() {
        logger.log(Level.INFO, "{0}: checking the weather", new Date());
        Forecast forecast = client.target("http://localhost:8080/weather/rest/forecast")
                .request(MediaType.APPLICATION_JSON)
                .get(Forecast.class);
        logger.log(Level.INFO, "Oracle says: {0}", forecast.getResult());
    }

}