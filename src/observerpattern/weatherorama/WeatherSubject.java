package observerpattern.weatherorama;

import java.util.List;
import java.util.ArrayList;

interface WeatherSubject {
    void registerObservers(WeatherObserver o);

    void notifyObservers();

    void removeObservers(WeatherObserver o);
}

class WeatherData implements WeatherSubject {
    private final List<WeatherObserver> wObservers;
    private double temperature;
    private double humidity;
    private double pressure;

    WeatherData() {
        wObservers = new ArrayList<>();
    }

    WeatherData(double temperature, double humidity, double pressure) {
        this();
        setMeasurements(temperature, humidity, pressure);
    }

    double getTemperature() {
        return temperature;
    }

    double getHumidity() {
        return humidity;
    }

    double getPressure() {
        return pressure;
    }

    void setMeasurements(double temperature, double humidity, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (var o : wObservers) {
            o.update();
        }
    }

    @Override
    public void registerObservers(WeatherObserver o) {
        wObservers.add(o);
    }

    @Override
    public void removeObservers(WeatherObserver o) {
        wObservers.remove(o);
    }
}
