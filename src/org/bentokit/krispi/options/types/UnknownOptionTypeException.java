package org.bentokit.krispi.options.types;

public class UnknownOptionTypeException extends Exception {
    public UnknownOptionTypeException(String name) { 
        super("No such Option Type \""+name+"\"");
    }
}
