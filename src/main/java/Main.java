import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        NodeManager manager = new NodeManager(new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Node node = manager.add(random.nextInt(120));
            System.out.println(node);
        }

        System.out.println("Find 2");
        Node found = null;
        try {
            found = manager.get(2);
        } catch (NoSuchElementException e) {
            System.out.println("Не найдено");
        }
        System.out.println(found);

        System.out.println("Find max");
        System.out.println(manager.getMax());

        System.out.println("Find min");
        System.out.println(manager.getMin());

        System.out.println("Find > 50");
        System.out.println(Arrays.toString(manager.getGreaterThen(50).toArray()));


    }
}
