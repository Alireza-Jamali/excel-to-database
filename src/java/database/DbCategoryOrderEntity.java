package database;

public class DbCategoryOrderEntity {

    private Double id;
    private String englishName;
    private String persianName;
    private Double parentID;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getPersianName() {
        return persianName;
    }

    public void setPersianName(String persianName) {
        this.persianName = persianName;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getParentID() {
        return parentID;
    }

    public void setParentID(Double parentID) {
        this.parentID = parentID;
    }
}