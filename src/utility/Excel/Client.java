package utility.Excel;


import java.io.IOException;
import java.util.List;
import entity.Student;

/**
 * @author Hongten
 * @created 2014-5-21
 */
public class Client {

    public static void main(String[] args) throws IOException {
        //String excel2003_2007 = Common.STUDENT_INFO_XLS_PATH;
        String excel2010 = Common.STUDENT_INFO_XLSX_PATH;
        // read the 2003-2007 excel
        //List<Student> list = new ReadExcel().getEntrollStudents(excel2003_2007);

        System.out.println("======================================");
        // read the 2010 excel
        List<Student> list1 = new ReadExcel().getEntrollStudents(excel2010);

    }
}