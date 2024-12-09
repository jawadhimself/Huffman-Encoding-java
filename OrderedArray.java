
class OrderedArray {

    private TreeNode[] array;
    private int size;

    OrderedArray(int capacity) {
        array = new TreeNode[capacity];
        size = 0;
    }

    void insert(TreeNode node) {
        int i = size - 1;
        while (i >= 0 && compareNodes(array[i], node) > 0) {
            array[i + 1] = array[i];
            i--;
        }
        array[i + 1] = node;
        size++;
    }

    TreeNode remove() {
        if (size == 0) {
            return null;
        }
        return array[--size];
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