package strategypattern.simuduck;

class DuckTest {
    static void main(String[] args) {
        Duck md = new MallardDuck();
        System.out.println(md.display());
        md.performSwim();
        System.out.println(md.performFly());
        System.out.println(md.performQuack());

        Duck rocketDuck = new RocketDuck();
        System.out.println(rocketDuck.display());
        System.out.println(rocketDuck.performQuack());
    }
}
