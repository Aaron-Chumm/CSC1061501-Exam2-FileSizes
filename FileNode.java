// Name: Aaron Chumm, Professor Name: Micheal Seely, Date: 10/25/2025
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileNode {
    private final File file;
    private final List<FileNode> children = new ArrayList<>();
    private long size;

    public FileNode(File f) {
        if (f == null) throw new IllegalArgumentException("file is null");
        this.file = f;
    }

    public File getFile() { return file; }
    public boolean isDirectory() { return file.isDirectory(); }
    public List<FileNode> getChildren() { return children; }
    public void addChild(FileNode c) { if (c != null) children.add(c); }
    public long getSize() { return size; }
    public void setSize(long s) { this.size = s; }
}
