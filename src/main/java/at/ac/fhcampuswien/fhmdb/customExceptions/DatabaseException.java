package at.ac.fhcampuswien.fhmdb.customExceptions;

public class DatabaseException {

    public  static class ConnectionEcxeption extends Exception{
        public ConnectionEcxeption(String message){
            super(message);
        }
    }

    public static class TableCreationException extends Exception{
        public TableCreationException(String message){
            super(message);

        }

    }

    public static class DaoCreationException extends Exception{
        public DaoCreationException(String message){
            super(message);

        }
    }
    public DatabaseException(Exception e) {
        System.out.println("not empty DatabaseException initiated");
        System.out.println("please print me out on UI Screen");

    }
    //nehme an es ist SQL Exception

    //movieCelL will mit mouseClickedEvent auf die MovieRepo zugreifen
    //sonstige Exceptions der Database, Movie Repository und Watchlist sollen hier zugreifen

    //die Exeptions werden vermutlich auch in der HomeController Klasse auftreten, weil
    //die Watchlist an die Observable Movies übergeben werden soll

    //ACHTUNGACHTUNG: AUSGABE FEHLERMELDUNG AN UI
}
