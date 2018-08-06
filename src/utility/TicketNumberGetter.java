package utility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by guwei on 2016/3/10.
 */
public class TicketNumberGetter
{
    public static void SetTicketNumberRange(String start, String end)
    {
        try
        {
            File path=new File("C:/AutoData");
            File dir=new File(path,"TicketRange.txt");
            if(!dir.exists())
                dir.createNewFile();

            FileOutputStream out=new FileOutputStream(dir,false); //如果追加方式用true
            StringBuffer sb=new StringBuffer();
            sb.append(start + "\n" + end);
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }

    public static String GetNextTicketNumber()
    {
        StringBuffer sb=new StringBuffer();
        StringBuffer sb2=new StringBuffer();
        String tempstr=null;
        try
        {
            String path="C:/AutoData/TicketRange.txt";
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();

            //另一种读取方式
            FileInputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            tempstr=br.readLine();
            sb.append(tempstr);
            sb2.append(tempstr);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
        String startNum = sb.toString();
        String endNum = sb2.toString();

        int startNumInt = Integer.parseInt(startNum);
        String ticketNum = (++startNumInt) + "";
        SetTicketNumberRange(ticketNum, endNum);

        return  ticketNum;
    }

    public static void main(String[] args) {
        SetTicketNumberRange("100000", "200000");
        System.out.println(GetNextTicketNumber());
        System.out.println(GetNextTicketNumber());
        System.out.println(GetNextTicketNumber());
        System.out.println(GetNextTicketNumber());
        System.out.println(GetNextTicketNumber());
    }
}
