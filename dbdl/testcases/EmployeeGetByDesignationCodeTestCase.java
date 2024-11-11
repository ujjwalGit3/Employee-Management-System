import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeGetByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{

EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();

Set<EmployeeDTOInterface> employees;
employees=employeeDAO.getByDesignationCode(designationCode);
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeDTOInterface employeeDTO:employees)
{
System.out.println("Employee Id :- "+employeeDTO.getEmployeeId());
System.out.println("Name :-"+employeeDTO.getName());
System.out.println("Designation Code :-"+employeeDTO.getDesignationCode());
System.out.println("Date Of Birth :-"+simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender :-"+employeeDTO.getGender());
System.out.println("Is Indain:-"+employeeDTO.getIsIndian());
System.out.println("Basic Salary :-"+employeeDTO.getBasicSalary().toPlainString());
System.out.println("PAN Number :-"+employeeDTO.getPANNumber());
System.out.println("Aadhar Card Number :-"+employeeDTO.getAadharCardNumber());
System.out.println("************************************************************************************");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}