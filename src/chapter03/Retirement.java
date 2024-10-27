package chapter03;

import java.util.Scanner;

final class Retirement {
    public static void main() {
        try (var in = new Scanner(System.in)) {
            System.out.print("How much money do you need to retire? ");
            var goal = in.nextDouble();
            System.out.print("How much money will you invest each year? ");
            var payment = in.nextDouble();
            System.out.print("How much interest? ");
            var rate = in.nextDouble();

            var balance = 0.00;
            var years = 0;

            while (balance < goal) {
                balance += payment;
                var interest = balance * rate / 100;
                balance += interest;
                ++years;
            }
            System.out.println("You can retire in " + years + " years.");
        }
    }    
}
