package autodesk.tests;

public class FIORIDeployment extends LocalFIORI{

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {

		/*
		 * LocalFIORI local = new LocalFIORI(); local.localFIORIInititation();
		 * local.validateGraphData();
		 */

		DevFIORI dev = new DevFIORI();
		dev.devFIORIInititation();
		dev.validateDevFioriData();

		if(DEVPassPercentage.equals("100%")) {
			QAFIORI QA = new QAFIORI();
			QA.qaFIORIInititation();
			QA.validateQAFioriData();
		}else {
			System.out.println("Dev Testing Failed");
		}
	}

}
