package numbers;

import java.util.Arrays;
import java.util.List;

class Message {

    static void welcome() {
        System.out.println("Welcome to Amazing Numbers!");
    }

    static void instructions() {
        System.out.println("\nSupported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and a property to search for;\n" +
                "- two natural numbers and two properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
    }

    static void request() {
        System.out.print("\nEnter a request: ");
    }

    static void goodbye() {
        System.out.println("\nGoodbye!");
    }

    static void firstNumberError() {
        System.out.println("The first parameter should be a natural number or zero.");
    }

    static void secondNumberError() {
        System.out.println("The second parameter should be a natural number.");
    }

    static void singlePropertyError(String wrongProperty) {
        System.out.println("The property " + wrongProperty + " is wrong." + '\n'
                + "Available properties: " + Arrays.toString(NumberProperties.values()) + '\n');
    }

    static void multiplePropertyError(List<String> wrongProperties) {
        System.out.println("The properties " + wrongProperties + " are wrong." + '\n'
                + "Available properties: " + Arrays.toString(NumberProperties.values()) + '\n');
    }

    static void exclusivePropertyError(List<String> exclusiveProperties) {
        System.out.println("The request contains mutually exclusive properties: "
                + exclusiveProperties);
        System.out.println("There are no numbers with these properties.");
    }
}
