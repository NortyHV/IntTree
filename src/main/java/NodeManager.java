
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class NodeManager {

    private Node root;

    private Comparator<Node> comparator;

    public NodeManager(Comparator<Node> comparator) {
        this.comparator = comparator;
    }

    public Node add(Integer value) {
        // Проверяем корень дерева на null
        if (this.root == null) {
            // Если корень null, то создаем его, инициализируя переданным занчением
            this.root = new Node(value);
            // Возвращаем созданную ноду
            return this.root;
        }
        // Есали корень не null, то ищем для него место в дереве
        return getNext(this.root, value);
    }

    public Node get(Integer value) throws NoSuchElementException {
        return get(root, value);
    }

    public Node getMax(){
        return getMaxMin(true);
    }

    public Node getMin(){
        return getMaxMin(false);
    }

    public List<Integer> getGreaterThen(Integer value) {
        List<Integer> rezult = new ArrayList<Integer>();
        Node node;
        try {
            node = get(value);
        } catch (NoSuchElementException e) {
            return rezult;
        }

        //rezult.add(node.getValue());
        do {
            node = node.getRight();
            if (node != null) {
                rezult.add(node.getValue());
            }
        } while (node != null);
        return rezult;
    }

    private Node getMaxMin(boolean isMax){
        Node node = null, next = root;
        do {
            node = next;
            if (isMax) {
                next = node.getRight();
            } else {
                next = node.getLeft();
            }
        } while (next != null);
        return node;
    }



    private Node get(Node node, Integer value) throws NoSuchElementException {
        Direction direction = getDirection(value, node);
        switch (direction) {
            // Если новое положение слева
            case LEFT: {
                // Проверяем у текущей ноды в дереве, свободно ли
                // у нее место слева
                if (node.getLeft() != null) {
                    // Если место не свободно, то рекурсивно вызываем
                    // этот же метод, нов качестве корня передаем следующую ноду
                    // расположенную СЛЕВА
                    return get(node.getLeft(), value);
                } else {
                    throw new NoSuchElementException("");
                }
            }
            case RIGHT: {
                if (node.getRight() != null) {
                    return get(node.getRight(), value);
                } else {
                    throw new NoSuchElementException("");
                }
            }
            default: return node;
        }

    }


    /**
     *
     * @param root - Текущая проверяемая нода, т.е. нода для которой
     *             пытаемся добавить элемент в качестве наследника
     * @param value - значение добавляемого элемента
     * @return - вновь созданную ноду
     */
    private Node getNext(Node root, Integer value) {
        // Определяем положение новой ноды в дереве (Слева или справа)
        Direction direction = getDirection(value, root);
        switch (direction) {
            // Если новое положение слева
            case LEFT: {
                // Проверяем у текущей ноды в дереве, свободно ли
                // у нее место слева
                if (root.getLeft() != null) {
                    // Если место не свободно, то рекурсивно вызываем
                    // этот же метод, нов качестве корня передаем следующую ноду
                    // расположенную СЛЕВА
                    return getNext(root.getLeft(), value);
                } else {
                    // Если место свободно, то создаем на этом месте новую ноду
                    Node node = new Node(value, root, Direction.LEFT);
                    // Родительской ноде назначаем наследника слева
                    root.setLeft(node);
                    // Возвращаяю вновь созданную ноду
                    return node;
                }
            }
            case RIGHT: {
                if (root.getRight() != null) {
                    return getNext(root.getRight(), value);
                } else {
                    Node node = new Node(value, root, Direction.RIGHT);
                    root.setRight(node);
                    return node;
                }
            }
            default: return root;
        }
    }

    private Direction getDirection(Integer value, Node comparingNode) {
        int compareResult = comparator.compare(comparingNode, new Node(value));
        return compareResult > 0 ? Direction.RIGHT :
                compareResult < 0 ? Direction.LEFT :
                        Direction.EQUAL;
    }
}