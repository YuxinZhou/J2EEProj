package utility;

import DAO.ExpertDao;
import DAO.StudentDao;
import entity.Expert;
import entity.Student;

import java.util.*;

/**
 * Created by guwei on 2016/3/15.
 */
public class GroupGenerator
{
    private static Hashtable<String, ArrayList<Integer>> categaryGroups = new Hashtable<String, ArrayList<Integer>>();
    private static int arrangedGroupNumber = 0;
    private static ArrayList<Integer> examNumbers = new ArrayList<Integer>();
    private static boolean hasArranged = false;

    public static final int MIN_EXAM_NUMBER = 100000;
    public static final int MAX_EXAM_NUMBER = 999999;

    public static void GenerateGroups()
    {
        if (hasArranged)
        {
            return;
        }

        ExpertDao expertDao = new ExpertDao();
        ArrayList<Expert> experts = expertDao.searchAllExpert();

        for (int i = 0; i < experts.size(); ++i)
        {
            Expert ep = experts.get(i);
            if (categaryGroups.containsKey(ep.getMajorIn()))
            {
                int groupNum = ++arrangedGroupNumber;
                ep.setExamGroup(groupNum);

                categaryGroups.get(ep.getMajorIn()).add(groupNum);
            }
            else
            {
                int groupNum = ++arrangedGroupNumber;
                ArrayList<Integer> groups = new ArrayList<Integer>();
                groups.add(groupNum);

                categaryGroups.put(ep.getMajorIn(), groups);
                ep.setExamGroup(groupNum);
            }
            expertDao.addExperExamGroup(ep.getExpertId(), ep.getExamGroup());
        }

        hasArranged = true;
    }

    /// arrange groupNumber and examNumber for student
    public static void ArrangeStudentGroup(Student student)
    {
        if (!hasArranged)
        {
            GenerateGroups();
        }

        if (!categaryGroups.containsKey(student.getCategory()))
        {
            student.setExamgroup(-1);
            student.setExamnumber(-1);
            return;
        }
        ArrayList<Integer> groups = categaryGroups.get(student.getCategory());

        int randomGroupIndex = (int)(Math.random() * groups.size());
        student.setExamgroup(groups.get(randomGroupIndex));
        student.setExamnumber(GetRandomExamNumber());

        StudentDao studentDao = new StudentDao();
        studentDao.confimStudentInfo(student.getTicketNumber(), student.getExamgroup(), student.getExamnumber());
    }

    private static int GetRandomExamNumber()
    {
        boolean findRandomNumber = false;
        while (!findRandomNumber)
        {
            int number = (int)(MIN_EXAM_NUMBER + Math.random() * (MAX_EXAM_NUMBER - MIN_EXAM_NUMBER));
            if (!examNumbers.contains(number))
            {
                examNumbers.add(number);
                return number;
            }
        }
        return -1;
    }

    public static ArrayList<Expert> FakeExpertInfo()
    {
        ArrayList<Expert> experts = new ArrayList<Expert>();

        Expert expert = new Expert();
        expert.setMajorIn("a");
        expert.setExpertId(1);
        experts.add(expert);

        Expert expert2 = new Expert();
        expert2.setMajorIn("a");
        expert2.setExpertId(2);
        experts.add(expert2);

        Expert expert3 = new Expert();
        expert3.setMajorIn("b");
        expert3.setExpertId(3);
        experts.add(expert3);

        Expert expert4 = new Expert();
        expert4.setMajorIn("b");
        expert4.setExpertId(4);
        experts.add(expert4);

        return experts;
    }

    public static void main(String[] args) {
        GenerateGroups();

        for (int i=0;i<11;++i)
        {
            GetRandomExamNumber();
            int test = (int)(Math.random() * 4);
            System.out.println(test);
        }

        int i =1;
        ++i;
    }
}
