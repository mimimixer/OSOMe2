package at.ac.fhcampuswien.fhmdb.customExceptions;

public class DatabaseException extends Exception{
    public String message;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException (){
        System.out.println("empty DatabaseException initiated");
    }
  /*  public DatabaseException (Exception e){
        System.out.println("not empty DatabaseException initiated");
        System.out.println("please print me out on UI Screen");
    }
    //nehme an es ist SQL Exception

    //movieCelL will mit mouseClickedEvent auf die MovieRepo zugreifen
    //sonstige Exceptions der Database, Movie Repository und Watchlist sollen hier zugreifen

    //die Exeptions werden vermutlich auch in der HomeController Klasse auftreten, weil
    //die Watchlist an die Observable Movies übergeben werden soll

    //ACHTUNGACHTUNG: AUSGABE FEHLERMELDUNG AN UI

   */
}
