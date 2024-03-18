import java.util.LinkedList;
import java.util.Queue;

// Интерфейс, описывающий поведение очереди
interface QueueBehaviour<T> {
    void enqueue(T person); // Добавить человека в очередь
    T dequeue(); // Удалить человека из очереди
}

// Интерфейс, описывающий поведение магазина
interface MarketBehaviour<T> {
    void acceptOrder(T order); // Принять заказ
    T completeOrder(); // Отдать заказ
}

// Реализация класса Market, который реализует оба интерфейса
public class Market<T> implements QueueBehaviour<T>, MarketBehaviour<T> {
    private Queue<T> queue; // Очередь
    private T currentOrder; // Текущий заказ

    public Market() {
        queue = new LinkedList<>();
    }

    // Методы из интерфейса QueueBehaviour
    @Override
    public void enqueue(T person) {
        queue.add(person);
    }

    @Override
    public T dequeue() {
        return queue.poll();
    }

    // Методы из интерфейса MarketBehaviour
    @Override
    public void acceptOrder(T order) {
        if (currentOrder == null) {
            currentOrder = order;
            System.out.println("Заказ принят: " + order);
        } else {
            System.out.println("Заказ уже в обработке.");
        }
    }

    @Override
    public T completeOrder() {
        if (currentOrder != null) {
            T completedOrder = currentOrder;
            currentOrder = null;
            System.out.println("Заказ выполнен: " + completedOrder);
            return completedOrder;
        } else {
            System.out.println("Нет текущего заказа для выполнения.");
            return null;
        }
    }

    // Метод для обновления состояния магазина
    public void update() {
        if (currentOrder == null && !queue.isEmpty()) {
            T nextOrder = dequeue();
            acceptOrder(nextOrder);
        }
    }

    public static void main(String[] args) {
        Market<String> market = new Market<>();
        market.enqueue("Покупатель 1");
        market.enqueue("Покупатель 2");

        market.update(); // Принимаем первый заказ
        market.update(); // Выполняем первый заказ
        market.update(); // Принимаем второй заказ
        market.update(); // Выполняем второй заказ
    }
}