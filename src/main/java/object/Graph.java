package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<Vertex, List<Vertex>> adjVertices;
    private ArrayList<String> labels;

    public Graph() {

        this.adjVertices = new HashMap<>();
        this.labels = new ArrayList<>();
    }

    public void addVertex(String label) {

        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());

        if (!labels.contains(label))
            this.labels.add(label);
    }

    public void removeVertex(String label) {

        Vertex v = new Vertex(label);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));

    }

    public void addEdge(String label1, String label2) {

        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);

        if(!adjVertices.get(v1).contains(v2))
            adjVertices.get(v1).add(v2);
    }

    public void removeEdge(String label1, String label2) {

        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);

        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);

        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);

    }

    public List<Vertex> getAdjVertices(String label) {
        return adjVertices.get(new Vertex(label));
    }

    public Vertex getVertex(String label){ return new Vertex(label); }

    public int size(){ return adjVertices.size(); }

    public Integer[][] getAdjacencyMatrix(){

        Integer[][] adjMatrix = new Integer[labels.size()][labels.size()];

        for (int i = 0; i < labels.size(); ++i)
            for (int j = 0; j < labels.size(); ++j) {

                if ((this.adjVertices.get(new Vertex(labels.get(i))).contains(new Vertex(labels.get(j)))))
                    adjMatrix[i][j] = 1;
                else
                    adjMatrix[i][j] = 0;

            }

        return adjMatrix;
    }

    public boolean adj(Vertex v1, Vertex v2) {

        List<Vertex> vertexList = adjVertices.get(v1);

        for(Vertex v: vertexList)
            if(v2 == v1)
                return true;

        return false;

    }

    public void printGraph() {

        for(Vertex v : adjVertices.keySet()) {
            System.out.println(v + adjVertices.get(v).toString());
        }

    }

    class Vertex {
        String label;
        Vertex(String label) {
            this.label = label;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((label == null) ? 0 : label.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Vertex other = (Vertex) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (label == null) {
                if (other.label != null)
                    return false;
            } else if (!label.equals(other.label))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return label;
        }


        private Graph getOuterType() {
            return Graph.this;
        }
    }
}
