package chapter05;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

final class ManagerTest {
    static void main() throws CloneNotSupportedException {
        final var BOSS = new Manager("Jerry Springer", 92_000, 1982, 3, 21);
        BOSS.setBonus(9_200);
        final var staff = new Employee[3];
        staff[0] = BOSS;
        staff[1] = new Employee("Michael Chang", 76_000, 1992, 1, 10);
        staff[2] = new Employee("Wilma Sinclair", 60_000, 2000, 10, 8);
        Arrays.sort(staff);
        for (Employee e : staff) {
            if (e instanceof Manager m) {
                m.raiseSalary(2);
            } else {
                e.raiseSalary(1);
            }
            System.out.println(e);
        }
        final var boss = BOSS.clone();
        boss.setBonus(10_250);
        boss.raiseSalary(2);
        if (boss.equals(BOSS)) {
            System.out.println(boss + " (" + boss.hashCode() + ") and " +
                    BOSS + " (" + BOSS.hashCode() + ") are same");
        }

        var lstaff = new ArrayList<Person>();
        lstaff.add(BOSS);
        lstaff.add(staff[1]);
        lstaff.add(staff[2]);
        lstaff.add(new Student("Blecky Fletcher", "Computer Science"));
        for (var e : lstaff) {
            System.out.println(e.getDescription());
        }
    }
}

abstract class Person implements Comparable <Person> {
    private final String name;

    Person(String name) {
        this.name = name;
    }

    abstract String getDescription();

    public String getName() {
        return name;
    }

    @Override
    public final int compareTo(Person o) {
        var spName = name.split(" ");
        var spOtherName = o.name.split(" ");
        return spName[spName.length - 1].compareTo(spOtherName[spOtherName.length - 1]);
    }

}

class Employee extends Person implements Cloneable {
    private double salary;
    private final LocalDate hireDate;

    public Employee(String name, double salary, int year, int month, int dayOfMonth) {
        super(name);
        this.salary = salary;
        hireDate = LocalDate.of(year, month, dayOfMonth);
    }

    public double getSalary() {
        return salary;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100.0;
        this.salary += raise;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        /*
         * If the semantics of Equality can change in the subclass use getClass test
         * It the equals applies to all subclasses then use instanceof test
         * if (!obj instance of Employee e) return false;
         */
        if (getClass() != obj.getClass()) {
            return false;
        }

        var otherEmployee = (Employee) obj;

        return (Objects.equals(getName(), otherEmployee.getName()) &&
                salary == otherEmployee.salary &&
                Objects.equals(hireDate, otherEmployee.hireDate));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), salary, hireDate);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + getName() + ", salary=" + salary + ", hireDate=" + hireDate + "]";
    }

    @Override
    String getDescription() {
        return getClass().getSimpleName() + " " + getName() + " earning HK$%.2f".formatted(salary);
    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        return (Employee) super.clone();
    }
    
}

final class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int year, int month, int dayOfMonth) {
        super(name, salary, year, month, dayOfMonth);
        this.bonus = 0;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double getSalary() {
        return super.getSalary() + bonus;
    }

    @Override
    public String toString() {
        return super.toString() + ", [bonus=" + bonus + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bonus);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Manager otherManager = (Manager) obj;
        return bonus == otherManager.bonus;
    }

    @Override
    protected Manager clone() throws CloneNotSupportedException {
        return (Manager) super.clone();
    }

}

final class Student extends Person {
    private final String major;

    public Student(String name, String major) {
        super(name);
        this.major = major;
    }
    
    @Override
    String getDescription() {
        return getClass().getSimpleName() + " " + getName() + " majoring in " + major;
    }
}