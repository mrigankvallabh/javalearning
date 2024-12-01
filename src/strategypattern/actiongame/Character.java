package strategypattern.actiongame;

abstract class Character {
    private final String role;
    private Weapon weapon;

    Character(String role) {
        this.role = role;
    }

    String getRole() {
        return role;
    }

    Weapon getWeapon() {
        return weapon;
    }

    void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    String fight() {
        return weapon.useWeapon();
    }

    @Override
    public String toString() {
        return "Character<" +
                getClass().getSimpleName() +
                "> [role=" + role + ", weapon=" + weapon.useWeapon() + "]";
    }
}

class King extends Character {
    King() {
        super("King");
        Weapon knife = new Knife();
        super.setWeapon(knife);
    }
}

class Queen extends Character {
    Queen() {
        super("Queen");
        Weapon bow = new BowAndArrow();
        super.setWeapon(bow);
    }
}

class Knight extends Character {
    Knight() {
        super("Knight");
        Weapon sword = new Sword();
        super.setWeapon(sword);
    }
}

class Troll extends Character {
    Troll() {
        super("Troll");
        Weapon axe = new Axe();
        super.setWeapon(axe);
    }
}
