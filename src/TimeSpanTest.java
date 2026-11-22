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
    }
}
