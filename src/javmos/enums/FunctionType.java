package javmos.enums;

public enum FunctionType {
    
    ORIGINAL("original"), 
    FIRST_DERIVATIVE("first derivative"), 
    SECOND_DERIVATIVE("second derivative"), 
    THIRD_DERIVATIVE("third derivative"); 

    public final String name;

    FunctionType(String name) {
        this.name = name;
    }
        
    public static FunctionType valueOf(String color, String name) {
        switch (name) {
            case "ORIGINAL":
                return  ORIGINAL;
            case "FIRST_DERIVATIVE":
                return FIRST_DERIVATIVE;
            case "SECOND_DERIVATIVE":
                return SECOND_DERIVATIVE;
            case "THIRD_DERIVATIVE":
                return THIRD_DERIVATIVE;
            default:
                return null;
        }
    }
}