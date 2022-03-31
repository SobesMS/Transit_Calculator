import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class TransitCalculator {
    int numOfDays;
    int numOfRides;
    int customerAge;
    private static final String[] RATE_OPTIONS = {"Pay-per-ride", "7-day Unlimited", "30-day Unlimited"};
    private static final double[] RATE_PRICES = {2.75, 33.00, 127.00};
    private static final double[] RATE_PRICES_SENIORS = {1.35, 16.50, 63.50};

    public TransitCalculator(int numOfDays, int numOfRides, int customerAge) {
        if (numOfDays <= 0) {
            this.numOfDays = -1;
        } else {
            this.numOfDays = numOfDays;
        }
        if (numOfRides <= 0) {
            this.numOfRides = -1;
        } else {
            this.numOfRides = numOfRides;
        }
        if (customerAge <= 0) {
            this.customerAge = -1;
        } else {
            this.customerAge = customerAge;
        }
    }

    private double unlimited7Price(int numOfDays, int numOfRides, int customerAge) {
        int numOfWeeks;
        if (numOfDays <= 7) {
            numOfWeeks = 1;
        } else {
            numOfWeeks = numOfDays / 7;
            if ((numOfDays % 7) != 0) {
                numOfWeeks++;
            }
        }
        if (customerAge >= 65) {
            return (numOfWeeks * RATE_PRICES_SENIORS[1]) / numOfRides;
        } else {
            return (numOfWeeks * RATE_PRICES[1]) / numOfRides;
        }
    }

    private double unlimited30Price(int numOfDays, int numOfRides, int customerAge) {
        int numOfMonths;
        if (numOfDays <= 30) {
            numOfMonths = 1;
        } else {
            numOfMonths = numOfDays / 30;
            if ((numOfDays % 30) != 0) {
                numOfMonths++;
            }
        }
        if (customerAge >= 65) {
            return (numOfMonths * RATE_PRICES_SENIORS[2]) / numOfRides;
        } else {
            return (numOfMonths * RATE_PRICES[2]) / numOfRides;
        }
    }

    private double[] getPerRidePrices(int numOfDays, int numOfRides, int customerAge) {
        double[] pricesPerRide = new double[3];
        if (customerAge >= 65) {
            pricesPerRide[0] = RATE_PRICES_SENIORS[0];
        } else {
            pricesPerRide[0] = RATE_PRICES[0];
        }
        pricesPerRide[1] = unlimited7Price(numOfDays, numOfRides, customerAge);
        pricesPerRide[2] = unlimited30Price(numOfDays, numOfRides, customerAge);
        return pricesPerRide;
    }

    private String getBestFare() {
        if (numOfDays == -1 || numOfRides == -1 || customerAge == -1) {
            return "Invalid entry.";
        } else {
            double[] pricesPerRide = getPerRidePrices(numOfDays, numOfRides, customerAge);
            int resultIndex = 0;
            for (int i = 1; i < pricesPerRide.length; i++) {
                if (pricesPerRide[i] < pricesPerRide[0]) {
                    resultIndex = i;
                }
            }
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            if (customerAge >= 65) {
                return "You should get the senior " + RATE_OPTIONS[resultIndex] + " option at " + currencyFormat.format(pricesPerRide[resultIndex]) + " per ride.";
            } else {
                return "You should get the " + RATE_OPTIONS[resultIndex] + " option at " + currencyFormat.format(pricesPerRide[resultIndex]) + " per ride.";
            }
        }
    }

    public static void main(String[] args) {
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            TransitCalculator myTransitCalculator = new TransitCalculator(r.nextInt(366), r.nextInt(1000), r.nextInt(83)+18);
            System.out.println("Days " + myTransitCalculator.numOfDays + " : Rides " + myTransitCalculator.numOfRides + " : Age " + myTransitCalculator.customerAge);
            System.out.println(myTransitCalculator.getBestFare() + "\n");
        }
    }
}
