package com.thinking.machines.hr.bl.interfaces.managers;
import  com.thinking.machines.hr.bl.interfaces.pojo.*;
import  com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee)throws BLException;
public void updateEmployee(EmployeeInterface employee)throws BLException;
public void removeEmployee(String employeeId)throws BLException;
public EmployeeInterface getEmployeeByEmployeeId(String employeeId)throws BLException;
public EmployeeInterface getEmployeeByPANNumber(String panNumber)throws BLException;
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber)throws BLException;
public int  getEmployeeCount();
public boolean employeeIdExists(String employeeId);
public boolean EmployeePANNumberExists(String panNumber);
public boolean EmployeeAadharCardNumberExists(String aadharCardNumber);
public Set<EmployeeInterface> getEmployees();
public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode)throws BLException;
public int getEmployeeCountByDesignationCode(int designationCode)throws BLException;
public boolean designationAlloted(int designationCode)throws BLException;
}
