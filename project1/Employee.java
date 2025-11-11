package project1;

/**
 * enum class of employees, has associated dept too.
 * @author joshuaH, alexg
 */
public enum Employee {
    /** Employee Patel, in the Computer Science department. */
    PATEL(Department.CS),
    /** Employee Lim, in the Electrical Engineering department. */
    LIM(Department.EE),
    /** Employee Zimnes, in the Computer Science department. */
    ZIMNES(Department.CS),
    /** Employee Harper, in the Electrical Engineering department. */
    HARPER(Department.EE),
    /** Employee Kaur, in the Information Technology and Informatics department. */
    KAUR(Department.ITI),
    /** Employee Taylor, in the Mathematics department. */
    TAYLOR(Department.MATH),
    /** Employee Ramesh, in the Mathematics department. */
    RAMESH(Department.MATH),
    /** Employee Ceravolo, in the Business Analytics and Information Technology department. */
    CERAVOLO(Department.BAIT);
    /** The department this employee belongs to. */
    private final Department dept;

    /**
     * creates an employee with their dept
     * @param dept , the department the employee is in
     */
    Employee(Department dept){
        this.dept = dept;
    }

    /**
     * gets the department of employee
     * @return returns the department
     */
    public Department getDept(){
        return dept;
    }
}
