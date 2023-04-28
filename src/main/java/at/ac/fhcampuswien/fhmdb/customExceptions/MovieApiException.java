package at.ac.fhcampuswien.fhmdb.customExceptions;

public class MovieApiException extends Exception{
    public MovieApiException (){
        System.out.println("empty MovieApiException initiated");
    }
    public MovieApiException (Exception e){
        System.out.println("not empty MovieApiException initiated");
        System.out.println("please print me out on UI Screen");
    }

    //Nehme an es ist eine IO exception, weil es kann sein: SockeException, InterruptedIOException,
    // InterruptedByTimeoutException, FileNotFoundEX, UnknownHostEX etc

    //hier alle Exceptions der API Klasse - URL HTTP request Exceptions - rein
    //ACHTUNG: ein Großteil der durch die API Klasse erzeugten Exceptions sind im Controller!
    //wo die Liste an allMovies und an oberservableMovies übergeben wird!

    //eventuell dort thows-MethodSignature einfügen? oder doch umgekehrt(einfach check beim abrufen)?

    //ACHTUNGACHTUNG: AUSGABE FEHLERMELDUNG AN UI

}
