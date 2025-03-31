package chapterv201;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CollectingIntoMaps {
    record Person(int id, String name) {
    }

    static Stream<Person> persons() {
        return Stream.of(
                new Person(1, "John"),
                new Person(2, "Jane"),
                new Person(3, "Jack"));
    }

    public static void main(String[] args) {
        var idToName = persons()
                .collect(Collectors.toMap(
                        Person::id,
                        Person::name));
        System.out.println(idToName);

        var idToPerson = persons()
                .collect(Collectors.toMap(
                        Person::id,
                        Function.identity()));
        System.out.println(idToPerson.getClass().getSimpleName() + ": " + idToPerson);

        idToPerson = persons()
                .collect(Collectors.toMap(
                        Person::id,
                        Function.identity(),
                        (_, _) -> {
                            throw new IllegalStateException();
                        },
                        TreeMap::new));
        System.out.println(idToPerson.getClass().getSimpleName() + ": " + idToPerson);

        var languages = Locale
                .availableLocales()
                .collect(Collectors.toMap(
                        Locale::getDisplayLanguage,
                        lcl -> lcl.getDisplayLanguage(lcl),
                        (xv, nv) -> xv.compareTo(nv) > 0 ? xv : nv)); // xv: existing, nv: new
        System.out.println(languages);

        var countryLanguageSets = Locale
                .availableLocales()
                .collect(Collectors.toMap(
                        Locale::getDisplayCountry,
                        lcl -> Collections.singleton(lcl.getDisplayLanguage()),
                        (xv, nv) -> {
                            var union = new HashSet<>(xv);
                            union.addAll(nv);
                            return union;
                        }));
        System.out.println(countryLanguageSets);
    }
}
