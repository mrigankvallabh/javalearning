package chapter02.welcome;
final class Welcome {
    static void main(String[] args) {
        var greeting = "Hello, World!";
        System.out.println(greeting);
        for (int i = 0; i < greeting.length(); i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}
