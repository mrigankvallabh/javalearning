package practice;

interface Vehicle {
    void drive();

    String getFuelType();
}

class Car implements Vehicle {

    @Override
    public void drive() {
        System.out.println("Vroom...vroom!");
    }

    @Override
    public String getFuelType() {
        return "Petrol";
    }

}

class Bike implements Vehicle {

    @Override
    public void drive() {
        System.out.println("Two wheels vroom");

    }

    @Override
    public String getFuelType() {
        return "Petrol";
    }

}

class VehicleFactory {
    static Vehicle creatVehicle(String type) {
        return switch (type) {
            case "car" -> new Car();
            case "bike" -> new Bike();
            default -> throw new IllegalArgumentException("What?");
        };
    }
}

abstract class AbstractVehicleFactory {
    abstract Vehicle creatVehicle();

    void howToDrive() {
        Vehicle v = creatVehicle();
        System.out.println("Put " + v.getFuelType() + " and go ");
        v.drive();
    }
}

class CarDrive extends AbstractVehicleFactory {

    @Override
    Vehicle creatVehicle() {

        return new Car();
    }

}