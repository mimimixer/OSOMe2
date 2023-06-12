package at.ac.fhcampuswien.fhmdb.creationalPatterns;

import at.ac.fhcampuswien.fhmdb.controllers.HomeController;
import at.ac.fhcampuswien.fhmdb.controllers.WatchlistController;
import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.persistience.WatchlistRepository;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private Object controllerInstance;
  //  private Object watchlistControllerInstance;
    private Object newController;

    private static ControllerFactory controllerFactoryInstance;
/*private ControllerFactory(){}
    public static ControllerFactory getInstance() { // allows only 1 instance of MyFactory
        if(controllerFactoryInstance == null)
        {
            controllerFactoryInstance = new ControllerFactory();
        }
        return controllerFactoryInstance;
    }

*/
   /*
    public Object getControllerInstance(Class<?> controllerClass) {
        try {
        if(controllerClass == HomeController.class || controllerClass== WatchlistController.class){
            if (getConInstance(controllerClass) == null){
                controllerInstance= call(controllerClass);
            }
        }
        }catch (Exception e){
            System.out.println("failed to create controller in FXMLLoader");
            e.printStackTrace();
        }
        return controllerInstance;
    }

    */


    @Override
    public Object call(Class<?> controllerClass) {
        try {
            if (controllerClass == HomeController.class) {
                newController = HomeController.getInstance();
            } else if (controllerClass == WatchlistController.class) {
                newController = WatchlistController.getInstance();
            } else {
                newController = controllerClass.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            System.out.println("failed to create instance in FXMLLoader");
            e.printStackTrace();
        }
        return newController;
    }

}
