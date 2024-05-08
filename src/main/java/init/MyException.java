package init;

public class MyException extends Exception {
    private final String path;
    private final int lineNo;

    public MyException(String msg, String path, int line) {
        super(msg);
        this.path = path;
        lineNo = line;
    }

    public String getPath() {
        return path;
    }

    public int getLineNo() {
        return lineNo;
    }
}