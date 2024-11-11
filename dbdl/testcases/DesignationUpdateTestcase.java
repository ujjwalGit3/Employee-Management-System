import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class  DesignationUpdateTestcase
{
public static void main(String gg[])
{
int fCode =Integer.parseInt(gg[0]);
String fTitle=gg[1];

try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();

designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);

DesignationDAOInterface designationDAO;
designationDAO =new DesignationDAO();

designationDAO.update(designationDTO);
System.out.println("designation updated");
}catch(DAOException daoException)
{

System.out.println(daoException.getMessage());

}

}
}