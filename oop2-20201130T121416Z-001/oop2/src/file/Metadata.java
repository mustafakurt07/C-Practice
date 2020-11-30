package file;

public class Metadata {
    String key;
    String value;

    public Metadata(String value){
        this.key = value.split(":")[0];
        try{
            this.value = value.split(":")[1];
        }catch (Exception e){
            this.value = "";
        }
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
