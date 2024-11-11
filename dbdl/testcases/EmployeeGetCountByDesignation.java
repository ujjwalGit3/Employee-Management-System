import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeGetCountByDesignation
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
System.out.println("number of employees whose designation code is :-"+designationCode+"is:"+new EmployeeDAO().getCountByDesignation(designationCode));

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}