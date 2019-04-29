package ie.dcu.computing.student.lenehan4.mystoremanager;

public class EmployeeModelClass {
    String employee_id;
    String name;
    String dept;
    String image;
    String votes;

    @Override
    public String toString() {
        return "EmployeeModelClass{" +
                "employee_id='" + employee_id + '\'' +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", image='" + image + '\'' +
                ", votes='" + votes + '\'' +
                '}';
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
