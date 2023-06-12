package at.ac.fhcampuswien.fhmdb.observePattern;

public class ObservableUpdates {
    private String data;
    private ObservableEnum observableEnum;

    public ObservableUpdates(String data, ObservableEnum observableEnum){
        this.data = data;
        this.observableEnum = observableEnum;

    }

    public ObservableEnum getObservableEnum(){
        return observableEnum;
    }
    public String getData(){
        return data;
    }

}
