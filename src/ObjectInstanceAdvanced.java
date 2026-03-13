/**
 * Object lifecycle, equals/hashCode, immutable vs mutable objects.
 */
import java.util.*;

public class ObjectInstanceAdvanced {

    // Mutable class (has state that can change)
    static class MutablePoint {
        int x, y;
        MutablePoint(int x, int y) { this.x=x; this.y=y; }
        void move(int dx, int dy)  { x+=dx; y+=dy; }
        @Override public String toString() { return "(" + x + "," + y + ")"; }
    }

    // Immutable class (state never changes after construction)
    static final class ImmutablePoint {
        private final int x, y;
        ImmutablePoint(int x, int y) { this.x=x; this.y=y; }
        ImmutablePoint move(int dx, int dy) { return new ImmutablePoint(x+dx, y+dy); }  // NEW object!

        @Override public boolean equals(Object o) {
            if (!(o instanceof ImmutablePoint p)) return false;
            return x==p.x && y==p.y;
        }
        @Override public int hashCode() { return Objects.hash(x,y); }
        @Override public String toString() { return "(" + x + "," + y + ")"; }
    }

    static class Student implements Comparable<Student> {
        String name; int score;
        Student(String name, int score) { this.name=name; this.score=score; }

        @Override public int compareTo(Student o) { return Integer.compare(o.score, this.score); }

        @Override public boolean equals(Object o) {
            if (!(o instanceof Student s)) return false;
            return name.equals(s.name) && score == s.score;
        }
        @Override public int hashCode() { return Objects.hash(name, score); }
        @Override public String toString() { return name + ":" + score; }
    }

    public static void main(String[] args) {
        System.out.println("=== Mutable vs Immutable ===");
        MutablePoint mp = new MutablePoint(3, 4);
        System.out.println("Mutable before: " + mp);
        mp.move(2, -1);
        System.out.println("Mutable after : " + mp + " (same object changed!)");

        ImmutablePoint ip = new ImmutablePoint(3, 4);
        ImmutablePoint ip2 = ip.move(2, -1);
        System.out.println("Immutable original: " + ip  + " (unchanged)");
        System.out.println("Immutable moved   : " + ip2 + " (new object)");

        System.out.println("\n=== equals() and hashCode() ===");
        ImmutablePoint a = new ImmutablePoint(5, 5);
        ImmutablePoint b = new ImmutablePoint(5, 5);
        System.out.println("a==b  (reference): " + (a==b));
        System.out.println("a.equals(b): "        + a.equals(b));
        System.out.println("Same hashCode: "       + (a.hashCode()==b.hashCode()));

        Set<ImmutablePoint> set = new HashSet<>();
        set.add(a); set.add(b);
        System.out.println("In HashSet — size: " + set.size() + " (deduped by equals/hashCode)");

        System.out.println("\n=== Sorting Objects (Comparable) ===");
        List<Student> students = Arrays.asList(
            new Student("Alice",88), new Student("Bob",95),
            new Student("Carol",72), new Student("Dave",95)
        );
        Collections.sort(students);
        students.forEach(System.out::println);
    }
}
