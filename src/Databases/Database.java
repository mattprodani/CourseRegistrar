package Databases;

import java.io.IOException;

public interface Database {

     void save() throws IOException;
     void load() throws IOException;
}
