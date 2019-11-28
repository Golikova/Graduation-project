package chrome_extencion;

public class WebPage {

    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "webPage='" + code + '\'' +
                '}';
    }
}

