package ie.dcu.computing.student.lenehan4.mystoremanager.FirebaseClasses;

public class Upload {

    private String month;
    private String imageUri;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String month, String imageUri) {
        if (month.trim().equals("")){
            month = "No month";
        }

        this.month = month;
        this.imageUri = imageUri;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}


