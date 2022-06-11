package com.lebrwcd;/**
 * @author lebrwcd
 * @date 2022/5/3
 * @note
 */

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lebrwcd.bean.ExcelTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * ClassName excelTest
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/3
 */
public class excelTest {

    @Test
    public void write(){

        String fileName = "D:\\test.xlsx";
       // EasyExcel.write(fileName, ExcelTest.class).sheet("学生").doWrite(data());

    }

    @Test
    public void read(){
        String fileName = "d:\\Users\\Lebr7Wcd\\Desktop\\test.xlsx";
        EasyExcel.read(fileName,ExcelTest.class,new ExcelListner()).sheet().doRead();
    }
    //
    //public static ArrayList<ExcelTest>  data(){
    //
    //    ArrayList<ExcelTest> data = new ArrayList<>();
    //    for (int i = 0; i < 10; i++) {
    //        ExcelTest excelTest = new ExcelTest();
    //        excelTest.setSno(i);
    //        excelTest.setSname("zs"+i);
    //        data.add(excelTest);
    //    }
    //
    //    return data;
    //}
}
class ExcelListner extends AnalysisEventListener{

    //一行一行读取
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        System.out.println("数据:**" + o);
    }

    @Override
    public void invokeHead(Map headMap, AnalysisContext context) {
        System.out.println("表头:" + headMap);
    }

    //读取完执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读完了");
    }
}
