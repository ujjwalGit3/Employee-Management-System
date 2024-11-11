import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;

import java.util.*;
class DesignationManagerGetDesignationCountTestCase
{
public static void main(String gg[])
{
try
{


DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
System.out.println("Number of Designation :-"+designationManager.designationCount());
}catch(BLException blException)
{
List<String> properties=blException.getProperties();
properties.forEach((property)->{
System.out.println(blException.getException(property));
});

}
}
}