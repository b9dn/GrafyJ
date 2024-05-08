package init;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadGraph {

    private static final int MAX_CONNECTIONS = 4;
    private static final int MAX_VALUES = 4;

    public static Graph readGraph(String path) throws IOException, MyException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Read dimensions from the first line
            String line = br.readLine();
            int[] dimensions = parseDimensions(line, path);
            Graph graph = new Graph(dimensions[0], dimensions[1]);

            // Parse each line to form the graph
            for (int i = 0; i < dimensions[0] * dimensions[1]; i++) {
                line = br.readLine();
                if (line != null) {
                    parseGraphLine(graph, i, line, path);
                }
            }
            return graph;
        } catch (IOException e) {
            throw new MyException("Error reading file: " + e.getMessage(), path, 0);
        }
    }

    private static int[] parseDimensions(String line, String path) throws MyException {
        if (line != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 2) {
                try {
                    int h = Integer.parseInt(parts[0]);
                    int w = Integer.parseInt(parts[1]);
                    return new int[]{h, w};
                } catch (NumberFormatException e) {
                    throw new MyException("Invalid dimensions format", path, 0);
                }
            }
        }
        throw new MyException("Dimensions missing in the first line", path, 0);
    }

    private static void parseGraphLine(Graph graph, int nodeIndex, String line, String path) throws MyException {
        String[] fields = line.trim().split("\\s+");
        int connectionCount = 0;
        int valueCount = 0;
        boolean isFirstConnection = true;

        for (String field : fields) {
            if (connectionCount > MAX_CONNECTIONS || valueCount > MAX_VALUES) {
                break;
            }
            try (Scanner scanner = new Scanner(field)) {
                if (scanner.hasNextInt()) {
                    handleIntegerField(graph, nodeIndex, connectionCount, scanner, path);
                    connectionCount++;
                } else if (field.contains(":") && field.split(":").length == 2) {
                    try {
                        double val = handleStringField(graph, nodeIndex, valueCount, field, path);
                        valueCount++;
                        updateEdgeValues(graph, val, isFirstConnection);
                        isFirstConnection = false;
                    } catch (NumberFormatException e) {
                        throw new MyException("Invalid edge weight format in line " + (nodeIndex + 2), path, nodeIndex + 2);
                    }
                } else {
                    throw new MyException("Invalid character or format in line " + (nodeIndex + 2), path, nodeIndex + 2);
                }
            } catch (NumberFormatException e) {
                throw new MyException("Invalid number format in line " + (nodeIndex + 2), path, nodeIndex + 2);
            }
        }
    }

    private static void handleIntegerField(Graph graph, int nodeIndex, int connectionIndex, Scanner scanner, String path) throws MyException {
        int connection = scanner.nextInt();
        if (connection >= 0 && connection < graph.getHeight() * graph.getWidth()) {
            graph.connectNode(nodeIndex, connectionIndex, connection);
        } else {
            throw new MyException("Invalid connection index in line " + (nodeIndex + 2), path, nodeIndex + 2);
        }
    }

    private static double handleStringField(Graph graph, int nodeIndex, int valueIndex, String field, String path) throws MyException {
        String[] parts = field.split(":");
        double value = Double.parseDouble(parts[1]);
        if (value < 0) {
            throw new MyException("Negative edge weight in line " + (nodeIndex + 2), path, nodeIndex + 2);
        }
        graph.setVal(nodeIndex, valueIndex, value);
        return value;
    }

    private static void updateEdgeValues(Graph graph, double value, boolean isFirstConnection) {
        if (value > graph.getMaxValEdg()) {
            graph.setMaxValEdg(value);
            if (isFirstConnection) {
                graph.setMinValEdg(value);
            }
        } else if (value < graph.getMinValEdg()) {
            graph.setMinValEdg(value);
        }
    }
}
