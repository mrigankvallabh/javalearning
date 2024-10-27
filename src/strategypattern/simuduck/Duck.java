package strategypattern.simuduck;

abstract class Duck {
    private final QuackBehavior quackBehavior;
    private final FlyBehavior flyBehavior;

    Duck(QuackBehavior quackBehavior, FlyBehavior flyBehavior) {
        this.quackBehavior = quackBehavior;
        this.flyBehavior = flyBehavior;
    }

    String performQuack() {
        return quackBehavior.quack();
    }

    String performFly() {
        return flyBehavior.fly();
    }

    void performSwim() {
        System.out.println(getClass().getSimpleName() + " Swimming");
    }

    abstract String display();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [quackBehavior=" + quackBehavior + ", flyBehavior=" + flyBehavior + "]";
    }
}

class MallardDuck extends Duck {

    MallardDuck() {
        var quackBehavior = new Quack();
        var flyBehavior = new FlyWithWings();
        super(quackBehavior, flyBehavior);
    }

    @Override
    String display() {
        return toString();
    }
}

class RocketDuck extends Duck {

    RocketDuck() {
        var quackBehavior = new MuteQuack();
        var flyBehavior = new FlyWithRocket();
        super(quackBehavior, flyBehavior);
    }

    @Override
    String display() {
        return toString();
    }
}
