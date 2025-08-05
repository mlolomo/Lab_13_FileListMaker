import java.util.Scanner;

public class SafeInput {
    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    // Part A
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;
    }

    // Part B
    public static int getInt(Scanner pipe, String prompt) {
        int retValue = 0;
        boolean done = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retValue = pipe.nextInt();
                pipe.nextLine();
                done = true;
            } else {
                String trash = pipe.nextLine();
                System.out.println("You must enter a valid integer. Not: " + trash);
            }
        } while (!done);

        return retValue;
    }

    // Part C
    public static double getDouble(Scanner pipe, String prompt) {
        double value = 0;
        boolean valid = false;
        String trash;

        do {
            System.out.print(prompt + ": ");
            if (pipe.hasNextDouble()) {
                value = pipe.nextDouble();
                pipe.nextLine();  // flush the Enter key
                valid = true;
            } else {
                trash = pipe.nextLine(); // clear bad input
                System.out.println("Your input is Invalid: " + trash);
            }
        } while (!valid);

        return value;
    }

    // Part D
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int value = 0;
        boolean valid = false;
        do {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                value = pipe.nextInt();
                if (value >= low && value <= high)
                    valid = true;
                else
                    System.out.println("Input out of range!");
            } else {
                String trash = pipe.nextLine();
                System.out.println("Invalid input: " + trash);
            }
            pipe.nextLine(); // clear buffer
        } while (!valid);
        return value;
    }

    // Part E
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retVal = 0;
        boolean done = false;

        do {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();

                if (retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("You must enter a number between " + low + " and " + high + ".");
                }
            } else {
                String trash = pipe.next();
                System.out.println("Invalid input: " + trash);
            }
            pipe.nextLine(); // clear the newline
        } while (!done);

        return retVal;
    }

    // Part F
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String response;
        boolean isYes = false;
        boolean validInput = false;

        do {
            System.out.print(prompt + " [Y/N]: ");
            response = pipe.nextLine().trim();

            if (response.equalsIgnoreCase("Y")) {
                isYes = true;
                validInput = true;
            } else if (response.equalsIgnoreCase("N")) {
                isYes = false;
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }

        } while (!validInput);

        return isYes;
    }

    // Part G
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String input = "";
        boolean valid = false;
        do {
            System.out.print(prompt + ": ");
            input = pipe.nextLine();
            if (input.matches(regEx)) {
                valid = true;
            } else {
                System.out.println("Invalid input, does not match pattern.");
            }
        } while (!valid);
        return input;
    }
    public static void PrettyHeader(String msg) {
        final int TOTAL_WIDTH = 60;
        final int SIDE_STARS = 3;
        int msgLength = msg.length();
        int totalSpaces = TOTAL_WIDTH - msgLength - (SIDE_STARS * 2);
        int leftSpaces = totalSpaces / 2;
        int rightSpaces = totalSpaces - leftSpaces;

        for (int i = 0; i < TOTAL_WIDTH; i++) {
            System.out.print("*");
        }
        System.out.println();

        System.out.print("***");
        for (int i = 0; i < leftSpaces; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < rightSpaces; i++) {
            System.out.print(" ");
        }
        System.out.println("***");

        for (int i = 0; i < TOTAL_WIDTH; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

}
