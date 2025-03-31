package chapterv201;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Locale;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.teeing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

class DownstreamCollectors {
    public record City(String name, String state, int population) {
    }

    static Stream<City> readCities(String fileOfCities) throws IOException {
        return Files
                .lines(Path.of(fileOfCities))
                .map(l -> l.split(", "))
                .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }

    public static void main(String[] args) throws IOException {
        var countryToLocaleSet = Locale
                .availableLocales()
                .collect(groupingBy(Locale::getCountry, toSet()));
        System.out.println(countryToLocaleSet);

        var countryAndLanguageToLocale = Locale
                .availableLocales()
                .collect(groupingBy(Locale::getCountry, groupingBy(Locale::getLanguage)));
        System.out.println("Hindi locales in India: " + countryAndLanguageToLocale.get("IN").get("hi"));

        var countryToLocaleCounts = Locale
                .availableLocales()
                .collect(groupingBy(Locale::getCountry, counting()));
        System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);

        var countryToLanguages = Locale
                .availableLocales()
                .collect(
                        groupingBy(
                                Locale::getDisplayCountry,
                                mapping(Locale::getDisplayLanguage, toSet())));
        System.out.println("countryToLanguages: " + countryToLanguages);

        var cities = readCities("resources/cities.txt");
        var stateToLongestCityName = cities
                .collect(
                        groupingBy(
                                City::state,
                                mapping(
                                        City::name,
                                        maxBy(Comparator.comparing(String::length)))));
        System.out.println("stateToLongestCityName: " + stateToLongestCityName);

        cities = readCities("resources/cities.txt");
        var stateToCityPopulationSummary = cities
                .collect(
                        groupingBy(City::state, summarizingInt(City::population)));
        System.out.println(stateToCityPopulationSummary.get("NY"));

        cities = readCities("resources/cities.txt");
        var stateToCityNames = cities
                .collect(
                        groupingBy(
                                City::state,
                                reducing(
                                        "",
                                        City::name,
                                        (s, t) -> s.length() == 0 ? t : s + ", " + t)));
        System.out.println("stateToCityNames:" + stateToCityNames);

        cities = readCities("resources/cities.txt");
        stateToCityNames = cities
                .collect(
                        groupingBy(
                                City::state,
                                mapping(City::name, joining(", "))));
        System.out.println("stateToCityNames:" + stateToCityNames);

        cities = readCities("resources/cities.txt");
        record Pair<S, T>(S first, T Second) {
        }

        var result = cities
                .filter(c -> c.state().equals("NV"))
                .collect(
                        teeing(
                                mapping(City::name, toList()),
                                averagingDouble(City::population),
                                (names, avg) -> new Pair<>(names, avg)));
        System.out.println(result);

    }
}
