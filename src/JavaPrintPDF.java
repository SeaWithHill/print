import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;

public class JavaPrintPDF {

	public static void main(String[] args) {
		FileInputStream fiStream = null;
		try {
			fiStream = new FileInputStream("d:\\pdf.pdf");
		} catch (FileNotFoundException ffne) {
			ffne.printStackTrace();
		}
		if (fiStream == null) {
			return;
		}

		// 这是要打印文件的格式，如果是PDF文档要设为自动识别
		DocFlavor fileFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		// 2.得到要打印的文档类DOC
		Doc myDoc = new SimpleDoc(fiStream, fileFormat, null);
		// 3.生成一个打印属性设置对象
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(new Copies(5));// Copies-打印份数5份
		aset.add(MediaSizeName.ISO_A4);// A4纸张
		aset.add(Sides.ONE_SIDED);// 双面打印
		// 4.关键一步，得到当前机器上所有已经安装的打印机
		// 传入的参数是文档格式跟打印属性，只有支持这个格式与属性的打印机才会被找到
		// PrintService[] services = PrintServiceLookup.lookupPrintServices(
		// fileFormat, aset);
		// 定位默认的打印服务
		PrintService services = PrintServiceLookup.lookupDefaultPrintService();

		if (services != null) {
			// 5.用打印服务生成一个文档打印任务，这里用的是第一个服务
			// 也可以进行筛选，services[i].getName()可以得到打印机名称，可用名称进行比较得到自己想要的打印机
			DocPrintJob job = services.createPrintJob();
			try {
				// 6.最后一步，执行打印文档任务，传入的参数是Doc文档类，与属性(5份，双面,A4)
				job.print(myDoc, aset);// 成功后电脑会提示已有文档添加到打印队列
			} catch (PrintException pe) {
				pe.printStackTrace();
			}
		}
	}
}
