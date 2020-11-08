# JASParse
Easy tool to represent ASP(Answer Set Programing) program in a custom data structures

## How to use?

```java

    public static void main(String[] args) throws IOException, ParseException {

        var graph = new Builder().addRule("test.txt")
                                 .parse()
                                 .buildGraph();

        var adjMatrix = new Builder().addRule("test.txt")
                                     .parse()
                                     .buildAdjacencyMatrix();
        
    }
        
```

Import the `Builder class` and enter the name of the file to be parsed as a parameter, 
use the builder to indicate in which data structure you want your program to be represented.
