package chapter04;

import java.time.LocalDate;
import java.util.Objects;
import java.util.random.RandomGenerator;

final class EmployeeTest {
    static void main(String[] args) {
        var staff = new Employee[3];
        staff[0] = new Employee(
                "Madhura Puri",
                75_000,
                LocalDate.of(1987, 12, 12));
        staff[1] = new Employee(
                "Sandeep Sharma",
                67_000,
                LocalDate.of(1995, 06, 3));
        staff[2] = new Employee(
                "Aakash Sethi",
                75_600,
                1987, 10, 19);

        for (Employee employee : staff) {
            employee.raiseSalary(5);
        }

        for (Employee employee : staff) {
            System.out.println(employee);
        }
    }
}

final class Employee {
    private static int nextId = 0;
    private final int id;
    private final String name;
    private double salary = 0;
    private final LocalDate hireDate;

    static {
        var generator = RandomGenerator.getDefault();
        nextId = generator.nextInt(10_000);
    }

    {
        id = nextId;
    }

    Employee(String name, double salary, LocalDate hireDate) {
        this.name = Objects.requireNonNull(name, "Unknown Person");
        this.salary = salary;
        this.hireDate = Objects.requireNonNull(hireDate, "The hiredate must be provided.");
        Employee.nextId++;
    }

    Employee(String name, double salary, int year, int month, int day) {
        LocalDate hireDate = LocalDate.of(year, month, day);
        this(name, salary, hireDate);
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    double getSalary() {
        return salary;
    }

    LocalDate getHireDate() {
        return hireDate;
    }

    void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", hireDate=" + hireDate + "]";
    }

}
