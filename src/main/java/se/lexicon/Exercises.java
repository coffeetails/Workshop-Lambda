package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	DONE: Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getFirstName().equals("Erik");

        List<Person> persons = storage.findMany(filter);
        for(Person person : persons) {
            System.out.println(person.toString());
        }

        System.out.println("----------------------");
    }

    /*
        2.	DONE: Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getGender().equals(Gender.FEMALE);

        List<Person> persons = storage.findMany(filter);
        for(Person person : persons) {
            System.out.println(person.toString());
        }

        System.out.println("----------------------");
    }

    /*
        3.	DINE: Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getBirthDate().isAfter(LocalDate.of(2000, 1, 1).minusDays(1));

        List<Person> persons = storage.findMany(filter);
        for(Person person : persons) {
            System.out.println(person.toString());
        }

        System.out.println("----------------------");
    }

    /*
        4.	DONE: Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getId() == 123;
        Person person = storage.findOne(filter);
        System.out.println(person.toString());

        System.out.println("----------------------");
    }

    /*
        5.	DONE: Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getId() == 456;
        Function<Person, String> personToString = person -> {
            return String.format("Name: %s %s born %s", person.getFirstName(), person.getLastName(), person.getBirthDate().toString());
        };
        String person = storage.findOneAndMapToString(filter, personToString);
        System.out.println(person);


        System.out.println("----------------------");
    }

    /*
        6.	DONE: Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getFirstName().startsWith("E") && person.getGender().equals(Gender.MALE);
        Function<Person, String> personToString = person -> {
            return String.format("Name: %s %s born %s", person.getFirstName(), person.getLastName(), person.getBirthDate().toString());
        };
        List<String> persons = storage.findManyAndMapEachToString(filter, personToString);
        for(String person : persons) {
            System.out.println(person);
        }

        System.out.println("----------------------");
    }

    /*
        7.	DONE: Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getBirthDate().isAfter(LocalDate.now().minusYears(9));
        Function<Person, String> personToString = person -> {
            return String.format("%s %s %s years", person.getFirstName(), person.getLastName(), LocalDate.now().getYear() - person.getBirthDate().getYear());
        };
        List<String> persons = storage.findManyAndMapEachToString(filter, personToString);
        for(String person : persons) {
            System.out.println(person);
        }

        System.out.println("----------------------");
    }

    /*
        8.	DONE: Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getFirstName().equals("Ulf");
        Consumer<Person> consumer = person -> System.out.println(person.toString());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        9.	DONE: Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getLastName().contains(person.getFirstName());
        Consumer<Person> consumer = person -> System.out.println(person.toString());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        10.	DONE: Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> {
            String name = person.getFirstName().toLowerCase();
            int nameLength = name.length();

            for(int i = 0; i < nameLength; i++) {
                if(i > nameLength - i) {
                    break;
                } else if(name.charAt(i) != name.charAt(nameLength - 1 - i)) {
                    return false;
                }
            }
            return true;
        };
        Consumer<Person> consumer = person -> System.out.println(person.toString());
        storage.findAndDo(filter, consumer);

        System.out.println("----------------------");
    }

    /*
        11.	DONE: Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getFirstName().startsWith("A");
        Comparator<Person> comparator = Comparator.comparing(person -> person.getBirthDate());
        List<Person> personList = storage.findAndSort(filter, comparator);
        for(Person person : personList) {
            System.out.println(person.toString());
        }

        System.out.println("----------------------");
    }

    /*
        12.	DONE: Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);

        Predicate<Person> filter = person -> person.getBirthDate().isBefore(LocalDate.of(1950, 1, 1));
        Comparator<Person> comparator = Comparator.comparing(person -> person.getBirthDate(), Comparator.reverseOrder());
        List<Person> personList = storage.findAndSort(filter, comparator);
        for(Person person : personList) {
            System.out.println(person.toString());
        }

        System.out.println("----------------------");
    }

    /*
        13.	TODO: Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);

        Comparator<Person> comparator = Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).thenComparing(Person::getBirthDate);
        List<Person> personList = storage.findAndSort(comparator);
        for(Person person : personList) {
            System.out.printf("%s %s %s\n", person.getLastName(), person.getFirstName(), person.getBirthDate().toString());
        }

        System.out.println("----------------------");
    }

}