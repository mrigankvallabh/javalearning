package observerpattern.weatherorama;

interface WeatherObserver {
    void update();
}

interface WeatherDisplay {
    void display();
}

class CurrentConditionsDisplay implements WeatherObserver, WeatherDisplay {
    private double temperature, humidity, pressure;
    private final WeatherData weatherData;

    CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObservers(this);
    }

    @Override
    public void update() {
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

class StatisticsDisplay implements WeatherObserver, WeatherDisplay {

    @Override
    public void update() {
        
    }

    @Override
    public void display() {
        
    }
    
}

class ThirdPartyDisplay implements WeatherObserver, WeatherDisplay {

    @Override
    public void update() {
        
    }

    @Override
    public void display() {
        
    }
    
}

class ForecastDisplay implements WeatherObserver, WeatherDisplay {
    private double currentPressure, lastPressure;
    private final WeatherData weatherData;

    ForecastDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        this.weatherData.registerObservers(this);
    }

    @Override
    public void update() {
        lastPressure = currentPressure;
        currentPressure = weatherData.getPressure();
        display();
    }

    @Override
    public void display() {
        System.out.println("Last Pressure: " + lastPressure + ", Current Pressure: " + currentPressure);
    }
   
}
