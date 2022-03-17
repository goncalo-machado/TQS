package euromillions;

import java.util.Objects;

import sets.SetOfNaturals;

import java.util.Random;

/**
 * A set of 5 numbers and 2 stars according to the Euromillions ranges.
 *
 * @author ico0
 */
public class Dip {


    private SetOfNaturals numbers;
    private SetOfNaturals stars;
    private static final int STARCOUNT = 2;
    private static final int NUMBERSCOUNT = 5;
    private static final int STARMAX = 12;
    private static final int NUMBERSMAX = 50;


    public Dip() {
        numbers = new SetOfNaturals();
        stars = new SetOfNaturals();
    }

    public Dip(int[] arrayOfNumbers, int[] arrayOfStars) {
        this();

        if (NUMBERSCOUNT == arrayOfNumbers.length && STARCOUNT == arrayOfStars.length) {
            for (int i = 0; i < arrayOfNumbers.length; i++){
                if (0 > arrayOfNumbers[i] || arrayOfNumbers[i] > NUMBERSMAX){
                    throw new IllegalArgumentException("Invalid number");
                }
            }
            for (int i = 0; i < arrayOfStars.length; i++){
                if (0 > arrayOfStars[i] || arrayOfStars[i] > STARMAX){
                    throw new IllegalArgumentException("Invalid star");
                }
            }
            numbers.add(arrayOfNumbers);
            stars.add(arrayOfStars);
        } else {
            throw new IllegalArgumentException("wrong number of elements in numbers/stars");
        }

    }

    public SetOfNaturals getNumbersColl() {
        return numbers;
    }

    public SetOfNaturals getStarsColl() {
        return stars;
    }

    public static Dip generateRandomDip() {
        Random generator = new Random();

        Dip randomDip = new Dip();
        for (int i = 0; i < 5; ) {
            int candidate = generator.nextInt(49) + 1;
            if (!randomDip.getNumbersColl().contains(candidate)) {
                randomDip.getNumbersColl().add(candidate);
                i++;
            }
        }
        for (int i = 0; i < 2; ) {
            int candidate = generator.nextInt(9) + 1;
            if (!randomDip.getStarsColl().contains(candidate)) {
                randomDip.getStarsColl().add(candidate);
                i++;
            }
        }
        return randomDip;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.numbers);
        hash = 29 * hash + Objects.hashCode(this.stars);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dip other = (Dip) obj;
        if (!Objects.equals(this.numbers, other.numbers)) {
            return false;
        }
        return Objects.equals(this.stars, other.stars);
    }


    /**
     * prepares a string representation of the data structure, formated for
     * printing
     *
     * @return formatted string with data
     */
    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("N[");
        for (int number : getNumbersColl()) {
            sb.append(String.format("%3d", number));
        }
        sb.append("] S[");
        for (int star : getStarsColl()) {
            sb.append(String.format("%3d", star));
        }
        sb.append("]");
        return sb.toString();
    }
}
