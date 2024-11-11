import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeePanNumberExistsTestCase
{
public static void main(String gg[])
{
String panNumber=gg[0];
try
{
System.out.println("Pan Number.:- "+panNumber+" Exists :-"+new EmployeeDAO().PANNumberExists(panNumber));

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}