package numbers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

enum NumberProperties {
    EVEN ("ODD -EVEN") {
        @Override
        boolean hasProperty(long n) {return n % 2 == 0;}

        @Override
        NumberProperties getOpponent() {
            return ODD;
        }
    },
    ODD ("EVEN -ODD") {
        @Override
        boolean hasProperty(long n) {return n % 2 != 0;}

        @Override
        NumberProperties getOpponent() {
            return EVEN;
        }
    },
    BUZZ ("-BUZZ") {
        @Override
        boolean hasProperty(long n) {return n % 7 == 0 || n % 10 == 7;}

        @Override
        NumberProperties getOpponent() {
            return this;
        }
    },
    DUCK ("SPY -DUCK") {
        @Override
        boolean hasProperty(long n) {
            while(n != 0) {
                if (n % 10 == 0) {
                    return true;
                }
                n /= 10;
            }
            return false;
        }

        @Override
        NumberProperties getOpponent() {
            return SPY;
        }
    },
    PALINDROMIC ("-PALINDROMIC") {
        @Override
        boolean hasProperty(long n) {
            return new StringBuilder(String.valueOf(n))
                    .reverse()
                    .toString()
                    .equals(String.valueOf(n));
        }

        @Override
        NumberProperties getOpponent() {
            return this;
        }
    },
    GAPFUL ("-GAPFUL") {
        @Override
        boolean hasProperty(long n) {
            if (n < 100) {
                return false;
            }
            String[] array = Long.toString(n).split("");
            int gap = Integer.parseInt(array[0] + array[array.length -1]);
            return n % gap == 0;
        }

        @Override
        NumberProperties getOpponent() {
            return this;
        }
    },
    SPY ("DUCK -SPY") {
        @Override
        boolean hasProperty(long n) {
            String[] array = Long.toString(n).split("");
            int sum = 0;
            int product = 1;
            for (String s : array) {
                sum += Integer.parseInt(s);
                product *= Integer.parseInt(s);
            }
            return sum == product;
        }

        @Override
        NumberProperties getOpponent() {
            return DUCK;
        }
    },
    SQUARE ("SUNNY -SQUARE") {
        @Override
        boolean hasProperty(long n) {
            double sqrt = Math.sqrt(n);
            return sqrt - Math.floor(sqrt) == 0;
        }

        @Override
        NumberProperties getOpponent() {
            return SUNNY;
        }
    },
    SUNNY ("SQUARE -SUNNY"){
        @Override
        boolean hasProperty(long n) {
            return Math.sqrt(n + 1) % 1 == 0;
        }

        @Override
        NumberProperties getOpponent() {
            return SQUARE;
        }
    },
    JUMPING ("-JUMPING"){
        @Override
        boolean hasProperty(long n) {
            char[] array = Long.toString(n).toCharArray();
            for (int i = 1; i < array.length; i++) {
                if (Math.abs(array[i - 1] - array[i]) != 1) {
                    return false;
                }
            }
            return true;
        }

        @Override
        NumberProperties getOpponent() {
            return this;
        }
    },
    HAPPY ("SAD -HAPPY"){
        @Override
        boolean hasProperty(long n) {
            long temp = n;

            do {
                char[] array = Long.toString(temp).toCharArray();
                temp = 0;

                for (char c : array) {
                    temp += Math.pow(c - '0', 2);
                }
                if (temp < 7 && temp != 1) {
                    return false;
                }
            } while (temp != 1);
            return true;
        }

        @Override
        NumberProperties getOpponent() {
            return SAD;
        }
    },
    SAD ("HAPPY -SAD") {
        @Override
        boolean hasProperty(long n) {
            return !HAPPY.hasProperty(n);
        }

        @Override
        NumberProperties getOpponent() {
            return HAPPY;
        }
    };

    private final String exclusiveProperty;

    NumberProperties(String exclusiveProperty) {
        this.exclusiveProperty = exclusiveProperty;
    }

    String[] getExclusiveProperty() {
        return this.exclusiveProperty.split(" ");
    }

    static List<String> getProperties(long n) {
        return Arrays.stream(values())
                .filter(e -> e.hasProperty(n))
                .map(Enum::toString)
                .collect(Collectors.toList());
    }



    abstract boolean hasProperty(long n);

    abstract NumberProperties getOpponent();
}
