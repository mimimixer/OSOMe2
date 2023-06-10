package at.ac.fhcampuswien.fhmdb.persistience;

public class URLBuilder {
    private String host;
    private String path;
    private String query;
    private String genre;
    private String releaseYear;
    private String ratingFrom;
    private String movieID;

    public URLBuilder setHost(String host){
        this.host=host;
        return this;
    }

    public URLBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public URLBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public URLBuilder setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public URLBuilder setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public URLBuilder setRatingFrom(String ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }
    public URLBuilder setMovieID(String movieID) {
        this.movieID = movieID;
        return this;
    }


    public URLBuilder(){}

    //+++++++++++++++++++++++++++++++++++++++++++++


    public String build() {
        StringBuilder url = new StringBuilder();
        url.append(host);

        if (path != null ){
            url.append(path);
        }
        if (query != null||genre!=null||releaseYear!=null||ratingFrom!=null) {
            url.append("?");
        }
        if (query != null && !query.isEmpty()) {
            url.append("query=").append(query);
        }
        if(genre != null && !genre.equals("No filter")){
            if (query != null && !query.isEmpty()){url.append("&");}
            url.append("genre=").append(genre);
        }
        if(releaseYear != null && !releaseYear.equals("No filter")){
            if ((query != null && !query.isEmpty())||(genre != null && !genre.equals("No filter"))){url.append("&");}
            url.append("releaseYear=").append(releaseYear);
        }
        if(ratingFrom != null && !ratingFrom.equals("No filter")){
            if ((query != null && !query.isEmpty())||(genre != null && !genre.equals("No filter"))||(releaseYear != null && !releaseYear.equals("No filter"))){url.append("&");}
            url.append("ratingFrom=").append(ratingFrom);
        }
        if (movieID!=null){
            url.append("/").append(movieID);
        }
        return url.toString();
    }
}
