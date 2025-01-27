package dmit2015.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherForecast {

    private String id;

    private LocalDate date;

    private int temperatureC;

    private String summary;

    public int getTemperatureF() {
        return (int) (32 + temperatureC / 0.5556);
    }
}
