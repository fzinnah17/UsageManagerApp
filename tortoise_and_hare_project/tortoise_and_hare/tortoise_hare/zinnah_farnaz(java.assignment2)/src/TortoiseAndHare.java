public class TortoiseAndHare {

    // Maximum moves
    private static final int MAX_MOVES = 100;

    // Maximum Iterations
    private static final int MAX_ITERATIONS = 2000;

    // Race State
    private int hare;
    private int tortoise;
    private int steps;

    public TortoiseAndHare() {

        printPosition();

        // Run the simulation - Either win Hare or Tortoise
        // or Simulation ends
        while (hare < MAX_MOVES-1 && tortoise < MAX_MOVES-1 && steps < MAX_ITERATIONS) {
           
            // Tortoise move
            simulateTortoiseMove();

            // Hare Move
            simulateHareMove();

            this.steps++;

            // Print the Positions
            printPosition();
        }

        if (this.steps == 2000 && (hare != 100 || tortoise != 100))
        {
            System.out.println("Time Out!");
        }

        if (hare == tortoise) {
            System.out.println("It's a tie");
        } else if (tortoise > hare) {
            System.out.println("TORTOISE WINS!!! YAY!!!");
        } else {
            System.out.println("Hare wins. Yuch!");
        }

        System.out.printf("Time Elapsed = %d seconds\n", steps);
    }

    /**
     * Main method - Entry point of the pgoram.
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("ON YOUR MARK, GET SET");
        System.out.println("BANG !!!!!");
        System.out.println("AND THEY'RE OFF !!!!!");
        TortoiseAndHare race = new TortoiseAndHare();
    }

    /**
     * Function to print the Current State of the Race Positions of Hare and
     * Tortoise.
     */
    private void printPosition() {
        System.out.printf("Iteration: %d%n", steps);

        for (int i = 0; i < MAX_MOVES; i++) {
            if (i == hare && i == tortoise) {
                System.out.print("B");
            } else if (i == tortoise) {
                System.out.print("T");
            } else if (i == hare) {
                System.out.print("H");
            } else {
                System.out.print(' ');
            }
        }

        System.out.printf(String.format("%n%100s%n", "").replace(' ', '-'));
    }

    /**
     * Function to generate a random number within the given range of low and high
     * inclusive using Random class.
     * 
     * @param low  lower limit
     * @param high upper limit
     * @return random number
     */
    private int randomBetween(int low, int high) {
        return (int)((Math.random() * (high-low)) + low);
    }

    /**
     * Function to Simulate the Movement of Tortoise for one simulation step.
     */
    public void simulateTortoiseMove() {
        int state = randomBetween(0, 9);
        switch (state) {
            case 0: // do nothing
                break; 
            case 1:
            case 2:
            case 3:
            case 4: // move forward (1-3)
                tortoise += randomBetween(1, 3);
                break;
            case 5:
            case 6:
            case 7: // move backwad
                tortoise -= randomBetween(1, 6);
                break;
            case 8:
            case 9: // walk - move forward
                tortoise += randomBetween(0, 1);
                break;
        }

        // boundary cases
        if (tortoise < 0) {
            tortoise = 0;
        } else if (tortoise >= MAX_MOVES) {
            tortoise = MAX_MOVES - 1;
        }
    }

    /**
     * Function to Simulate the Movement of Hare for one simulation step.
     */
    private void simulateHareMove() {
        int state = randomBetween(0, 9);
        switch (state) {
            case 0:
                break; // Do nothing
            case 1:
            case 2:
            case 3:// move forward (1-5)
                hare += randomBetween(1, 5);
                break;
            case 4:
            case 5: // Slip - (1, 2) move backwad
                hare -= randomBetween(1, 2);
                break;
            case 6: // Big Slip(1,7)
                hare -= randomBetween(1, 7);
                break;
            case 7:
            case 8:
            case 9: // Walk - move forward (0, 1)
                hare += randomBetween(0, 1);
                break;
        }

        // boundary cases
        if (hare < 0) {
            hare = 0;
        } else if (hare >= MAX_MOVES) {
            hare = MAX_MOVES - 1;
        }
    }
}
