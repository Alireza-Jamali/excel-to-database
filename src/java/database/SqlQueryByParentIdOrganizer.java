package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SqlQueryByParentIdOrganizer {

    public int exceptionCounter;
    public int recordCounter;
    
    String tableName = ""; // ENTER TABLE NAME
    double counter = 0;
    private HashMap<Double, Double> hashmap = new HashMap<>();
    Logger logger = Logger.getLogger("CategoryInsertException");

    public SqlQueryByParentIdOrganizer(ArrayList<DbCategoryOrderEntity> entityList) {

        for (DbCategoryOrderEntity en : entityList) {
            recurse(en);
        }
    }

    private void recurse(DbCategoryOrderEntity en) {
        if (en.getParentID() != null) {
            Double tempParentId = hashmap.get(en.getParentID());
            if (tempParentId == null) {
                try {
                    FileHandler fh = new FileHandler(System.getProperty("catalina.base") + "\\logs\\CategoryInsertException.log");
                    logger.addHandler(fh);
                    fh.setFormatter(new SimpleFormatter());
                    logger.severe("Inside your excel file: " + en.getId() + "  " + en.getEnglishName() + "  " + en.getPersianName() + " " + en.getParentID() + " has unregistered Parent ID insert it manually: \n");
                    exceptionCounter++;
                } catch (IOException | SecurityException ex) {
                    ex.printStackTrace();
                }
            }
            double returnedId = insert("INSERT INTO " + tableName + " (column1, column2, column3) VALUES ('" + en.getEnglishName() + "', N'" + en.getPersianName() + "', " + tempParentId + ");");
            hashmap.put(en.getId(), returnedId);
            recordCounter++;
        } else {
            double returnedId = insert("INSERT INTO " + tableName + " (column1, column2, column3) VALUES ('" + en.getEnglishName() + "', N'" + en.getPersianName() + "', NULL);");
            hashmap.put(en.getId(), returnedId);
            recordCounter++;
        }
    }

    private double insert(String s) {
        System.out.println(s);
        return counter++;
    }
}