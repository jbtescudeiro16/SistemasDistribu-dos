import java.util.List;

public interface Ficheiros {

    void using(String path);
    void notUsing(String path, boolean modified);
    List<String> startBackup();
    void endBackup();

}
