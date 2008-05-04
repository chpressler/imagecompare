package timer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Created on 09.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author ASW
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Shutdown {

	public static void main(String[] args) {
		new Shutdown().doIt();
	}

	/**
	 * 
	 */
	private void doIt() {
		// TODO Auto-generated method stub
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("c:/shutdown.vbs");
			bw = new BufferedWriter(fw);

			bw.write(
				"set WshShell = CreateObject(\"WScript.Shell\")\r\nWshShell.SendKeys \"^{ESC}^{ESC}^{ESC}{UP}{ENTER}{ENTER}\"\r\n");

			bw.flush();
                                                                bw.close();

			Runtime.getRuntime().exec(
				"cmd /c start /min cscript c:\\shutdown.vbs");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}