package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Message.welcome();
        Message.instructions();

        while (true) {
            Message.request();
            String[] userInput = scanner.nextLine().trim().toUpperCase().split("\\s+");
            if (userInput.length == 1 && userInput[0].equals("0")) {
                Message.goodbye();
                break;
            }
            if (!isValid(userInput)) {
                continue;
            }
            switch (userInput.length) {
                case 1:
                    getProperties(Long.parseLong(userInput[0]));
                    break;
                case 2:
                    getProperties(Long.parseLong(userInput[0]), Long.parseLong(userInput[1]));
                    break;
                default:
                    getProperties(Long.parseLong(userInput[0]), Long.parseLong(userInput[1]), userInput);
                    break;
            }
        }
        scanner.close();
    }

    private static boolean isValid(String[] inputArray) {
        if (inputArray.length == 0) {
            Message.instructions();
            return false;
        }
        switch (inputArray.length) {
            case 1:
            case 2:
                return isNatural(inputArray);
            case 3:
                return isNatural(inputArray) && isGoodProperty(inputArray);
            default:
                return isNatural(inputArray) && isGoodProperty(inputArray) && isNotExclusive(inputArray);
        }
    }

    private static boolean isNatural(String[] inputArray) {
        try {
            if (Long.parseLong(inputArray[0]) < 1) {
                Message.firstNumberError();
                return false;
            }
        } catch (NumberFormatException e) {
            Message.firstNumberError();
        }
        try {
            if (inputArray.length > 1 && Long.parseLong(inputArray[1]) < 1) {
                Message.secondNumberError();
                return false;
            }
        } catch (NumberFormatException e) {
            Message.secondNumberError();
            return false;
        }

        return true;
    }

    private static boolean isGoodProperty(String[] inputArray) {
        List<String> badProperties = new ArrayList<>();

        for (String input : Arrays.copyOfRange(inputArray, 2, inputArray.length)) {
            boolean contains = false;
            for (NumberProperties property : NumberProperties.values()) {
                if (property.name().equals(input) || ("-" + property.name()).equals(input)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                badProperties.add(input);
            }
        }
        if (badProperties.size() == 1) {
            Message.singlePropertyError(badProperties.get(0));
            return false;
        }
        if (badProperties.size() > 1) {
            Message.multiplePropertyError(badProperties);
            return false;
        }

        return true;
    }

    private static boolean isNotExclusive(String[] inputArray) {
        List<String> badProperties = new ArrayList<>();
        List<String> request = List.of(inputArray).subList(2, inputArray.length);
        for (int i = 0; i < request.size(); i++) {
            String current = request.get(i);
            String[] exclusiveProperties;

            if (current.startsWith("-")) {
                NumberProperties opponent = NumberProperties.valueOf(current.substring(1)).getOpponent();
                exclusiveProperties = opponent.getExclusiveProperty();
            } else {
                exclusiveProperties = NumberProperties.valueOf(current).getExclusiveProperty();
            }

            for (String p : exclusiveProperties) {
                if (request.subList(i + 1, request.size()).contains(p)) {
                    if (!badProperties.contains(current)) {
                        badProperties.add(current);
                    }
                    badProperties.add(p);
                }
            }
        }

        if (!badProperties.isEmpty()) {
            Message.exclusivePropertyError(badProperties);
            return false;
        }

        return true;
    }

    // Properties for a single value
    private static void getProperties(long n) {
        System.out.println('\n' + "Properties of " + n + '\n');
        for (NumberProperties property : NumberProperties.values()) {
            System.out.printf("%12s: %b%n", property.toString().toLowerCase(), property.hasProperty(n));
        }
    }

    // Properties for start value and next N values with increment of 1
    private static void getProperties(long startValue, long n) {
        for (int i = 0; i < n; i++) {
            System.out.println("\n" + startValue + " is "
                    + String.join(", ", NumberProperties.getProperties(startValue)));
            startValue++;
        }
    }

    // Displaying N values with requested properties from start value
    private static void getProperties(long startValue, long n, String[] input) {
        System.out.println();
        List<String> request = List.of(Arrays.copyOfRange(input, 2, input.length));
        List<String> exclude = new ArrayList<>();
        List<String> include = new ArrayList<>();
        List<String> properties;

        for (String property : request) {
            if (property.startsWith("-")) {
                exclude.add(property.substring(1));
            } else {
                include.add(property);
            }
        }
        while (n > 0) {
            properties = NumberProperties.getProperties(startValue);
            if (exclude.size() == 0) {
                if (properties.containsAll(include)) {
                    System.out.println(startValue + " is " + String.join(", ", properties));
                    n--;
                }
            } else if (include.size() == 0) {
                if (notContains(properties, exclude)) {
                    System.out.println(startValue + " is " + String.join(", ", properties));
                    n--;
                }
            } else {
                if (properties.containsAll(include) && notContains(properties, exclude)) {
                    System.out.println(startValue + " is " + String.join(", ", properties));
                    n--;
                }
            }
            startValue++;
        }
    }

    private static boolean notContains(List<String> a, List<String> b) {
        for (String stringB : b) {
            if (a.contains(stringB)) {
                return false;
            }
        }
        return true;
    }
    
}
