package search;

import database.DbCategoryOrderEntity;
import database.SqlQueryByParentIdOrganizer;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Reader {

    ArrayList<DbCategoryOrderEntity> entityList;
    public int exceptionCounter;
    public int recordCounter;
    private <T> T getCellValue(Cell cell) {

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return (T) cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN:
                return (T) (Boolean) cell.getBooleanCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return (T) (Double) cell.getNumericCellValue();

        }
        return null;
    }

    public ArrayList<DbCategoryOrderEntity> readFromExcel(FileInputStream fin) throws IOException, InvalidFormatException {
        entityList = new ArrayList<>();
        BufferedInputStream input = new BufferedInputStream(fin);

        HSSFWorkbook workbook = new HSSFWorkbook(input);

        HSSFSheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {

            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            DbCategoryOrderEntity entity = new DbCategoryOrderEntity();

            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();

                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex) {
                    case 0: {
                        Object ob = getCellValue(nextCell);

                        if (ob instanceof Double) {
                            entity.setId((Double) ob);
                        } else if (ob instanceof String) {
                            if (((String) ob).equals("null")) {
                                entity.setId(null);
                            }
                        }
                        break;
                    }//case
                    case 1: {
                        Object ob = getCellValue(nextCell);

                        entity.setEnglishName((String) ob);

                        break;
                    }//case
                    case 2: {
                        Object ob = getCellValue(nextCell);

                        entity.setPersianName((String) ob);

                        break;
                    }//case

                    case 4: {
                        Object ob = getCellValue(nextCell);
                        if (ob instanceof Double) {
                            entity.setParentID((Double) ob);
                        } else if (ob instanceof String) {
                            if (((String) ob).equals("null")) {

                                entity.setParentID(null);
                            }
                        }
                        break;
                    }//case

                }//switch

            }

            entityList.add(entity);

        }//first while

        
        
        workbook.close();
        input.close();
        SqlQueryByParentIdOrganizer sqlQueryByParentIdOrganizer = new SqlQueryByParentIdOrganizer(entityList);
        this.exceptionCounter = sqlQueryByParentIdOrganizer.exceptionCounter;
        this.recordCounter = sqlQueryByParentIdOrganizer.recordCounter;
                
        return entityList;
    }
}
