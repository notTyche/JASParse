package builder;

import object.Graph;
import object.Pair;
import object.TermBase;
import parser.ASParse;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Builder {

    private Graph graph;
    private ArrayList<Pair<ArrayList<TermBase>,ArrayList<TermBase>>> ruleSet;
    private ArrayList<Pair<ArrayList<String>,ArrayList<String>>> rule;

    public Builder(){
        this.graph = new Graph();
        this.ruleSet = new ArrayList<>();
        this.rule = new ArrayList<>();
    }

    public Builder addRule(String fileName) {

       ArrayList<String> lines = new ArrayList<>();

        try {

            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            String s = buffer.readLine();

            while( s != null) {
                lines.add(s);
                s = buffer.readLine();
            }

        } catch (IOException ignored) {
            System.out.println("File not found");
        }


        for (String line : lines) {

            ArrayList<String> stringBody = new ArrayList<>();
            ArrayList<String> stringHead = new ArrayList<>();

            if(line.length() > 0) {

                String[] s = line.split("\\s*:-\\s*");

                String head = s[0];
                String body;


                if (s.length > 1) {

                    body = s[1];
                    s = body.split("\\)\\s*,\\s*");

                    for (String s1 : s) {

                        if (s1.charAt(s1.length() - 1) == '.')
                            s1 = s1.substring(0, s1.length() - 1);

                        else if (s1.charAt(s1.length() - 1) != ')')
                            s1 = s1.concat(")");

                        stringBody.add(s1);
                    }
                }

                s = head.split("\\)\\s*\\|\\s*");

                for (String s1 : s) {

                    if (s1.charAt(s1.length() - 1) != ')' && s1.charAt(s1.length() - 1) != '.' )
                        s1 = s1.concat(")");

                    stringHead.add(s1);
                }

                rule.add(new Pair<>(stringHead,stringBody));

            }

        }

        return this;
    }

    public Builder parse() throws ParseException {

        for(Pair<ArrayList<String>, ArrayList<String>> container : this.rule){

            ArrayList<TermBase> parseHead = new ArrayList<>();
            ArrayList<TermBase> parseBody = new ArrayList<>();

            ArrayList<String> head =  container.getFirst();
            ArrayList<String> body =  container.getSecond();

            for( String h : head)
                parseHead.add(ASParse.parseAtom(h));

            for( String b : body)
                parseBody.add(ASParse.parseAtom(b));

            this.ruleSet.add(new Pair<>(parseHead,parseBody));

        }

        return this;
    }

    public Graph buildGraph(){

        for (Pair<ArrayList<TermBase>, ArrayList<TermBase>> container : this.ruleSet){

            ArrayList<TermBase> head = container.getFirst();
            ArrayList<TermBase> body = container.getSecond();

            for(TermBase atomHead : head)
                for (TermBase atomBody : body){
                    this.graph.addVertex(atomHead.identifier());
                    this.graph.addVertex(atomBody.identifier());

                    this.graph.addEdge(atomHead.identifier(), atomBody.identifier());

                }

        }

        return this.graph;
    }

    public Integer[][] buildAdjacencyMatrix(){
        buildGraph();
        return this.graph.getAdjacencyMatrix();
    }

}
