package init;

public class Queue {
    private Queue_node[] queue;
    private int size;
    private int actualSize;

    public int getActualSize() {
        return actualSize;
    }

    public int getSize() {
        return size;
    }

    public Queue() {
        queue = new Queue_node[8];
        size = 0;
        actualSize = 8;
    }

    public Queue_node[] getQueue() {
        return queue;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addToQueue(Queue_node element) {
        if (size + 1 > actualSize)
            doubleQueueSize();
        queue[size++] = element;
        heapUp();
    }

    public Queue_node popFromQueue() {
        Queue_node ret = queue[0];
        queue[0] = queue[--size];
        heapDown();

        return ret;
    }

    private void doubleQueueSize() {
        Queue_node[] tmpq = new Queue_node[actualSize * 2];
        System.arraycopy(queue, 0, tmpq, 0, size);
        queue = tmpq;
        actualSize *= 2;
    }

    private void heapUp() {
        int i = size - 1;
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (queue[parent].odl <= queue[i].odl)
                return;
            Queue_node tmp = queue[parent];
            queue[parent] = queue[i];
            queue[i] = tmp;
            i = parent;
        }
    }

    private void heapDown() {
        int i = 0;
        int child = 2 * i + 1;
        while (child < size) {
            if (child + 1 < size && queue[child + 1].odl < queue[child].odl)
                child++;
            if (queue[i].odl <= queue[child].odl)
                return;
            Queue_node tmp = queue[i];
            queue[i] = queue[child];
            queue[child] = tmp;
            i = child;
            child = 2 * i + 1;
        }
    }
}
