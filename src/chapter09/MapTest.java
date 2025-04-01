package chapter09;

import java.util.HashMap;

final class MapTest {
    static void main(String[] args) {
        var staff = new HashMap<String, Employee>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Greg Reeves"));
        staff.put("157-62-7935", new Employee("Kenny Li"));
        staff.put("456-62-5527", new Employee("Alistair Rolund"));

        System.out.println(staff);

        staff.remove("567-24-2546");
        staff.put("456-62-5527", new Employee("Wilma Sunderland"));
        System.out.println(staff.get("157-62-7935"));
        staff.forEach((k, v) -> System.out.println("<" + k + ": " + v + ">"));
    }
}

final class Employee {
    private final String name;
    private double salary;

    public Employee(String name) {
        this.name = name;
        salary = 0;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", salary=" + salary + "]";
    }

}