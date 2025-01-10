package org.example;

public class FatalError extends Exception {
    private final Throwable cause;

    public FatalError(Throwable cause){
        super(cause);
        this.cause=cause;
    }

    @Override
    public void printStackTrace(){
        String programRunPhase = switch (Runner.programRunProgress) {
            case 0 -> "Before mode selection";
            case 1 -> "Encryption";
            case 2 -> "After Encryption";
            case 3 -> "PDF Encryption";
            case 4 -> "After PDF Encryption";
            case 5 -> "Decryption";
            case 6 -> "After decryption";
            default -> "Unable to fetch program run state";
        };
        System.out.println(
                "A fatal error was found during program execution process!\n"+
                "    with a program run state of "+Runner.programRunProgress+" ("+programRunPhase+")\n"
        );
        this.cause.printStackTrace();
    }
}
