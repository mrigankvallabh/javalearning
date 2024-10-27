package strategypattern.simuduck;

interface QuackBehavior {
    String quack();
}

class Quack implements QuackBehavior {
    @Override
    public String quack() {
        return "Quack quack!";
    }

    @Override
    public String toString() {
        return "Quack";
    }
    
}

class Squeak implements QuackBehavior {
    @Override
    public String quack() {
        return "Squeak squeak!";
    }

    @Override
    public String toString() {
        return "Squeak";
    }

}

class MuteQuack implements QuackBehavior {
    @Override
    public String quack() {
        return "Shh...!";
    }

    @Override
    public String toString() {
        return "MuteQuack";
    }
}