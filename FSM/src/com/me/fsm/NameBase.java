package com.me.fsm;

import java.util.StringTokenizer;

public class NameBase {
    private String name;
    private String toStringName;

    public NameBase(String name) {
        this.name = name;
        toStringName = getPreferredClassName() + "[" + name + "]";
    }

    // Return just the name of the class, not the full qualified name.
    private String getClassName() {
        String fqn = this.getClass().getName();
        StringTokenizer st = new StringTokenizer(fqn, ".");
        String token = st.nextToken();
        while (st.hasMoreTokens())
            token = st.nextToken();
        return token;
    }

    private String getPreferredClassName() {
        if (this instanceof Action)
            return "Action";
        if (this instanceof State)
            return "State";
        if (this instanceof Guard)
            return "Guard";
        if (this instanceof Input)
            return "Input";
        return getClassName();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return toStringName;
    }
}
