package strategypattern.actiongame;

interface Weapon {
    String useWeapon();
}

class Axe implements Weapon {
    @Override
    public String useWeapon() {
        return getClass()
                .getSimpleName() + ": Chop, Mace, and Club";
    }
}

class BowAndArrow implements Weapon {
    @Override
    public String useWeapon() {
        return getClass()
                .getSimpleName() + ": Aim and Shoot";
    }
}

class Knife implements Weapon {
    @Override
    public String useWeapon() {
        return getClass()
                .getSimpleName() + ": Cut and Slash";
    }
}

class Sword implements Weapon {
    @Override
    public String useWeapon() {
        return getClass()
                .getSimpleName() + ": Lunge and Parry";
    }
}
