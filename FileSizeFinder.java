// Name: Aaron Chumm, Professor Name: Micheal Seely, Date: 10/25/2025
import java.io.File;
import java.util.Iterator;

public class FileSizeFinder {

    private static final String filePath = "FileSizeTest";

    public static void main(String[] args) {
        File rootFs = new File(filePath);
        if (!rootFs.exists() || !rootFs.isDirectory()) {
            System.out.println("The folder \"" + filePath + "\" was not found.");
            System.out.println("Place FileSizeTest in project root.");
            return;
        }
        FileTree tree = new FileTree(filePath);
        Iterator<FileNode> post = tree.postOrderIterator();
        while (post.hasNext()) {
            FileNode n = post.next();
            if (n.isDirectory()) {
                long total = 0;
                for (FileNode c : n.getChildren())
                    total += c.getSize();
                n.setSize(total);
            }
        }
        FileNode root = tree.getRoot();

        System.out.println("File size listing");
        for (FileNode child : root.getChildren()) {
            if (child.isDirectory()) {
                System.out.printf("%-80s %d%n",
                        formatPath(child.getFile(), rootFs), child.getSize());
            }
        }
        System.out.printf("%-80s %d%n",
                formatPath(root.getFile(), rootFs), root.getSize());

        System.out.println("===============================================================================================");
        System.out.println("Breadth first listing of files");
        Iterator<FileNode> bfs = tree.breadthFirstIterator();
        while (bfs.hasNext()) {
            FileNode n = bfs.next();
            System.out.printf("%-80s %d%n",
                    formatPath(n.getFile(), rootFs), n.getSize());
        }
    }
    private static String formatPath(File f, File rootFs) {
        String rootName = "D:\\\\FileSizeTest";
        String abs = f.getPath().replace('/', '\\');
        String rootAbs = rootFs.getPath().replace('/', '\\');

        if (abs.equals(rootAbs)) return rootName;
        if (abs.startsWith(rootAbs + "\\")) {
            return rootName + "\\" + abs.substring(rootAbs.length() + 1);
        }
        return rootName + "\\" + f.getName();
    }
}
