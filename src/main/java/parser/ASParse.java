package parser;

import object.Atom;
import object.Constant;
import object.Term;
import object.TermBase;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ASParse {

    public static TermBase parseTerm(String termString) throws ParseException {

        int pIndex = termString.indexOf("(");

        if (pIndex == -1) {
            return new Constant(termString);
        }

        String identifier = termString.substring(0, pIndex);
        String inner = termString.substring(pIndex + 1, termString.length() - 1);

        ArrayList<String> termTokens = tokenizeTerms(inner);

        List<TermBase> terms = Arrays.asList(new TermBase[termTokens.size()]);

        for (int i = 0; i < termTokens.size(); i++)
            terms.set(i,parseTerm(termTokens.get(i)));

        return new Term(identifier, terms);
    }


    public static Atom parseAtom(String atomString) throws ParseException {

        String s = atomString.trim();

        if (s.endsWith("."))
            s = s.substring(0, s.length() - 1);

        int pIndex = s.indexOf("(");

        if (pIndex == -1)
            return new Atom(s, new ArrayList<>());

        String identifier = s.substring(0, pIndex);
        String inner = s.substring(pIndex + 1, s.length() - 1);

        ArrayList<String> termTokens = tokenizeTerms(inner);

        List<TermBase> terms = Arrays.asList(new TermBase[termTokens.size()]);

        for (int i = 0; i < terms.size(); i++)
            terms.set(i, parseTerm(termTokens.get(i)));

        return new Atom(identifier, terms);
    }


    protected static ArrayList<String> tokenizeTerms(String innerStr) throws ParseException {

        char[] chars = innerStr.toCharArray();
        ArrayList<String> tokens = new ArrayList<>();
        parseStep(chars, 0, tokens, new StringBuilder(), 0);

        return tokens;
    }


    protected static void parseStep(char[] chars, int charIdx, ArrayList<String> terms, StringBuilder token, int parenDepth) throws ParseException {

        if (charIdx == chars.length) {

            if (token.length() > 0)
                terms.add(token.toString());
            return;
        }

        char c = chars[charIdx];

        if (c == '(')
            parseStep(chars, charIdx + 1, terms, token.append(c), parenDepth + 1);

        else if (c == ')') {

            if (parenDepth < 1)
                throw new ParseException("Syntax error", charIdx);

            else if (parenDepth == 1) {
                terms.add(token.append(c).toString());
                parseStep(chars, charIdx + 1, terms, new StringBuilder(), 0);
            }

            else
                parseStep(chars, charIdx + 1, terms, token.append(c), parenDepth - 1);

        }

        else if (c == ',') {

            if (parenDepth == 0) {

                if (token.length() > 0)
                    terms.add(token.toString());

                parseStep(chars, charIdx + 1, terms, new StringBuilder(), parenDepth);

            }
            else
                parseStep(chars, charIdx + 1, terms, token.append(c), parenDepth);

        }

        else
            parseStep(chars, charIdx + 1, terms, token.append(c), parenDepth);

    }

}
