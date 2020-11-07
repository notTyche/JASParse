package object;

import java.util.List;
import java.util.Objects;

public class Atom implements TermBase{

    private final String identifier;
    private final List<TermBase> terms;

    public Atom(String identifier, List<TermBase> terms){

        this.identifier = identifier;

        if(terms.size() == 0)
            this.terms = null;
        else
            this.terms = terms;

    }

    @Override
    public Integer arity() {

        if (terms == null) {
            return 0;
        }
        return terms.size();
    }

    @Override
    public TermBase getTerm(int i) {

        if (terms == null) {
            return null;
        }

        return terms.get(i);
    }

    @Override
    public String identifier() {
        return identifier;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(identifier());

        if (terms.size() != 0) {

            sb.append("(").append(terms.get(0).toString());

            for (int i = 1; i < terms.size(); i++)
                sb.append(",").append(terms.get(i).toString());

            sb.append(").");

        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;


        if (!(obj instanceof Atom))
            return false;

        final Atom other = (Atom) obj;

        if (!Objects.equals(this.identifier(), other.identifier()))
            return false;

        if (!this.arity().equals(other.arity()))
            return false;

        if (this.terms != null)
            for (int i=0; i< terms.size(); i++)
                if (!this.getTerm(i).equals(other.getTerm(i)))
                    return false;

        return true;
    }

}
