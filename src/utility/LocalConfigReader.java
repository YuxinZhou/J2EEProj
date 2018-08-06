package utility;

import entity.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by guwei on 2016/3/17.
 */
public class LocalConfigReader
{
    private static String examPlace = "";

    public static String GetExamPlace()
    {
        if (!examPlace.equals(""))
            return examPlace;

        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        try
        {
            String path="C:/AutoData/ExamPlace.txt";
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();

            FileInputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            tempstr=br.readLine();
            sb.append(tempstr);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }

        return sb.toString();
    }

    // warning
    private static Hashtable<Integer, Integer> warnings = new Hashtable<>();
    public static int AddWarning(int examGroup)
    {
        if (warnings.containsKey(examGroup))
        {
            warnings.replace(examGroup, warnings.get(examGroup) + 1);
        }
        else
        {
            warnings.put(examGroup, 1);
        }
        return warnings.get(examGroup);
    }

    public static String MainServerIP = "192.168.1.101";
    public static int MainServerPORT = 8899;

    public static int ServerID = 1;

    public static boolean ShouldConnectMainServer = true;


    //trick local student
    public static ArrayList<Student> localEnrollStudents = new ArrayList<Student>();
    public static void AddLocalStudent(Student student)
    {
        localEnrollStudents.add(student);
    }

    public static void AddLocalStudent(ArrayList<Student> students)
    {
        for (int i=0;i<students.size();++i)
        {
            localEnrollStudents.add(students.get(i));
        }
    }

    //re exam time
    public static String ReExamTime = "";
}
