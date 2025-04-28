import ReverseModule.ReversePOA;
import java.lang.String;

class Reverseimpl extends ReversePOA {
    Reverseimpl() {
        super();
        System.out.println("Reverse Object Created");
    }

    @Override
    public String reverse_string(String name) {
        StringBuffer str = new StringBuffer(name);
        // str = new StringBuffer(str.toString().toUpperCase());  // Convert to uppercase
        str.reverse();   // Reverse the string
        
        return (("Server send: " + str));
    }
}