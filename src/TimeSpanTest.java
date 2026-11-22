public class TimeSpanTest {

    public static void main(String[] args) {
        TimeSpan span = new TimeSpan(1, 30);
        System.out.println("new TimeSpan(1, 30) -> " + span);
        System.out.println("new TimeSpan(135) -> " + new TimeSpan(135));
        span.add(0, 45);
        System.out.println("add(0, 45) -> " + span);
        span.add(new TimeSpan(20));
        System.out.println("add(TimeSpan 20m) -> " + span);
        span.subtract(1, 5);
        System.out.println("subtract(1, 5) -> " + span);
        span.scale(3);
        System.out.println("scale(3) -> " + span);
        System.out.println("getTotalMinutes() -> " + span.getTotalMinutes());
        System.out.println("getTotalHours() -> " + span.getTotalHours());
        check("new TimeSpan(-1, 10)", () -> new TimeSpan(-1, 10));
        check("new TimeSpan(2, 60)", () -> new TimeSpan(2, 60));
        check("new TimeSpan(-5)", () -> new TimeSpan(-5));
        check("add(1, -3)", () -> span.add(1, -3));
        check("add(-10)", () -> span.add(-10));
        check("subtract(0, 75)", () -> span.subtract(0, 75));
        check("subtract(-1)", () -> span.subtract(-1));
        check("subtract(5, 0)", () -> span.subtract(5, 0));
        check("subtract(271)", () -> span.subtract(271));
        check("subtract(TimeSpan 10h 0m)", () -> span.subtract(new TimeSpan(10, 0)));
        System.out.println("after failed calls span is still -> " + span);
        check("subtract(270)", () -> span.subtract(270));
        System.out.println("subtract(270) -> " + span);
    }

    private static void check(String label, Runnable action) {
        try {
            action.run();
            System.out.println(label + " -> no exception");
        } catch (RuntimeException e) {
            System.out.println(label + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
