import builder.Builder;

import java.io.*;
import java.text.ParseException;

public class Application {

    public static void main(String[] args) throws IOException, ParseException {

        var graph = new Builder().addRule("test.txt")
                                        .parse()
                                        .buildGraph();

        graph.printGraph();

        var adjMatrix= new Builder().addRule("test.txt")
                                             .parse()
                                             .buildGraph()
                                             .getAdjacencyMatrix();

        for (int i = 0; i < graph.size(); ++i) {
            for (int j = 0; j < graph.size(); ++j)
                System.out.print(adjMatrix[i][j] + " ");
            System.out.println("");
        }
    }
}
