import java.io.*;
class MemberManager
{
private static final String DATA_FILE="members.data";
public static void main(String gg[])
{
if(gg.length==0)
{
System.out.println("Please Enter An Operation");
System.out.println("[add,update,getAll,getByContactNumber,getByCourse,remove]");
System.out.println("Usage : java MemberManager operation args....");
return;
}
String cmd=gg[0];
if(!isValidOperation(cmd))
{
System.out.println("Please Enter A Valid Operation");
System.out.println("[add,update,getAll,getByContactNumber,getByCourse,remove]");
return;
}
if(cmd.equalsIgnoreCase("add")) add(gg);
else if(cmd.equalsIgnoreCase("update")) update(gg);
else if(cmd.equalsIgnoreCase("getAll")) getAll(gg);
else if(cmd.equalsIgnoreCase("getByContactNumber")) getByContactNumber(gg);
else if(cmd.equalsIgnoreCase("getByCourse")) getByCourse(gg);
else if(cmd.equalsIgnoreCase("remove")) remove(gg);
}
//functions
private static void add(String [] gg)
{
if(gg.length!=5)
{
System.out.println("Too few Arguments");
return;
}
String contactNumber=gg[1];
String name=gg[2];
String course=gg[3];
if(!isValidCourse(course))
{
System.out.println("Invalid Course : "+course);
return;
}
int fees;
try
{
fees=Integer.parseInt(gg[4]);
}catch(NumberFormatException nfe)
{
System.out.println(nfe.getMessage());
return;
}

try
{
String number;
File f=new File(DATA_FILE);
RandomAccessFile raf=new RandomAccessFile(f,"rw");
while(raf.getFilePointer()<raf.length())
{
number=raf.readLine();
if(number.equalsIgnoreCase(contactNumber))
{
raf.close();
System.out.println("Contact Number : "+contactNumber);
System.out.println("Already Exists");
return;
}
raf.readLine();
raf.readLine();
raf.readLine();
}
raf.writeBytes(contactNumber);
raf.writeBytes("\n");
raf.writeBytes(name);
raf.writeBytes("\n");
raf.writeBytes(course);
raf.writeBytes("\n");
raf.writeBytes(String.valueOf(fees));
raf.writeBytes("\n");
raf.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
System.out.println("Successfully Added Data");
}
private static void update(String [] gg)
{
if(gg.length!=5)
{
System.out.println("Too few Arguments");
return;
}
String number=gg[1];
String editName=gg[2];
String editCourse=gg[3];
if(!isValidCourse(editCourse))
{
System.out.println("Invalid Course : "+editCourse);
System.out.println("Valid Courses Are [c,c++,java,python,j2ee]");
return;
}
int editFees;
try
{
editFees=Integer.parseInt(gg[4]);
}catch(NumberFormatException nfe)
{
System.out.println(nfe.getMessage());
return;
}
try
{
File f=new File(DATA_FILE);
if(f.exists()==false)
{
System.out.println("Invalid Contact Number");
return;
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("Invalid Contact Number");
return;
}
String fNumber;
String fName="";
String fCourse;
int fFees;
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFees=Integer.parseInt(raf.readLine());
if(fNumber.equalsIgnoreCase(number))
{
found=true;
break;
}
}
if(found==false)
{
raf.close();
System.out.println("Invalid Contact Number : "+number);
return;
}
System.out.println("Updating data of : "+number);
System.out.println("Name Of The Candidate : "+fName);
File k=new File("tmp.data");
RandomAccessFile tmp=new RandomAccessFile(k,"rw");
tmp.setLength(0);
raf.seek(0);
while(raf.getFilePointer()<raf.length())
{
fNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFees=Integer.parseInt(raf.readLine());
if(fNumber.equalsIgnoreCase(number))
{
tmp.writeBytes(fNumber+"\n");
tmp.writeBytes(fName+"\n");
tmp.writeBytes(fCourse+"\n");
tmp.writeBytes(String.valueOf(fFees)+"\n");
}
else
{
tmp.writeBytes(number+"\n");
tmp.writeBytes(editName+"\n");
tmp.writeBytes(editCourse+"\n");
tmp.writeBytes(String.valueOf(editFees)+"\n");
}
}
raf.seek(0);
tmp.seek(0);
while(tmp.getFilePointer()<tmp.length())
{
raf.writeBytes(tmp.readLine()+"\n");
}
raf.setLength(tmp.length());
tmp.setLength(0);
tmp.close();
raf.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
System.out.println("Successfully Updated Data");
}
private static void getAll(String [] gg)
{
try
{
File f=new File(DATA_FILE);
if(f.exists()==false)
{
System.out.println("No Members");
return;
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("No Members");
return;
}
String number,name,course;
int fees,totalFee,memberCount;
int i=1;
memberCount=0;
totalFee=0;
System.out.println("S.no | Name | Course | Fees");
while(raf.getFilePointer()<raf.length())
{
number=raf.readLine();
name=raf.readLine();
course=raf.readLine();
fees=Integer.parseInt(raf.readLine());
System.out.println(i+" "+name+" "+course+" "+fees);
i++;
memberCount++;
totalFee+=fees;
}
raf.close();
System.out.println("Total Members = "+memberCount);
System.out.println("Total Fees = "+totalFee);
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
}
private static void getByContactNumber(String [] gg)
{
if(gg.length!=2)
{
System.out.println("Invalid Number Of Data Elements passed");
System.out.println("Usage : java MemberManager ContactNumber");
return;
}
String mobileNumber=gg[1];
try
{
File f=new File(DATA_FILE);
if(f.exists()==false)
{
System.out.println("No Members");
return;
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("No Members");
return;
}
String fNumber="";
String fName="";
String fCourse="";
int fFees=0;
boolean found=false;
System.out.println("S.no | Name | Course | Fees");
while(raf.getFilePointer()<raf.length())
{
fNumber=raf.readLine();
if(fNumber.equalsIgnoreCase(mobileNumber))
{
fName=raf.readLine();
fCourse=raf.readLine();
fFees=Integer.parseInt(raf.readLine());
found=true;
break;
}
raf.readLine();
raf.readLine();
raf.readLine();
}
raf.close();
if(found==false)
{
System.out.println("Invalid Mobile Number");
return;
}
System.out.println("Name = "+fName);
System.out.println("Course = "+fCourse);
System.out.println("Fee = "+fFees);
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
}
private static void getByCourse(String [] gg)
{
if(gg.length!=2)
{
System.out.println("Invalid Number Of Data Elements passed");
System.out.println("Usage : java MemberManager Course");
return;
}
String course=gg[1];
if(!isValidCourse(course))
{
System.out.println("Not A Valid Course : "+course);
return;
}
try
{
File f=new File(DATA_FILE);
if(f.exists()==false)
{
System.out.println("No Members");
return;
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("No Members");
return;
}
String fNumber="";
String fName="";
String fCourse="";
int fFees=0;
int totalFees=0;
int count=0;
int i=1;
System.out.println("S.no | Name | Course | Fees | ContactNumber");
while(raf.getFilePointer()<raf.length())
{
fNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFees=Integer.parseInt(raf.readLine());
if(fCourse.equalsIgnoreCase(course))
{
System.out.println(i+" "+fName+" "+fCourse+" "+fFees+" "+fNumber);
count++;
totalFees+=fFees;
i++;
}
}
raf.close();
System.out.println("Total Count = "+count);
System.out.println("Total Fees = "+totalFees);
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
}
private static void remove(String [] gg)
{
if(gg.length!=2)
{
System.out.println("Too few Arguments");
return;
}
String number=gg[1];
try
{
File f=new File(DATA_FILE);
if(f.exists()==false)
{
System.out.println("Invalid Contact Number : "+number);
return;
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()==0)
{
raf.close();
System.out.println("Invalid Contact Number : "+number);
return;
}
String fNumber;
String fName="";
String fCourse;
int fFees;
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFees=Integer.parseInt(raf.readLine());
if(fNumber.equalsIgnoreCase(number))
{
found=true;
break;
}
}
if(found==false)
{
System.out.println("Invalid Contact Number : "+number);
raf.close();
return;
}
System.out.println("Deleting Data Of : "+number);
System.out.println("Name Of Candidate is : "+fName);
File k=new File("tmp.data");
RandomAccessFile tmp=new RandomAccessFile(k,"rw");
tmp.setLength(0);
raf.seek(0);
while(raf.getFilePointer()<raf.length())
{
fNumber=raf.readLine();
fName=raf.readLine();
fCourse=raf.readLine();
fFees=Integer.parseInt(raf.readLine());
if(fNumber.equalsIgnoreCase(number)==false)
{
//write in tmp file
tmp.writeBytes(fNumber);
tmp.writeBytes("\n");
tmp.writeBytes(fName);
tmp.writeBytes("\n");
tmp.writeBytes(fCourse);
tmp.writeBytes("\n");
tmp.writeBytes(String.valueOf(fFees));
tmp.writeBytes("\n");
}
}
raf.seek(0);
tmp.seek(0);
while(tmp.getFilePointer()<tmp.length())
{
raf.writeBytes(tmp.readLine()+"\n");
}
raf.setLength(tmp.length());
tmp.setLength(0);
tmp.close();
raf.close();
k.renameTo(new File(DATA_FILE));
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
return;
}
System.out.println("Successfully Removed Data");
}
//helper Functions
private static boolean isValidOperation(String cmd)
{
cmd=cmd.trim();
String arr[]={"add","update","getAll","getByContactNumber","getByCourse","remove"};
for(int i=0;i<arr.length;i++)
{
if(arr[i].equalsIgnoreCase(cmd)) return true;
}
return false;
}
private static boolean isValidCourse(String course)
{
course=course.trim();
String arr[]={"c","c++","java","python","j2EE"};
for(int i=0;i<arr.length;i++)
{
if(arr[i].equalsIgnoreCase(course)) return true;
}
return false;
}
}