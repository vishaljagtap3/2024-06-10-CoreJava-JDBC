public class Emp {
    private int id;
    private String name;
    private String dob;
    private int mgr;
    private int deptNo;
    private int sal;
    private String location;

    public Emp(int id, String name, String dob, int mgr, int deptNo, int sal, String location) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.mgr = mgr;
        this.deptNo = deptNo;
        this.sal = sal;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", mgr=" + mgr +
                ", deptNo=" + deptNo +
                ", sal=" + sal +
                ", location='" + location + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public int getSal() {
        return sal;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
