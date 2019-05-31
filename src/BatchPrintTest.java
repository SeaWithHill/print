import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;

/**
 * 用于测试批量打印文件夹内的文件
 * @author wanghui
 *
 */
public class BatchPrintTest {
	
	//测试入口
	public static void main(String[] args) throws IOException, PrinterException {   
        // This is the path where the file's name you want to take.   
        String path = "D:/pdftest/"; 
        File filePath = new File(path);
        
        /**
         * 判断根据path获取的filePath是否为文件夹，如果是则循环打印，如果不是则认为是文件路径，直接打印
         */
        if(filePath.isDirectory()){
        	File[] array = getFile(filePath); 
        	String fileName = null;
        	for(int i = 0; i<array.length; i++){
        		fileName = array[i].getPath();
        		PDDocument document = PDDocument.load(new File(fileName));
        		printWithPaper(document);
        	}
        }else{
        	PDDocument document = PDDocument.load(new File(path));
    		printWithPaper(document);
        }
    }
	/**
	 * 获取文件夹地址下的文件名
	 * @param path
	 */
	private static File[] getFile(File path){   
		    
        File[] array = path.listFiles(); 
          
        for(int i=0;i<array.length;i++){   
            if(array[i].isFile()){     
                System.out.println("#####" + array[i]);    
            }  
        } 
        return array;
	}
	 
	/**
	 * 打印功能
	 */
	 private static void printWithPaper(PDDocument document)throws IOException, PrinterException
	    {
	        PrinterJob job = PrinterJob.getPrinterJob();
	        job.setPageable(new PDFPageable(document));

	        // define custom paper
	        Paper paper = new Paper();
	        //纸张大小设置为A4 
	        /*
	         * 	A4纸张尺寸：210mm * 297mm
				1英寸：2.54cm
				假设屏幕DPI(Dots per inch)为72像素（pixels）每英寸，计算一下，结果为72px/2.54 = 28.34px
				下面给出常见分辨率下，A4纸张大小在屏幕上的像素尺寸：
				DPI：72px/inch，A4 size（598 * 842）；
				DPI：96px/inch，A4 size（794* 1123）；（default）
				DPI：120px/inch，A4 size（1487* 2105）；
				DPI：150px/inch，A4 size（1240* 1754）；
	         */
	        paper.setSize(598,842); // 1/72 inch
	        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()); // no margins

	        // custom page format
	        PageFormat pageFormat = new PageFormat();
	        pageFormat.setPaper(paper);
	        
	        // override the page format
	        Book book = new Book();
	        // append all pages
	        book.append(new PDFPrintable(document), pageFormat, document.getNumberOfPages());
	        job.setPageable(book);
	        
	        job.print();
	    }

}
