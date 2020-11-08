# JASParse
Easy parsing of Answer Sets in in custom data structures


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
use the builder to indicate in which data structure you want to have your answer set.
