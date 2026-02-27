import jason.asSyntax.Literal;
import jason.asSyntax.Structure;
import jason.environment.Environment;

public class FactorialEnv extends Environment {

    Literal ld  = Literal.parseLiteral("fact");

    @Override
    public void init(String[] args) {
        // initial percepts
        addPercept(ld);
    }


}
