import exceptions.IllegalInputTimeSpanException;

public class TimeSpan {
    private int hours;
    private int minutes;

    public TimeSpan() {
        this.hours = 0;
        this.minutes = 0;
    }

    public TimeSpan(int minutes) {
        if (minutes < 0) throw new IllegalInputTimeSpanException("Minutes cannot be negative: " + minutes);
        this.hours = minutes / 60;
        this.minutes = minutes % 60;
    }

    public TimeSpan(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) throw new IllegalInputTimeSpanException("Invalid time: " + hours + "h " + minutes + "m");
        this.hours = hours;
        this.minutes = minutes;
    }

    public TimeSpan(TimeSpan span) {
        this.hours = span.getHours();
        this.minutes = span.getMinutes();
    }

    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }

    public void add(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) throw new IllegalInputTimeSpanException("Invalid time: " + hours + "h " + minutes + "m");
        int totalMinutes = this.getTotalMinutes() + (hours * 60 + minutes);
        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    public void add(int minutes) {
        if (minutes < 0) throw new IllegalInputTimeSpanException("Minutes cannot be negative: " + minutes);
        int totalMinutes = this.getTotalMinutes() + minutes;
        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    public void add(TimeSpan span) {
        add(span.getHours(), span.getMinutes());
    }

    public void subtract(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes > 59) throw new IllegalInputTimeSpanException("Invalid time: " + hours + "h " + minutes + "m");
        int currentTotal = this.getTotalMinutes();
        int subTotal = hours * 60 + minutes;
        if (subTotal <= currentTotal) {
            int newTotal = currentTotal - subTotal;
            this.hours = newTotal / 60;
            this.minutes = newTotal % 60;
        }
    }

    public void subtract(int minutes) {
        if (minutes < 0) throw new IllegalInputTimeSpanException("Minutes cannot be negative: " + minutes);
        int currentTotal = this.getTotalMinutes();
        if (minutes <= currentTotal) {
            int newTotal = currentTotal - minutes;
            this.hours = newTotal / 60;
            this.minutes = newTotal % 60;
        }
    }

    public void subtract(TimeSpan span) {
        subtract(span.getHours(), span.getMinutes());
    }

    public double getTotalHours() {
        return hours + (minutes / 60.0);
    }

    public int getTotalMinutes() {
        return hours * 60 + minutes;
    }

    public void scale(int factor) {
        if (factor <= 0) throw new IllegalArgumentException("Множник має бути > 0");
        int totalMinutes = this.getTotalMinutes() * factor;
        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    @Override
    public String toString() {
        return hours + "h " + minutes + "m";
    }
}
