package at.ac.fhcampuswien.fhmdb.controllers;

import at.ac.fhcampuswien.fhmdb.customExceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.persistience.WatchlistRepository;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {
    private Object controllerInstance;
  //  private Object watchlistControllerInstance;
    private Object newController;

    public Object getInstance(Class<?> controllerClass) {
        try {
        if(controllerClass == HomeController.class || controllerClass== WatchlistController.class){
            if (controllerClass == null){
                controllerInstance= call(controllerClass);
            }
        }
        }catch (Exception e){
            System.out.println("failed to create controller in FXMLLoader");
            e.printStackTrace();
        }
        return controllerInstance;
    }
    @Override
    public Object call(Class<?> controllerClass) {
        try {
           newController = controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("failed to create instance in FXMLLoader");
            e.printStackTrace();
        }
        return newController;
    }

}
