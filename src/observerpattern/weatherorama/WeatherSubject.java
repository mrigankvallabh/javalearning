package observerpattern.weatherorama;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

interface WeatherSubject {
    void registerObservers(WeatherObserver<WeatherData> o);

    void notifyObservers();

    void removeObservers(WeatherObserver<WeatherData> o);
}

class WeatherData implements WeatherSubject {
    private double temperature;
    private double humidity;
    private double pressure;
    private final Set<WeatherObserver<WeatherData>> wObservers = ConcurrentHashMap.newKeySet();

    WeatherData() {
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
        wObservers
                .forEach(obs -> obs.update(this));
    }

    @Override
    public void registerObservers(WeatherObserver<WeatherData> o) {
        wObservers.add(o);
    }

    @Override
    public void removeObservers(WeatherObserver<WeatherData> o) {
        wObservers.remove(o);
    }
}
