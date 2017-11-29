package soundanaliser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
/** 
 * Abstarkcyjna klasa DaneMiernika służy do obsługi zdarzeń zainicjalizowynych przez klasę GUI.
 * <p>
 * Posiada pola i metody pozwalające na:
 * <p> - wczytanie danych z pliku wskazanego przez użytkownika
 * <p> - sprawdzenie danych pod kątem poprawności formatu
 * <p> - generowanie danych
 * <p> - zapis danych do pliku
*/
public abstract class DaneMiernika
{
    static List lista;
    static int dlugoscListyWKlasie;
    static String rduze = "Ryzyko duże";
    static String rsrednie = "Ryzyko średnie";
    static String rmale = "Ryzyko małe";
    public static boolean valid = true;

    static private String TablicaSurowychDanych[];
    
    static private int TablicaDataY[];
    static private int TablicaDataM[];
    static private int TablicaDataD[];
    static private int TablicaGodzinaH[];
    static private int TablicaGodzinaM[];
    static private int TablicaGodzinaS[];
    static private double TablicaSPL[];
    static private double TablicaSPLSorted[];
    static private String TablicaCharakter[];
    static private String TablicaCzasuPomiaru[];
    static private String TablicaPoprawnosci[];
    static private BigDecimal TablicaPoziomRownowazny[];

    
//    
//      Pola dot. miernika
//    
    static private double czasEkspozycji = 0;
    static private double zrownowazonyPoziomA = 0;
    static private double maksymalnyPoziomA = 0;
    static private double szczytowyPoziomC = 0;
    
    static private double wartoscOczekiwana = 0;
    static private double odchylenieStandardowe = 0;
    static private double wariancja = 0;
    static private double mediana = 0;
    static private double kwartyl1 = 0;
    static private double kwartyl3 = 0;
    static private double minimum = 0;
    static private double maximum = 0;
    static private double poziomEkspozycji8h = 0;
    static private double poziomEkspozycji24h = 0;
    static private BigDecimal BDpoziomEkspozycji8h;
    static private BigDecimal BDpoziomEkspozycji24h;
    static private String ocenaSzkodliwosci[] = new String[9];
    static private String charakterystyka; 
    
    
    
//
//      Metody
//
    
    /**
     * Szereg metod sprawdzający poprawność danych
     */
    static void MainCheckCode()                     {
        
        StworzTablice();
        DekodujDate();
        DekodujGodzine();
        DekodujPoziomSPL();
        DekodujCharakterystyke();
        DekodujCzasPomiaru();
        DekodujPoprawnoscPomiaru();
        SprawdzDane();
        
    }
    /**
     * Tworzy zestaw tablic używanych przez program
     */
    static void StworzTablice()                     {

    TablicaDataY        = new int[dlugoscListyWKlasie];
    TablicaDataM        = new int[dlugoscListyWKlasie];
    TablicaDataD        = new int[dlugoscListyWKlasie];
    TablicaGodzinaH     = new int[dlugoscListyWKlasie];
    TablicaGodzinaM     = new int[dlugoscListyWKlasie];
    TablicaGodzinaS     = new int[dlugoscListyWKlasie];
    TablicaSPL          = new double[dlugoscListyWKlasie];
    TablicaCharakter    = new String[dlugoscListyWKlasie];
    TablicaCzasuPomiaru = new String[dlugoscListyWKlasie];
    TablicaPoprawnosci  = new String[dlugoscListyWKlasie];
    }
    /**
     * Zmienia Liste w Tablice o ustalonym rozmiarze
     * @param lista
     * @param dlugoscListy 
     */
    static void PobierzTablice(List<String> lista, int dlugoscListy) {
        dlugoscListyWKlasie = lista.size();
        TablicaSurowychDanych = new String[dlugoscListy];
        for(int licznik = 0; licznik < dlugoscListy; licznik++)
        {
            TablicaSurowychDanych[licznik] = lista.get(licznik);
        }
    }
    /**
     * Wyłuskuje date z Tablicy surowych danych
     */
    
    static private void DekodujDate()               {
        String tymczasowyString;
        char tab = '\t';
        char dwukropek = ':';
        char myslnik = '-';
        try{
            for(int licznik = 0; licznik < dlugoscListyWKlasie; licznik++)
            {
            tymczasowyString = TablicaSurowychDanych[licznik];
            String[] parts1;
            parts1 = tymczasowyString.split(Character.toString(tab));
            String[] parts2;
            parts2 = parts1[0].split(Character.toString(myslnik));
            
            TablicaDataY[licznik] = Integer.parseInt(parts2[0]);
            TablicaDataM[licznik] = Integer.parseInt(parts2[1]);
            TablicaDataD[licznik] = Integer.parseInt(parts2[2]);
            }
            
            //
            //  Pętla obroci tyle razy, ile linijek jest w pliku. 
            //  do tymczasowegoStringu ładowany jest string kolejnej linijki z pliku
            //  tworzona jest tablica Stringow "parts", która bedzie przechowywała podzielone czesci stringa
            //  

        }
        catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Zły format daty");            
            }
    }
    /**
     * Wyłuskuje godzine z Tablicy surowych danych
     */
    static private void DekodujGodzine()            {
        String tymczasowyString;
        char tab = '\t';
        char dwukropek = ':';
        char myslnik = '-';
        try{
            for(int licznik = 0; licznik < dlugoscListyWKlasie; licznik++)
            {
                tymczasowyString = TablicaSurowychDanych[licznik];
                String[] parts1;
                parts1 = tymczasowyString.split(Character.toString(tab));
                String[] parts2;
                parts2 = parts1[1].split(Character.toString(dwukropek));
            
                TablicaGodzinaH[licznik] = Integer.parseInt(parts2[0]);
                TablicaGodzinaM[licznik] = Integer.parseInt(parts2[1]);
                TablicaGodzinaS[licznik] = Integer.parseInt(parts2[2]);
            
            //
            //  Pętla obroci tyle razy, ile linijek jest w pliku. 
            //  do tymczasowegoStringu ładowany jest string kolejnej linijki z pliku
            //  tworzona jest tablica Stringow "parts", która bedzie przechowywała podzielone czesci stringa
            //  

            }
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Zły format godziny");
        }
        
    }
    /**
     * Wyłuskuje poziom SPL z Tablicy surowych danych
     */
    static private void DekodujPoziomSPL()          {
        String tymczasowyString;
        char tab = '\t';
        char dwukropek = ':';
        char myslnik = '-';
        try{
        for(int licznik = 0; licznik < dlugoscListyWKlasie; licznik++)
        {
            tymczasowyString = TablicaSurowychDanych[licznik];
            String[] parts2;
            parts2 = tymczasowyString.split(Character.toString(tab));
            TablicaSPL[licznik] = Double.parseDouble(parts2[2].replace(',', '.'));

            //
            //  Pętla obroci tyle razy, ile linijek jest w pliku. 
            //  do tymczasowegoStringu ładowany jest string kolejnej linijki z pliku
            //  tworzona jest tablica Stringow "parts", która bedzie przechowywała podzielone czesci stringa
            //  

        }
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Zły format poziomu SPL");
        }
        
    }
    /**
     * Wyłuskuje charakterystyke z Tablicy surowych danych
     */
    static private void DekodujCharakterystyke()    {
        String tymczasowyString;
        char tab = '\t';
        char dwukropek = ':';
        char myslnik = '-';
        try{
        for(int licznik = 0; licznik < dlugoscListyWKlasie; licznik++)
        {
            tymczasowyString = TablicaSurowychDanych[licznik];
            String[] parts1;
            parts1 = tymczasowyString.split(Character.toString(tab));
            
            
            TablicaCharakter[licznik] = parts1[3];

            charakterystyka = TablicaCharakter[0];
            //
            //  Pętla obroci tyle razy, ile linijek jest w pliku. 
            //  do tymczasowegoStringu ładowany jest string kolejnej linijki z pliku
            //  tworzona jest tablica Stringow "parts", która bedzie przechowywała podzielone czesci stringa
            //  

        }
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Zły format Charakterystyki");
        }
    }
    /**
     * Wyłuskuje czas pomiaru z Tablicy surowych danych
     */
    static private void DekodujCzasPomiaru()        {
        String tymczasowyString;
        char tab = '\t';
        char dwukropek = ':';
        char myslnik = '-';
        for(int licznik = 0; licznik < dlugoscListyWKlasie; licznik++)
        {
            tymczasowyString = TablicaSurowychDanych[licznik];
            String[] parts1;
            parts1 = tymczasowyString.split(Character.toString(tab));
            
            
            TablicaCzasuPomiaru[licznik] = parts1[4];

            
            //
            //  Pętla obroci tyle razy, ile linijek jest w pliku. 
            //  do tymczasowegoStringu ładowany jest string kolejnej linijki z pliku
            //  tworzona jest tablica Stringow "parts", która bedzie przechowywała podzielone czesci stringa
            //  

        }
    }
    /**
     * Wyłuskuje poprawnosc z Tablicy surowych danych
     */
    static private void DekodujPoprawnoscPomiaru()  {
        String tymczasowyString;
        char tab = '\t';
        char dwukropek = ':';
        char myslnik = '-';
        for(int licznik = 0; licznik < dlugoscListyWKlasie; licznik++)
        {
            tymczasowyString = TablicaSurowychDanych[licznik];
            String[] parts1;
            parts1 = tymczasowyString.split(Character.toString(tab));
            
            
             TablicaPoprawnosci[licznik] = parts1[5];

            
            //
            //  Pętla obroci tyle razy, ile linijek jest w pliku. 
            //  do tymczasowegoStringu ładowany jest string kolejnej linijki z pliku
            //  tworzona jest tablica Stringow "parts", która bedzie przechowywała podzielone czesci stringa
            //  

        }
    }
    /**
     * Sprawdzenie danych
     */
    static private void SprawdzDane()               {
        
        testFormatDaty();
        testFormtGodziny();
        testPoziomSPL();
        testCharakterystyka();
        testPoprawnosciPomiaru();
        testDaneChronologiczne();
        if(valid == true)
        {JOptionPane.showMessageDialog(null, "Plik przeszedł testy.");}
        else {
            JOptionPane.showMessageDialog(null, "Plik nie przeszedł testów.");
            
        }
    }
    /**
     * Generowanie danych
     */

    public static void MainGenerateCode()           {
        
        PoliczCzasEkspozycji();
        PoliczMaksymalnyPoziomA();
        PoliczSzczytowyPoziomC();
        PoliczWartoscOczekiwana();
        PoliczOdchylenieStandardowe();
        PoliczWariancja();
        PosortujTablice();
        PoliczMediana();
        PoliczKwartyl1();
        PoliczKwartyl3();
        PoliczMinimum();
        PoliczMaximum();
        PoliczPoziomRownowazny();
        PoliczPoziomEkspozycji8h();
        PoliczPoziomEkspozycji24h();
        PoliczOceneSzkodliwosci();
        
    }
    /**
     * Liczy czas ekspozycji
     */
    
    private static void PoliczCzasEkspozycji()      {       
        
        for(int licznik = 0; licznik < dlugoscListyWKlasie; licznik++)
        {
            if("SLOW".equals(TablicaCzasuPomiaru[licznik]))
            {
                czasEkspozycji += 1;
            }
            else if("FAST".equals(TablicaCzasuPomiaru[licznik]))
            {
                czasEkspozycji += 0.125;
            }
            else
            {
                System.out.println("Wykryto błąd przy liczeniu czasu ekspozycji");
            }
        }
    }
    /**
     * Liczy maksymalny poziom A
     */
    private static void PoliczMaksymalnyPoziomA()   {
        if("C".equals(charakterystyka));
        {
            maksymalnyPoziomA = 0;
        }
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            if(TablicaSPL[a] > maksymalnyPoziomA && "A".equals(TablicaCharakter[a]))
            {
                maksymalnyPoziomA = TablicaSPL[a];
            }
        }
    }
    /**
     * Liczy szczytowy poziom C
     */
    private static void PoliczSzczytowyPoziomC()    {
        if("A".equals(charakterystyka));
        {
            szczytowyPoziomC = 0;
        }
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            if(TablicaSPL[a] > szczytowyPoziomC && "C".equals(TablicaCharakter[a]))
            {
                szczytowyPoziomC = TablicaSPL[a];
            }
        }
    }
    /**
     * Liczy Wartosc oczekiwaną
     */
    private static void PoliczWartoscOczekiwana()   {
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            wartoscOczekiwana += TablicaSPL[a];
        }
        wartoscOczekiwana /= dlugoscListyWKlasie;

    }
    /**
     * Liczy odchylenie standardowe
     */
    private static void PoliczOdchylenieStandardowe(){
        for(int a=0; a<dlugoscListyWKlasie;a++)
        {
            odchylenieStandardowe += (TablicaSPL[a] - wartoscOczekiwana);
        }
        odchylenieStandardowe /= dlugoscListyWKlasie;
    }
    /**
     * Liczy wariancje
     */
    private static void PoliczWariancja()           {
        
        for(int a=0; a<dlugoscListyWKlasie;a++)
        {
            wariancja += Math.pow(TablicaSPL[a] - wartoscOczekiwana, 2.0);
        }
        wariancja /= dlugoscListyWKlasie;
    }
    /**
     * Sortuje nową tablice TablicaSPLSorted
     */
    private static void PosortujTablice()           {
        TablicaSPLSorted = new double[dlugoscListyWKlasie];
        for(int a = 0; a < dlugoscListyWKlasie; a++)
        {
            TablicaSPLSorted[a] = TablicaSPL[a];
        }
        // Sortowanie właściwe
        double curr;
        for(int a=0; a<TablicaSPLSorted.length; a++)
        {
            for(int aa = 0, ab = 1; aa<TablicaSPLSorted.length-1 || ab < TablicaSPLSorted.length; aa++, ab++)
            {
                if(TablicaSPLSorted[aa] < TablicaSPLSorted[ab])
                {
                    curr = TablicaSPLSorted[aa];
                    TablicaSPLSorted[aa] = TablicaSPLSorted[ab];
                    TablicaSPLSorted[ab] = curr;
                }
            }
        }
    }
    /**
     * Wykorzystuje posortowaną tablice aby policzyc mediane
     */
    private static void PoliczMediana()             {
        int liczb;
        if(dlugoscListyWKlasie %2 == 0)
        {
            mediana = (TablicaSPLSorted[dlugoscListyWKlasie/2] + TablicaSPLSorted[(dlugoscListyWKlasie/2)-1]) / 2;
        }
        else
        {
            mediana = TablicaSPLSorted[dlugoscListyWKlasie/2];
        }
    }
    /**
     * Wykorzystuje posortowaną tablice aby policzyc kwartyl1
    */
    private static void PoliczKwartyl1()            {
        int kw;
        kw = (int) (dlugoscListyWKlasie * 0.75 -1);
        kwartyl1 = TablicaSPLSorted[kw];
    }
    /**
    * Wykorzystuje posortowaną tablice aby policzyc kwartyl3
    */
    private static void PoliczKwartyl3()            {
        int kw;
        kw = (int) (dlugoscListyWKlasie * 0.25 -1);
        kwartyl3 = TablicaSPLSorted[kw];
    }
    /**
     * Liczy wartosc minimalna poziomu SPL
    */
    private static void PoliczMinimum()             {
        minimum = TablicaSPL[0];
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            if(TablicaSPL[a] < minimum)
            {
                minimum = TablicaSPL[a];
            }
        }
    }
    /**
     * Liczy wartosc maksymalna poziomu SPL
    */     
    private static void PoliczMaximum()             {
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            if(TablicaSPL[a] > maximum)
            {
                maximum = TablicaSPL[a];
            }
        }
    }
    /**
     * Liczy poziom równowazny
     */
    private static void PoliczPoziomRownowazny()    {
        
        
        TablicaPoziomRownowazny = new BigDecimal[dlugoscListyWKlasie];
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            TablicaPoziomRownowazny[a] = new BigDecimal(Math.pow(10, 0.1 * TablicaSPL[a]));
        }
        BigDecimal suma = new BigDecimal("0");
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            suma = suma.add(TablicaPoziomRownowazny[a]);
        }
        double sumaDouble = suma.doubleValue();
        zrownowazonyPoziomA = Math.log10((1/czasEkspozycji) * sumaDouble) * 10;
    }
    
    /**
     * Liczy poziom poziom ekspozycji na dzwiek w okresie 8h
     */
    private static void PoliczPoziomEkspozycji8h()  {
        poziomEkspozycji8h = zrownowazonyPoziomA + (Math.log10(czasEkspozycji / 28800) * 10);
        
        BDpoziomEkspozycji8h = new BigDecimal(Double.toString(poziomEkspozycji8h));
    }
    /**
     * Liczy poziom poziom ekspozycji na dzwiek w okresie 24h
     */    
    private static void PoliczPoziomEkspozycji24h() {
        poziomEkspozycji24h = Math.pow(10, -5) * Math.pow(10, 0.1 * poziomEkspozycji8h) * 1.15;
        BDpoziomEkspozycji24h = new BigDecimal(Double.toString(poziomEkspozycji24h));
    }
    /**
     * Liczy poziom szkodliwości dla osób pracujących, dla kobiet w ciązy oraz osób młodocianych
     */
    private static void PoliczOceneSzkodliwosci()   {
        //tab[0]
        if(poziomEkspozycji8h <= 42.5)
        {
            ocenaSzkodliwosci[0] = rmale;
        }
        else if(poziomEkspozycji8h <= 85)
        {
            ocenaSzkodliwosci[0] = rsrednie;
        }
        else
        {
            ocenaSzkodliwosci[0] = rduze;
        }
        
        //tab[1]
        if("C".equals(charakterystyka))
        {
            ocenaSzkodliwosci[1] = "Brak danych";
        }
        else
        {
            if(maksymalnyPoziomA <= 57.5)
            {
                ocenaSzkodliwosci[1] = rmale;
            }
            else if(maksymalnyPoziomA <= 115)
            {
                ocenaSzkodliwosci[1] = rsrednie;
            }
            else
            {
                ocenaSzkodliwosci[1] = rduze;
            }
        }
        
        //tab[2]
        if("A".equals(charakterystyka))
        {
            ocenaSzkodliwosci[2] = "Brak danych";
        }
        else
        {
            if(szczytowyPoziomC <= 67.5)
            {
                ocenaSzkodliwosci[2] = rmale;
            }
            else if(szczytowyPoziomC <= 135)
            {
                ocenaSzkodliwosci[2] = rsrednie;
            }
            else
            {
                ocenaSzkodliwosci[2] = rduze;
            }
        }    
        //tab[3]
        if(poziomEkspozycji8h <= 40)
        {
            ocenaSzkodliwosci[3] = rmale;
        }
        else if(poziomEkspozycji8h <= 80)
        {
            ocenaSzkodliwosci[3] = rsrednie;
        }
        else
        {
            ocenaSzkodliwosci[3] = rduze;
        }
                
        //tab[4]
        if("C".equals(charakterystyka))
        {
            ocenaSzkodliwosci[4] = "Brak danych";
        }
        else
        {
            if(maksymalnyPoziomA <= 55)
            {
                ocenaSzkodliwosci[4] = rmale;
            }
            else if(maksymalnyPoziomA <= 110)
            {
                ocenaSzkodliwosci[4] = rsrednie;
            }
            else
            {
                ocenaSzkodliwosci[4] = rduze;
            }
        }    
        //tab[5]
        if("A".equals(charakterystyka))
        {
            ocenaSzkodliwosci[5] = "Brak danych";
        }
        else
        {
            if(szczytowyPoziomC <= 65)
            {
                ocenaSzkodliwosci[5] = rmale;
            }
            else if(szczytowyPoziomC <= 130)
            {
                ocenaSzkodliwosci[5] = rsrednie;
            }
            else
            {
                ocenaSzkodliwosci[5] = rduze;
            }
        }
        
                
        //tab[6]
        if(poziomEkspozycji8h <= 32.5)
        {
            ocenaSzkodliwosci[6] = rmale;
        }
        else if(poziomEkspozycji8h <= 65)
        {
            ocenaSzkodliwosci[6] = rsrednie;
        }
        else
        {
            ocenaSzkodliwosci[6] = rduze;
        }
                
        //tab[7]
        
        if("C".equals(charakterystyka))
        {
            ocenaSzkodliwosci[7] = "Brak danych";
        }
        else
        {
            if(maksymalnyPoziomA <= 55)
            {
                ocenaSzkodliwosci[7] = rmale;
            }
            else if(maksymalnyPoziomA <= 110)
            {
                ocenaSzkodliwosci[7] = rsrednie;
            }
            else
            {
                ocenaSzkodliwosci[7] = rduze;
            }
        }   
        //tab[8]
        if("A".equals(charakterystyka))
        {
            ocenaSzkodliwosci[8] = "Brak danych";
        }
        else
        {
            if(szczytowyPoziomC <= 65)
            {
                ocenaSzkodliwosci[8] = rmale;
            }
            else if(szczytowyPoziomC <= 130)
            {
                ocenaSzkodliwosci[8] = rsrednie;
            }
            else
            {
                ocenaSzkodliwosci[8] = rduze;
            }
        }
    }
    /**
     * Wypisuje na panelu taOutput wygenerowane dane
     */
    static public void WypiszNaPanelu()             {
        GUI.taOutput.setEditable(true);
        GUI.taOutput.setText("");
        GUI.taOutput.append("Czas ekspozycji:           " + czasEkspozycji + '\n');
        GUI.taOutput.append("Zrównoważony Poziom A:     " + zrownowazonyPoziomA + '\n');
        GUI.taOutput.append("Maksymalny Poziom A:       " + maksymalnyPoziomA + '\n');
        GUI.taOutput.append("Szczytowy Poziom C:        " + szczytowyPoziomC + '\n');
        GUI.taOutput.append("" + '\n');
        GUI.taOutput.append("Wartość oczekiwana:        " + wartoscOczekiwana + '\n');
        GUI.taOutput.append("Odchylenie standardowe:    " + odchylenieStandardowe + '\n');
        GUI.taOutput.append("Wariancja:                 " + wariancja + '\n');
        GUI.taOutput.append("Kwartyl1:                  " + kwartyl1 + '\n');
        GUI.taOutput.append("Mediana:                   " + mediana + '\n');
        GUI.taOutput.append("Kwartyl3:                  " + kwartyl3 + '\n');
        GUI.taOutput.append("Minimum:                   " + minimum + '\n');
        GUI.taOutput.append("Maximum:                   " + maximum + '\n');
        GUI.taOutput.append("Poziom Ekspozycji: (8h)    " + BDpoziomEkspozycji8h.toString() + '\n');
        GUI.taOutput.append("Poziom Ekspozycji: (24h)   " + BDpoziomEkspozycji24h.toString() + '\n');
        GUI.taOutput.append(""+'\n');
        GUI.taOutput.append(""+'\n');
        GUI.taOutput.append("Ocena szkodliwosci w dniu roboczym (8h):" + '\n');
        GUI.taOutput.append("Poziom ekspozycji (8h):    " + ocenaSzkodliwosci[0] + '\n');
        GUI.taOutput.append("Maksymalny poziom A:       " + ocenaSzkodliwosci[1] + '\n');
        GUI.taOutput.append("Szczytowy poziom C:        " + ocenaSzkodliwosci[2] + '\n');
        GUI.taOutput.append(""+'\n');
        GUI.taOutput.append("Ocena szkodliwosci dla kobiet w ciazy w dniu roboczym (8h):" + '\n');
        GUI.taOutput.append("Poziom ekspozycji (8h):    " + ocenaSzkodliwosci[3] + '\n');
        GUI.taOutput.append("Maksymalny poziom A:       " + ocenaSzkodliwosci[4] + '\n');
        GUI.taOutput.append("Szczytowy poziom C:        " + ocenaSzkodliwosci[5] + '\n');
        GUI.taOutput.append(""+'\n');
        GUI.taOutput.append("Ocena szkodliwosci dla osob mlodych w dniu roboczym (8h):" + '\n');
        GUI.taOutput.append("Poziom ekspozycji (8h):    " + ocenaSzkodliwosci[6] + '\n');
        GUI.taOutput.append("Maksymalny poziom A:       " + ocenaSzkodliwosci[7] + '\n');
        GUI.taOutput.append("Szczytowy poziom C:        " + ocenaSzkodliwosci[8] + '\n');
        
        GUI.taOutput.setEditable(false);
         
    }
    /**
     * Wypisuje na konsoli wygenerowane dane
     */
    static public void wypiszRaportNaKonsoli()      {
        System.out.println("");
        System.out.println("Czas ekspozycji:          " + czasEkspozycji);
        System.out.println("Zrównoważony Poziom A:    " + zrownowazonyPoziomA);
        System.out.println("Maksymalny Poziom A:      " + maksymalnyPoziomA);
        System.out.println("Szczytowy Poziom C:       " + szczytowyPoziomC);
        System.out.println("");
        System.out.println("Wartość oczekiwana:       " + wartoscOczekiwana);
        System.out.println("Odchylenie standardowe:   " + odchylenieStandardowe);
        System.out.println("Wariancja:                " + wariancja);
        System.out.println("Kwartyl1:                 " + kwartyl1);
        System.out.println("Mediana :                 " + mediana);
        System.out.println("Kwartyl3:                 " + kwartyl3);
        System.out.println("Minimum:                  " + minimum);
        System.out.println("Maximum:                  " + maximum);
        System.out.println("Poziom Ekspozycji: (8h)   " + BDpoziomEkspozycji8h.toString());
        System.out.println("Poziom Ekspozycji: (24h)  " + BDpoziomEkspozycji24h.toString());
        

    }
    /**
 * Sprawdza poprawnosc danych (daty)
 */
    private static void testFormatDaty()            {
        for(int a=0; a<dlugoscListyWKlasie;a++)
        {
        if(TablicaDataY[a] < 2000 || TablicaDataY[a] > 2020)
        {   
            JOptionPane.showMessageDialog(null, "Wykryto błąd w dacie" + '\n' + "Linia: " + a);
            valid = false;
            break;
        }
        if(TablicaDataM[a] < 1 || TablicaDataM[a] >12)
        {   
            JOptionPane.showMessageDialog(null, "Wykryto błąd w dacie" + '\n' + "Linia: " + a);
            valid = false;
            break;}
        if(TablicaDataD[a] < 1 || TablicaDataD[a] > 31)
        {            
            JOptionPane.showMessageDialog(null, "Wykryto błąd w dacie" + '\n' + "Linia: " + a);
            valid = false;
            break;}
        }
    }
    /**
 * Sprawdza poprawnosc danych (godziny)
 */
    private static void testFormtGodziny()          {
        for(int a=0; a<dlugoscListyWKlasie;a++)
        {
        if(TablicaGodzinaH[a] < 0 || TablicaGodzinaH[a] > 23)
        {   
            JOptionPane.showMessageDialog(null, "Wykryto błąd w godzinie" + '\n' + "Linia: " + a);
            valid = false;
            break;
        }
        if(TablicaGodzinaM[a] < 0 || TablicaGodzinaM[a] > 59)
        {   
            JOptionPane.showMessageDialog(null, "Wykryto błąd w godzinie" + '\n' + "Linia: " + a);
            valid = false;
            break;}
        if(TablicaGodzinaS[a] < 0 || TablicaGodzinaS[a] > 59)
        {            
            JOptionPane.showMessageDialog(null, "Wykryto błąd w godzinie" + '\n' + "Linia: " + a);
            valid = false;
            break;}
        }
    }
    /**
 * Sprawdza poprawnosc danych (poziom SPL)
 */
    private static void testPoziomSPL()             {
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            if(TablicaSPL[a] <= 10.0 || TablicaSPL[a] >= 130.0)
            {
                JOptionPane.showMessageDialog(null, "Wykryto błąd w poziomie SPL" + '\n' + "Linia: " + a);
                valid = false;
                break;
            }
        }
    }
    /**
 * Sprawdza poprawnosc danych (charakterystyka)
 */
    private static void testCharakterystyka()       {
        String l = TablicaCharakter[0];
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            if(l.equals(TablicaCharakter[a]))
            {}
            else
            {
                JOptionPane.showMessageDialog(null, "Wykryto błąd w charakterystyce" + '\n' + "Linia: " + a);
                valid = false;
                break;
            }
        }
    }
    /**
 * Sprawdza poprawnosc danych (poprawnosc pomiaru)
 */
    private static void testPoprawnosciPomiaru()    {
        for(int a=0;a<dlugoscListyWKlasie;a++)
        {
            if("Good".equals(TablicaPoprawnosci[a]) || "good".equals(TablicaPoprawnosci[a]) || "GOOD".equals(TablicaPoprawnosci[a]))
            {}
            else
            {
                JOptionPane.showMessageDialog(null, "Wykryto błąd w poprawnosci pomiaru" + '\n' + "Linia: " + a);
                valid = false;
                break;
            }
        }   
    }
    /**
     * Sprawdza czy dane są ustawione chronologicznie
     */
    private static void testDaneChronologiczne()    {

        for(int a = 0; a < dlugoscListyWKlasie -1; a++)
        {
            if(TablicaGodzinaS[a]>=TablicaGodzinaS[a+1])
            {
                if(TablicaGodzinaM[a] >= TablicaGodzinaM[a] + 1)
                {
                    if(TablicaGodzinaH[a] >= TablicaGodzinaH[a] + 1)
                    {   
                        if(TablicaDataD[a] >= TablicaDataD[a] + 1)
                        {
                            if(TablicaDataM[a] >= TablicaDataM[a] + 1)
                            {
                                if(TablicaDataY[a] >= TablicaDataY[a] + 1)
                                {
                                    JOptionPane.showMessageDialog(null, "Wykryto błąd w chronologii czasu" + '\n' + "Linia: " + a);
                                    valid = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Eksportuje wygenerowane dane do pliku
    */
    static void MainExportCode() throws FileNotFoundException {
        
        PrintWriter pw = new PrintWriter("" + new Date() + ".csv");
        
        pw.append("Czas ekspozycji" + "," + czasEkspozycji + '\n');
        pw.append("Zrównoważony Poziom A" + "," + zrownowazonyPoziomA + '\n');
        pw.append("Maksymalny Poziom A" + "," + maksymalnyPoziomA + '\n');
        pw.append("Szczytowy Poziom C" + "," + szczytowyPoziomC + '\n');
        pw.append("Wartość oczekiwana" + "," +wartoscOczekiwana + '\n');
        pw.append("Odchylenie standardowe" + "," +odchylenieStandardowe + '\n');
        pw.append("Wariancja" + "," +wariancja + '\n');
        pw.append("Kwartyl1" + "," +kwartyl1 + '\n');
        pw.append("Mediana" + "," +mediana + '\n');
        pw.append("Kwartyl3" + "," +kwartyl3 + '\n');
        pw.append("Minimum" + "," +minimum + '\n');
        pw.append("Maximum" + "," +maximum + '\n');
        pw.append("Poziom Ekspozycji:(8h)" + "," +BDpoziomEkspozycji8h.toString() + '\n');
        pw.append("Poziom Ekspozycji:(24h)" + "," +BDpoziomEkspozycji24h.toString() + '\n');
        pw.append("Ocena szkodliwosci w dniu roboczym (8h)" + '\n');
        pw.append("Poziom ekspozycji (8h)" + "," +ocenaSzkodliwosci[0] + '\n');
        pw.append("Maksymalny poziom A" + "," +ocenaSzkodliwosci[1] + '\n');
        pw.append("Szczytowy poziom C" + "," +ocenaSzkodliwosci[2] + '\n');
        pw.append("Ocena szkodliwosci dla kobiet w ciazy w dniu roboczym (8h)" + '\n');
        pw.append("Poziom ekspozycji (8h)" + "," +ocenaSzkodliwosci[3] + '\n');
        pw.append("Maksymalny poziom A" + "," +ocenaSzkodliwosci[4] + '\n');
        pw.append("Szczytowy poziom C" + "," +ocenaSzkodliwosci[5] + '\n');
        pw.append("Ocena szkodliwosci dla osob mlodych w dniu roboczym (8h)" + '\n');
        pw.append("Poziom ekspozycji (8h)" + "," +ocenaSzkodliwosci[6] + '\n');
        pw.append("Maksymalny poziom A" + "," +ocenaSzkodliwosci[7] + '\n');
        pw.append("Szczytowy poziom C" + "," +ocenaSzkodliwosci[8] + '\n');
        pw.close();
        
        JOptionPane.showMessageDialog(null, "Utworzono plik \"" + new Date() + ".csv.\"" + '\n' + "Znajduje się w katalogu z plikiem .jar");
    }
}