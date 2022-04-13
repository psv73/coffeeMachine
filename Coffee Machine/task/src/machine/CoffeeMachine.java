package machine;

import java.util.Scanner;

public class CoffeeMachine {

    //Amount at the start
    private int water = 400;
    private int milk = 540;
    private int coffeeBeans = 120;
    private int cups = 9;
    private int summa = 550;

    private final Scanner scanner;

    public CoffeeMachine() {
        this.scanner = new Scanner(System.in);
    }

    public int getWater() {
        return water;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getMilk() {
        return milk;
    }

    public void setWater(int water) {
        this.water += water;
    }

    public void setSumma(int summa) {
        this.summa += summa;
    }

    public void setCups(int cups) {
        this.cups += cups;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans += coffeeBeans;
    }

    public void setMilk(int milk) {
        this.milk += milk;
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        coffeeMachine.chooseAction();
    }

    public void chooseAction() {
        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");

            var input = scanner.nextLine().trim().toUpperCase();

            switch (Action.valueOf(input)) {
                case BUY:
                    this.buyCoffee();
                    break;
                case FILL:
                    this.fillCoffeeMachine();
                    break;
                case TAKE:
                    this.takeMoney();
                    break;
                case REMAINING:
                    printStatus();
                    break;
                case EXIT:
                    System.exit(0);
                default:
                    throw new IllegalArgumentException("Unknown action");
            }
            System.out.println();
        }
    }

    public void buyCoffee() {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, " +
                "back - to main menu:");
        String input = (scanner.nextLine().trim().toLowerCase());

        if ("back".equals(input)) {
            return;
        }

        Coffee coffee = Coffee.values()[Integer.parseInt(input) - 1];

        if (!checkResources(coffee)) {
            return;
        }

        this.setWater(-coffee.getWater());
        this.setMilk(-coffee.getMilk());
        this.setCoffeeBeans(-coffee.getCoffeeBeans());
        this.setCups(-1);
        this.setSumma(coffee.getCost());
    }

    public boolean checkResources(Coffee coffee) {

        if (this.water < coffee.getWater()) {
            System.out.println("Sorry, not enough water!");
            return false;
        }

        if (this.milk < coffee.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }

        if (this.coffeeBeans < coffee.getCoffeeBeans()) {
            System.out.println("Sorry, not enough beans!");
            return false;
        }

        if (this.cups < 1) {
            System.out.println("Sorry, not enough cups!");
            return false;
        }

        System.out.println("I have enough resources, making you coffee!");
        return true;
    }

    public void takeMoney() {
        System.out.println();
        System.out.printf("I gave you $%d \n", this.summa);
        setSumma(-this.summa);
    }

    public void fillCoffeeMachine() {
        System.out.println("Write how many ml of water the coffee machine has:");
        this.setWater(this.scanner.nextInt());
        System.out.println("Write how many ml of milk the coffee machine has:");
        this.setMilk(this.scanner.nextInt());
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        this.setCoffeeBeans(this.scanner.nextInt());
        System.out.println("Write how many disposable cups of coffee you want to add:");
        this.setCups(this.scanner.nextInt());
        scanner.nextLine();
    }

    public void printStatus() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water\n", this.water);
        System.out.printf("%d ml of milk\n", this.milk);
        System.out.printf("%d g of coffee beans\n", this.coffeeBeans);
        System.out.printf("%d disposable cups\n", this.cups);
        System.out.printf("$%d of money\n", this.summa);
    }

    public void checkCupsCoffee(Coffee coffee) {
        System.out.println("Writ how many cups of coffee you will need:");
        int amount = this.scanner.nextInt();

        int cupsOfWater = getWater() / coffee.getWater();
        int cupsOfMilk = getMilk() / coffee.getMilk();
        int cupsOfCoffeeBeans = getCoffeeBeans() / coffee.getCoffeeBeans();

        int maxCups = Math.min(cupsOfMilk, Math.min(cupsOfCoffeeBeans, cupsOfWater));

        if (maxCups == amount) {
            System.out.println("Yes, I can make that amount of coffee");
        } else if (maxCups >= 0 && maxCups < amount) {
            System.out.printf("No, I can make only %d cup(s) of coffee\n", maxCups);
        } else {
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)\n",
                    maxCups - amount);
        }
    }

    public void calcAmountIngredients(int cups, Coffee coffee) {

        System.out.printf("For %d cups of coffee you will need:\n", cups);
        System.out.printf("%d ml of water\n", coffee.getWater() * cups);
        System.out.printf("%d ml of milk\n", coffee.getMilk() * cups);
        System.out.printf("%d g of coffee beans\n", coffee.getCoffeeBeans() * cups);

    }
}
