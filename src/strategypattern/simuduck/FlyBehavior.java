package strategypattern.simuduck;

interface FlyBehavior {
    String fly();
}

class FlyWithWings implements FlyBehavior {
    @Override
    public String fly() {
        return "Flap flap flap...";
    }

    @Override
    public String toString() {
        return "FlyWithWings";
    }
}

class FlyNoWay implements FlyBehavior {
    @Override
    public String fly() {
        return "Cannot fly!";
    }

    @Override
    public String toString() {
        return "FlyNoWay";
    }
}

class FlyWithRocket implements FlyBehavior {
    @Override
    public String fly() {
        return "Zoooom!";
    }

    @Override
    public String toString() {
        return "FlyWithRocket";
    }
}