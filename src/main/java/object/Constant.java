package object;

import java.util.Objects;

public class Constant implements TermBase {

    private final String identifier;

    public Constant(String identifier) {

        this.identifier = identifier;

    }

    @Override
    public Integer arity() {
        return 0;
    }

    @Override
    public TermBase getTerm(int i) {
        return null;
    }

    @Override
    public String identifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (!(obj instanceof Constant))
            return false;

        final Constant other = (Constant) obj;

        return Objects.equals(this.identifier, identifier());
    }

}
