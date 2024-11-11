package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.image.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
private String[] columnTitles;
public  DesignationModel()
{
populateDataStructures();
} 
private void populateDataStructures()
{
this.columnTitles=new String[2];
this.columnTitles[0]="S.No.";
this.columnTitles[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
//
}
Set<DesignationInterface> blDesignations=designationManager.getDesignation();
this.designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
this.designations.add(designation);
}
//for sort the data according to there names in alphabetical order
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
//comparator consists compare method
public int compare(DesignationInterface left,DesignationInterface right)
//Compare  ke pass do ayenge designationInterface type ke object ke addresses
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return designations.size();
}
public int getColumnCount()
{
return this.columnTitles.length;
}
public String getColumnName(int columnIndex)
{
return columnTitles[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}

public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;// special treatment
//as good as writing return Class.forName("java.lang.Integer")
return String.class;
}
public boolean IsCellEditable(int rowIndex,int columnIndex)
{
return false;
}


//application specific methods
public void add(DesignationInterface  designation)throws BLException
{
designationManager.addDesignation(designation);   
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public int indexOfDesignation(DesignationInterface designation)throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException;
blException=new BLException();
blException.setGenericException("Invalid designation :"+designation.getTitle());
throw blException;
}

public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid title :"+title);
throw blException;
}

public void update(DesignationInterface designation)throws BLException
{
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public void remove(int code)throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code)break;
index++;
}
if(index==this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid deisgnation code:"+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}

public DesignationInterface getDesignationAt(int index)throws BLException
{
if(index<0 || index>=this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index:"+index);
throw blException;	

}
return this.designations.get(index);
}

public void exportToPDF(File file)throws BLException
{

try
{
if(file.exists())file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document doc=new Document(pdfDocument);
//                java lec-87

//Image logo=new Image(ImageDataFactory.create("C:"+File.separator+"Javaproject"+File.separator+"hr"+File.separator+"icon"+File.separator+"logo_icon.png"));
Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icon/logo_icon.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);

Paragraph companyNamePara=new Paragraph();
companyNamePara.add("ABCD Corporation");

PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);

Paragraph reportTitlePara=new Paragraph("List Of Designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);

PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

Paragraph columnTitle1=new Paragraph("S.NO");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(14);

Paragraph columnTitle2=new Paragraph("Designations");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(14);

Paragraph pageNumberParagraph;
Paragraph dataParagraph;

float topTableColumnWidths[]={1,5};
float dataTableColumnWidths[]={1,5};
int sno,x,pageSize;
pageSize=5;

boolean newPage=true;
Table topTable;
Table pageNumberTable;
Table dataTable=null; // hoga yeh ham header mai create karenge joki if mai hoga or header ke bad footer mai add karenge toh assignment vala concept if mai hoga or footer mai value acces karenmge toh compiler chilayege toh iske liye null
Cell cell;

int numberOfPages=this.designations.size()/pageSize; // 2/13 esa dikhega pdf mai uske liye yeh
if((this.designations.size()%pageSize)!=0) numberOfPages++; //means odd number of records hai if 15 toh 3 page is if mai ni fasega agr 17hai toh if mai fasega joh 3 page hai voh 4 compute ho jayega
int pageNumber=0;
sno=0;
x=0;
DesignationInterface designation;
while(x<this.designations.size())
{
if(newPage==true)
{
//create new page header
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);//border ni chiye 
cell.add(logoPara);
topTable.addCell(cell);


cell=new Cell();
cell.setBorder(Border.NO_BORDER);//border ni chiye 
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
doc.add(topTable);
pageNumberParagraph=new Paragraph("Page :"+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(13);
pageNumberTable=new Table(1);// ek cell esa samjha diya 
pageNumberTable.setWidth(UnitValue.createPercentValue(100));  // pure page ko cover karega or alignment se right mai dikhega  
cell=new Cell();
cell.setBorder(Border.NO_BORDER); //border ni chiye 
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
doc.add(pageNumberTable);
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);

newPage=false;
}
designation=this.designations.get(x);
//add row to table
sno++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(sno)); // SNO INT TYPE SO WE HAVE TO CONVERT INTO STRING 
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

cell=new Cell();
dataParagraph=new Paragraph(designation.getTitle()); 
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph); //by default alignment is left
dataTable.addCell(cell);

x++;
if(sno%pageSize==0 || x==this.designations.size())
{
//create footer
doc.add(dataTable);
doc.add(new Paragraph("Software By:- Thinking Machines"));
if(x<this.designations.size())
{
//add new page to document
doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE)); //older version doc.newPage() func to add new page now it is chnaged
newPage=true;// age loop mai header ban jaye agr or pages hai toh
}
}
}
doc.close();
}catch(Exception exception)
{
BLException blException;
blException=new BLException();
blException.setGenericException(exception.getMessage());
throw blException;
}
}
}