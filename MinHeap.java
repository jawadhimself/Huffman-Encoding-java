
class MinHeap {

    private TreeNode[] heap;
    private int size;

    MinHeap(int capacity) {
        heap = new TreeNode[capacity];
        size = 0;
    }

    void insert(TreeNode node) {
        heap[size] = node;
        int current = size;
        size++;

        while (current > 0 && compareNodes(heap[current], heap[(current - 1) / 2]) < 0) {
            swap(current, (current - 1) / 2);
            current = (current - 1) / 2;
        }
    }

    TreeNode remove() {
        if (size == 0) {
            return null;
        }

        TreeNode root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return root;
    }

    private void heapify(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && compareNodes(heap[left], heap[smallest]) < 0) {
            smallest = left;
        }

        if (right < size && compareNodes(heap[right], heap[smallest]) < 0) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }

    private void swap(int a, int b) {
        TreeNode temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    private int compareNodes(TreeNode a, TreeNode b) {
        if (a.freq != b.freq) {
            return Double.compare(a.freq, b.freq);
        }
        return Character.compare(a.symbol, b.symbol);
    }

    int getSize() {
        return size;
    }
}
