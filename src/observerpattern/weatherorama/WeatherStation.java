package observerpattern.weatherorama;

class WeatherStation {
    public static void main(String[] args) {
        var weatherData = new WeatherData();
        new CurrentConditionsDisplay(weatherData);
        new ForecastDisplay(weatherData);
        new StatisticsDisplay(weatherData);
        new ThirdPartyDisplay(weatherData);
        weatherData.setMeasurements(82.6, 78, 1.08);
        weatherData.setMeasurements(88.2, 76, 1.05);
        weatherData.setMeasurements(85.3, 76, 1.08);
    }
}
