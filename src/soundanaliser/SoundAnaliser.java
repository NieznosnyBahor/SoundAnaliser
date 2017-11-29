package soundanaliser;
/**
* 
* Główna klasa projektu. Tworzy obiekt klasy GUI 
* oraz uruchamia go (powoduje, że staje się widzialny);
* 
*/
public class SoundAnaliser {
    public static void main(String[] args) 
    {
        
        GUI gui = new GUI();
        gui.setVisible(true);

    }
}
