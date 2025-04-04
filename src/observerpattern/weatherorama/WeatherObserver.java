package observerpattern.weatherorama;

@FunctionalInterface
interface WeatherObserver<T> {
    void update(T data);
}

@FunctionalInterface
interface WeatherDisplay {
    void display();
}

class CurrentConditionsDisplay implements WeatherObserver<WeatherData>, WeatherDisplay {
    private double temperature, humidity, pressure;
    private final WeatherData weatherData;

    CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObservers(this);
    }

    @Override
    public void update(WeatherData weatherData) {
        temperature = weatherData.getTemperature();
        humidity = weatherData.getHumidity();
        pressure = weatherData.getPressure();
        display();
    }

    @Override
    public void display() {
        System.out.println("Current Conditions: Temperature: " + temperature + " F, " +
                "Humidity: " + humidity + " %, " +
                "Pressure: " + pressure + " bars");
    }

}

class StatisticsDisplay implements WeatherObserver<WeatherData>, WeatherDisplay {
    private double maxTemp = Double.MIN_VALUE;
    private double minTemp = Double.MAX_VALUE;
    private final WeatherData weatherData;

    StatisticsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObservers(this);
    }

    @Override
    public void update(WeatherData weatherData) {
        maxTemp = Math.max(maxTemp, weatherData.getTemperature());
        minTemp = Math.min(minTemp, weatherData.getTemperature());
        display();
    }

    @Override
    public void display() {
        System.out.printf("Stats: Max=%.1f°F, Min=%.1f°F%n", maxTemp, minTemp);
    }

}

class ThirdPartyDisplay implements WeatherObserver<WeatherData>, WeatherDisplay {
    private final WeatherData weatherData;
    private double temperature;

    ThirdPartyDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObservers(this);
    }

    @Override
    public void update(WeatherData weatherData) {
        temperature = weatherData.getTemperature();
        display();
    }

    @Override
    public void display() {
        System.out.printf("Third Party Display: %.1f°F%n", temperature);
    }

}

class ForecastDisplay implements WeatherObserver<WeatherData>, WeatherDisplay {
    private double currentPressure, lastPressure;
    private final WeatherData weatherData;

    ForecastDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObservers(this);
    }

    @Override
    public void update(WeatherData weatherData) {
        lastPressure = currentPressure;
        currentPressure = weatherData.getPressure();
        display();
    }

    @Override
    public void display() {
        System.out.println("Last Pressure: " + lastPressure + ", Current Pressure: " + currentPressure);
    }

}
