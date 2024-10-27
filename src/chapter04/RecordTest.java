package chapter04;

final class RecordTest {
    static void main(String[] args) {
        CartesianPoint origin = CartesianPoint.ORIGIN;
        var p_3_4 = new CartesianPoint(3, 4);
        var p_5_12 = new CartesianPoint(5, -12);
        System.out.println(origin);
        System.out.println(p_3_4);
        System.out.println(p_5_12);
        System.out.println(p_3_4.distanceFromOrigin());
        System.out.println(p_3_4.distanceFromPoint(p_5_12));
        System.out.println(CartesianPoint.distanceBetweenPoints(p_5_12, origin));

        var p_o1 = PolarPoint.ORIGIN;
        var p_o2 = PolarPoint.ORIGIN;
        if (p_o1 == p_o2) {
            System.out.println("Same");
        }
        var p_5_45 = new PolarPoint(5, 45);
        var p_13_25 = new PolarPoint(13, -25);
        System.out.println(p_o1);
        System.out.println(p_5_45);
        System.out.println(p_13_25);

        var range1 = new Range(2, 17);
        var range2 = new Range(12, 6);
        for (int i = range1.from(); i < range1.to(); i++) {
            System.out.printf("%2d, ", i);
        }
        System.out.println();
        for (int i = range2.from(); i < range2.to(); i++) {
            System.out.printf("%2d, ", i);
        }
        System.out.println();
    }
}

record CartesianPoint(double x, double y) {
    final static CartesianPoint ORIGIN = new CartesianPoint();

    CartesianPoint() { this(0, 0); }

    double distanceFromOrigin() {
        return Math.hypot(x, y);
    }

    double distanceFromPoint(CartesianPoint p) {
        return Math.hypot(p.x - x, p.y - y);
    }

    static double distanceBetweenPoints(CartesianPoint p, CartesianPoint q) {
        return Math.hypot(p.x - q.x, p.y - q.y);
    }
}

record PolarPoint(double r, double theta) {
    final static PolarPoint ORIGIN = new PolarPoint(0, 0);
    PolarPoint() { this(0, 0); }
}

record Range(int from, int to) {
    Range {
        if (from > to) {
            int t = from;
            from = to;
            to = t;
        }
    }
}