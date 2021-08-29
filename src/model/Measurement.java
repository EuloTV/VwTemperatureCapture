package model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;

public class Measurement {

    private double temperatureKelvin;
    private double temperatureCelsius;
    private double temperatureFahrenheit;
    private LocalDateTime pointInTime;
    private SimpleStringProperty pointInTimeString = new SimpleStringProperty();
    private SimpleStringProperty temperatureKelvinString = new SimpleStringProperty();
    private SimpleStringProperty temperatureCelsiusString = new SimpleStringProperty();
    private SimpleStringProperty temperatureFahrenheitString = new SimpleStringProperty();
    private static DecimalFormat format = new DecimalFormat("#0.00");
    public static final int KELVIN = 1;
    public static final int CELSIUS = 2;
    public static final int FAHRENHEIT = 3;

    public Measurement(LocalDate date, int hour, int minute) {
        this.setPointInTime(date, hour, minute);
    }

    public double getTemperatureKelvin() {
        return this.temperatureKelvin;
    }

    public void setTemperatureKelvin(String temperatureKelvin) {
        this.temperatureKelvin = Double.parseDouble(temperatureKelvin);
        setTemperatureKelvinString();
    }

    public double getTemperatureCelsius() {
        return this.temperatureCelsius;
    }

    public void setTemperatureCelsius(String temperatureCelsius) {
        this.temperatureCelsius = Double.parseDouble(temperatureCelsius);
        setTemperatureCelsiusString();
    }

    public double getTemperatureFahrenheit() {
        return this.temperatureFahrenheit;
    }

    public void setTemperatureFahrenheit(String temperatureFahrenheit) {
        this.temperatureFahrenheit = Double.parseDouble(temperatureFahrenheit);
        setTemperatureFahrenheitString();
    }

    public void kelvinToCelsius(double temperatureKelvin) {

        this.temperatureCelsius = temperatureKelvin - 273.15;
        setTemperatureCelsiusString();
    }

    public void kelvinToFahrenheit(double temperatureKelvin) {

        this.temperatureFahrenheit = temperatureKelvin * (9 / 5) - 459.67;
        setTemperatureFahrenheitString();
    }

    public void celsiusToKelvin(double temperatureCelsius) {

        this.temperatureKelvin = temperatureCelsius + 273.15;
        setTemperatureKelvinString();
    }

    public void fahrenheitToKelvin(double temperatureFahrenheit) {

        this.temperatureKelvin = (temperatureFahrenheit + 459.67) * (5 / 9);
        setTemperatureKelvinString();
    }

    public void setTemperatureKelvinString() {
        this.temperatureKelvinString.setValue(format.format(this.temperatureKelvin));
    }

    public void setTemperatureCelsiusString() {
        this.temperatureCelsiusString.setValue(format.format(this.temperatureCelsius));
    }

    public void setTemperatureFahrenheitString() {
        this.temperatureFahrenheitString.setValue(format.format(this.temperatureFahrenheit));
    }

    public LocalDateTime getPointInTime() {
        return this.pointInTime;
    }

    private void setPointInTime(LocalDate date, int hour, int minute) {
        this.pointInTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
        setPointInTimeString();
    }

    public String timeToString(int time) {

        String output = "" + time;

        if (output.length() < 2) {
            output = "0" + output;
        }
        return output;

    }

    public String timeToString2(int time) {

        if (time < 10) {
            return "0" + time;
        } else {
            return "" + time;
        }

    }

    public void setPointInTimeString() {

        String day = timeToString(getPointInTime().getDayOfMonth());
        String month = timeToString(getPointInTime().getMonthValue());
        String year = timeToString(getPointInTime().getYear());
        String hour = timeToString(getPointInTime().getHour());
        String minute = timeToString(getPointInTime().getMinute());

        this.pointInTimeString = new SimpleStringProperty(
                String.format("%s.%s.%s um %s:%s Uhr", day, month, year, hour, minute));

    }

    public String getPointInTimeString() {
        return pointInTimeString.get();
    }

    public void setPointInTimeString(SimpleStringProperty pointInTimeString) {
        this.pointInTimeString = pointInTimeString;
    }

    public String getTemperatureKelvinString() {
        return temperatureKelvinString.get();
    }

    public void setTemperatureKelvinString(SimpleStringProperty temperatureKelvinString) {
        this.temperatureKelvinString = temperatureKelvinString;
    }

    public String getTemperatureCelsiusString() {
        return temperatureCelsiusString.get();
    }

    public void setTemperatureCelsiusString(SimpleStringProperty temperatureCelsiusString) {
        this.temperatureCelsiusString = temperatureCelsiusString;
    }

    public String getTemperatureFahrenheitString() {
        return temperatureFahrenheitString.get();
    }

    public void setTemperatureFahrenheitString(SimpleStringProperty temperatureFahrenheitString) {
        this.temperatureFahrenheitString = temperatureFahrenheitString;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPointInTimeString(), getTemperatureKelvinString());
    }

}
