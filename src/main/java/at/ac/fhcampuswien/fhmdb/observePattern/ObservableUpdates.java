package at.ac.fhcampuswien.fhmdb.observePattern;

public class ObservableUpdates {
    private String title;
    private ObservableEnum observableEnum;

    public ObservableUpdates(String title, ObservableEnum observableEnum){
        this.title = title;
        this.observableEnum = observableEnum;

    }

    public ObservableEnum getObservableEnum(){
        return observableEnum;
    }
    public String getTitle(){
        return title;
    }

}
