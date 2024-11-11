package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import com.thinking.machines.enums.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeeMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeeMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeeMap;
private Set<EmployeeInterface> employeeSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeeMap;
private static EmployeeManager employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures()throws BLException
{
this.employeeIdWiseEmployeeMap=new HashMap<>();
this.panNumberWiseEmployeeMap=new HashMap<>();
this.aadharCardNumberWiseEmployeeMap=new HashMap<>();
this.employeeSet=new TreeSet<>();
this.designationCodeWiseEmployeeMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
if(dlEmployee.getGender()=='M')
{
employee.setGender(GENDER.MALE);
}else
{
employee.setGender(GENDER.FEMALE);
}
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeeMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeeMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeeMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeeSet.add(employee);
ets=this.designationCodeWiseEmployeeMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
designationCodeWiseEmployeeMap.put(designation.getCode(),ets);
}
else
{
ets.add(employee);
}

}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager =new EmployeeManager();
return employeeManager;
}



public void addEmployee(EmployeeInterface employee)throws BLException
{
BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","emplpoyee Id.should be nil/empty");
}
}
if(name==null)
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designation.getCode())==false)
{
blException.addException("designation","Designation required");
}
}
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date Of Birth required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic Salary required");
}
}
if(panNumber==null)
{
blException.addException("panNumber","PANNumber required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PANNumber required");
}
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar Card Number required");
}
}

if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase()))
{
blException.addException("panNumber","PAN Number"+panNumber+" Exists.");
}
}

if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase()))
{
blException.addException("aadharCardNumber","Aadhar Card Number"+aadharCardNumber+"Exists.");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee=new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
employeeSet.add(dsEmployee);
employeeIdWiseEmployeeMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),dsEmployee);


Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeeMap.put(dsEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(dsEmployee);
}


}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
 
}
public void updateEmployee(EmployeeInterface employee)throws BLException
{

BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId==null)
{
blException.addException("employeeId","emplpoyee Id required");
}
else
{
employeeId=employeeId.trim(); 
if(employeeId.length()==0)
{
blException.addException("employeeId","emplpoyee Id required");
}
else
{
if(employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","employeee Id required"); 
throw blException;
}
}
}
if(name==null)
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designation.getCode())==false)
{
blException.addException("designation","Designation required");
}
}
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date Of Birth required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic Salary required");
}
}
if(panNumber==null)
{
blException.addException("panNumber","PANNumber required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PANNumber required");
}
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar Card Number required");
}
}

if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=panNumberWiseEmployeeMap.get(panNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("panNumber","PAN Number"+panNumber+"Exists");
}
}

if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface ee=aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("aadharCardNumber","Aadhar Card Number"+aadharCardNumber+"Exists.");
}
}

if(blException.hasException())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
int oldDesignationCode=dsEmployee.getDesignation().getCode();
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee=new EmployeeDTO();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.update(dlEmployee);


dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);

employeeSet.remove(dsEmployee);
employeeIdWiseEmployeeMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeeMap.remove(oldPANNumber);
aadharCardNumberWiseEmployeeMap.remove(oldAadharCardNumber);

employeeSet.add(dsEmployee);
employeeIdWiseEmployeeMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),dsEmployee);

Set<EmployeeInterface> ets;

ets=this.designationCodeWiseEmployeeMap.get(oldDesignationCode);
ets.remove(dsEmployee);
ets=this.designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeeMap.put(dsEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(dsEmployee);
}

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId)throws BLException
{

if(employeeId==null)
{
BLException blException=new BLException();
blException.addException("employeeId","emplpoyee Id required");
throw blException;
}
else
{
employeeId=employeeId.trim(); 
if(employeeId.length()==0)
{
BLException blException=new BLException();
blException.addException("employeeId","emplpoyee Id required");
throw blException;
}
else
{
if(employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase())==false)
{
BLException blException=new BLException();
blException.addException("employeeId","emplpoyee Id required");
throw blException;
}
}
}

try
{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());

EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();

employeeDAO.delete(dsEmployee.getEmployeeId());
employeeSet.remove(dsEmployee);
employeeIdWiseEmployeeMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeeMap.remove(dsEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeeMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public EmployeeInterface getEmployeeByEmployeeId(String employeeId)throws BLException
{
EmployeeInterface dsEmployee=employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("employeeId","Invalid EmployeeId.:"+employeeId);
throw blException;
}
EmployeeInterface employee =new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeeMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
return employee;

}

public EmployeeInterface getEmployeeByPANNumber(String panNumber)throws BLException
{
EmployeeInterface dsEmployee=panNumberWiseEmployeeMap.get(panNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("panNumber","Invalid PAN Number:"+panNumber);
throw blException;
}
EmployeeInterface employee =new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber)throws BLException
{
EmployeeInterface dsEmployee=aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("aadharCardNumber","Invalid Aadhar Card Number:"+aadharCardNumber);
throw blException;
}
EmployeeInterface employee =new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
public int  getEmployeeCount()
{
return employeeSet.size();
}
public boolean employeeIdExists(String employeeId)
{
return employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase());
}
public boolean EmployeePANNumberExists(String panNumber)
{
return panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase());
}
public boolean EmployeeAadharCardNumberExists(String aadharCardNumber)
{
return aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase());
}
public Set<EmployeeInterface> getEmployees() 
{
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface employee;
DesignationInterface dsDesignation;
DesignationInterface designation;
for(EmployeeInterface dsEmployee:employeeSet)
{
employee =new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode)throws BLException
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false)
{
BLException blException=new BLException();
blException.setGenericException("Invalid designation code"+designationCode);
}
Set<EmployeeInterface> employees=new TreeSet<>();
Set<EmployeeInterface> ets;
ets=designationCodeWiseEmployeeMap.get(designationCode);
if(ets==null)
{
return employees;
}

EmployeeInterface employee;
DesignationInterface dsDesignation;
DesignationInterface designation;
for(EmployeeInterface dsEmployee:ets)
{
employee =new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}

return employees;
}
public int getEmployeeCountByDesignationCode(int designationCode)throws BLException
{
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeeMap.get(designationCode);
if(ets==null) return 0;
return ets.size();
}
public boolean designationAlloted(int designationCode)throws BLException
{
return this.designationCodeWiseEmployeeMap.containsKey(designationCode);
}
}