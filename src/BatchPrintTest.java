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
 * ���ڲ���������ӡ�ļ����ڵ��ļ�
 * @author wanghui
 *
 */
public class BatchPrintTest {
	
	//�������
	public static void main(String[] args) throws IOException, PrinterException {   
        // This is the path where the file's name you want to take.   
        String path = "D:/pdftest/"; 
        File filePath = new File(path);
        
        /**
         * �жϸ���path��ȡ��filePath�Ƿ�Ϊ�ļ��У��������ѭ����ӡ�������������Ϊ���ļ�·����ֱ�Ӵ�ӡ
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
	 * ��ȡ�ļ��е�ַ�µ��ļ���
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
	 * ��ӡ����
	 */
	 private static void printWithPaper(PDDocument document)throws IOException, PrinterException
	    {
	        PrinterJob job = PrinterJob.getPrinterJob();
	        job.setPageable(new PDFPageable(document));

	        // define custom paper
	        Paper paper = new Paper();
	        //ֽ�Ŵ�С����ΪA4 
	        /*
	         * 	A4ֽ�ųߴ磺210mm * 297mm
				1Ӣ�磺2.54cm
				������ĻDPI(Dots per inch)Ϊ72���أ�pixels��ÿӢ�磬����һ�£����Ϊ72px/2.54 = 28.34px
				������������ֱ����£�A4ֽ�Ŵ�С����Ļ�ϵ����سߴ磺
				DPI��72px/inch��A4 size��598 * 842����
				DPI��96px/inch��A4 size��794* 1123������default��
				DPI��120px/inch��A4 size��1487* 2105����
				DPI��150px/inch��A4 size��1240* 1754����
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
