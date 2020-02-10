package chrome_extencion;

import org.json.JSONException;

import java.io.IOException;

public interface Searcher {

    public Source getSource(String qry) throws IOException, JSONException;

}
