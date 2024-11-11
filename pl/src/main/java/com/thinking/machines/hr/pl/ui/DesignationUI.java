package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;	
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
public class DesignationUI extends  JFrame implements DocumentListener,ListSelectionListener
{
private JTable designationTable;
private JLabel searchLabel;
private JLabel titleLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JScrollPane scrollPane;
private DesignationModel designationModel;
private Container container;
private JLabel searchErrorLabel;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;

private ImageIcon addIcon;
private ImageIcon logoIcon;
private ImageIcon saveIcon;
private ImageIcon cancelIcon;
private ImageIcon deleteIcon;
private ImageIcon pdfIcon;
private ImageIcon editIcon;
private ImageIcon crossIcon;



public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
}
private void initComponents()
{
/*
java lec-86
logoIcon=new ImageIcon("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icons"+File.separator+"logo_icon.png");
setIconImage(logoIcon.getImage());//get image give image type object address
addIcon=new ImageIcon("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icons"+File.separator+"add_icon.png");
saveIcon=new ImageIcon("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icons"+File.separator+"save_icon.png");
deleteIcon=new ImageIcon("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icons"+File.separator+"delete_icon.png");
cancelIcon=new ImageIcon("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icons"+File.separator+"cancel_icon.png");
pdfIcon=new ImageIcon("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icons"+File.separator+"pdf_icon.png");
editIcon=new ImageIcon("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icons"+File.separator+"edit_icon.png");
*/
//java lec-87 changes to icon
logoIcon=new ImageIcon(this.getClass().getResource("/icon/logo_icon.png"));
setIconImage(logoIcon.getImage());//get image give image type object address
addIcon=new ImageIcon(this.getClass().getResource("/icon/add_icon.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icon/save_icon.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icon/delete_icon.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icon/cancel_icon.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icon/pdf_icon.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icon/edit_icon.png"));
crossIcon=new ImageIcon(this.getClass().getResource("/icon/cross_icon.png"));

designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("search");
clearSearchTextFieldButton=new JButton(crossIcon);
searchTextField=new JTextField();
designationTable=new JTable(designationModel);
searchErrorLabel=new JLabel("");
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
designationPanel=new DesignationPanel();
container=getContentPane();
}
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,16);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);
designationTable.setRowHeight(35);
TableColumnModel columnModel = designationTable.getColumnModel();
columnModel.getColumn(0).setPreferredWidth(20);
columnModel.getColumn(1).setPreferredWidth(400);
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//for singel selection of row
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
container.setLayout(null);
int lm,tm;//left margine right margine
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchErrorLabel.setBounds(lm+10+100+400+10-95,tm+10+10+20,100,20);
searchLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(lm+10+400+100+10,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,300);
designationPanel.setBounds(lm+10,tm+10+40+10+30+10+300+10,565,200);
container.add(searchErrorLabel);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
int w,h;
w=600;
h=675;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
public void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);

}
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent ec)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent ed)
{
searchDesignation();
}
public void valueChanged(ListSelectionEvent ev)
{
//when we click on row then this method called
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}
catch(BLException blException)
{
designationPanel.clearDesignation();
}
}

private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}

private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}




//inner class starts
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
initComponents();
setAppearance();
addListeners();
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
private void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextFieldButton=new JButton("X");
buttonsPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}

private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont); 
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+110+5+10,tm+20,400,30);
titleTextField.setBounds(lm+10+110+5,tm+20,350,30);
clearTitleTextFieldButton.setBounds(lm+10+110+5+350+5,tm+20,30,30);

buttonsPanel.setBounds(50,tm+20+30+10+10+10,465,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);

buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleTextField);
add(titleLabel);
add(clearTitleTextFieldButton);
add(buttonsPanel);
}
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
try
{

designationModel.add(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return false;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
 if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}

private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{

designationModel.update(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return false;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
 if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}

private void removeDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION)return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" Deleted");
this.clearDesignation();
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
}


private void addListeners()
{
this.exportToPDFButton.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ef)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path :-"+file.getAbsolutePath());
return;
}     
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data Exported To PDF :-"+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
}

}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}
});

this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setAddMode();
}else
{
if(addDesignation())
{
setViewMode();
}
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent wc)
{
if(mode==MODE.VIEW)
{
setEditMode();
}else
{
if(updateDesignation())
{
setViewMode();
}
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});

this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();

}
});
}

void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
this.titleLabel.setVisible(true);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}

void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.clearTitleTextFieldButton.setVisible(true);
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
addButton.setIcon(saveIcon);
addButton.setEnabled(true);
cancelButton.setEnabled(true);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"select designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
removeDesignation();
DesignationUI.this.setViewMode();
this.setViewMode(); 
}
void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"select designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.clearTitleTextFieldButton.setVisible(true);
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
editButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setIcon(editIcon);

}
void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}


}//inner class ends

}