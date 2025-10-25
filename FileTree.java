// Name: Aaron Chumm, Professor Name: Micheal Seely, Date: 10/25/2025
import java.io.File;
import java.util.*;

public class FileTree {
    private final FileNode root;

    public FileTree(String path) { this(new File(path)); }

    public FileTree(File rootFile) {
        if (rootFile == null) throw new IllegalArgumentException("rootFile is null");
        this.root = buildTree(rootFile);
    }

    public FileNode getRoot() { return root; }
    private static FileNode buildTree(File f) {
        FileNode node = new FileNode(f);
        if (f.isDirectory()) {
            node.setSize(0L);
            File[] kids = f.listFiles();
            if (kids != null) {
                Arrays.sort(kids, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
                for (File k : kids) {
                    node.addChild(buildTree(k));
                }
            }
        } else {
            node.setSize(f.length());
        }
        return node;
    }

    public Iterator<FileNode> postOrderIterator() { return new PostOrderIterator(root); }

    private static final class PostOrderIterator implements Iterator<FileNode> {
        private static final class Frame {
            final FileNode n; boolean expanded;
            Frame(FileNode n) { this.n = n; }
        }
        private final Deque<Frame> stack = new ArrayDeque<>();
        PostOrderIterator(FileNode r) { if (r != null) stack.push(new Frame(r)); }
        @Override public boolean hasNext() { return !stack.isEmpty(); }

        @Override public FileNode next() {
            while (!stack.isEmpty()) {
                Frame f = stack.peek();
                if (f.n.isDirectory() && !f.expanded) {
                    f.expanded = true;
                    List<FileNode> ch = f.n.getChildren();
                    for (int i = ch.size() - 1; i >= 0; i--)
                        stack.push(new Frame(ch.get(i)));
                } else {
                    stack.pop();
                    return f.n;
                }
            }
            throw new NoSuchElementException();
        }
    }

    public Iterator<FileNode> breadthFirstIterator() { return new BreadthFirstIterator(root); }

    private static final class BreadthFirstIterator implements Iterator<FileNode> {
        private final Deque<FileNode> queue = new ArrayDeque<>();
        BreadthFirstIterator(FileNode r) { if (r != null) queue.add(r); }

        @Override public boolean hasNext() { return !queue.isEmpty(); }

        @Override public FileNode next() {
            if (queue.isEmpty()) throw new NoSuchElementException();
            FileNode n = queue.removeFirst();
            if (n.isDirectory())
                queue.addAll(n.getChildren());
            return n;
        }
    }
}
