package practice;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

final class Sortmaps {
    record Person(Integer personId, String firstName, String lastName) {
    }
    public static void main(String[] args) {
        
        Map<Integer, Person> personMap = Map.of(
            101, new Person(101, "Johny", "Lucas"),
            21, new Person(21, "Adams", "Luk"),
            3, new Person(3, "Andy", "Lau"),
            47, new Person(47, "Cherry", "Kong")
        );
        System.out.println("====");
        System.out.println(personMap);
        var sortedByLastName = personMap
            .values()
            .stream()
            .sorted(Comparator.comparing(Person::lastName, String.CASE_INSENSITIVE_ORDER))
            .collect(
                Collectors.toMap(
                    Person::personId,
                    Function.identity(),
                    (xv, _) -> xv,
                    LinkedHashMap::new
                    ));

        System.out.println("====");
        System.out.println(sortedByLastName);

    }

    
}
