package autodesk.tests;

public class FIORIDeployment extends LocalFIORI{

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
	
		DevFIORI dev = new DevFIORI();
		dev.devFIORIInititation();
		dev.validateDevFioriData();

		if(DEVPassPercentage.equals("100%")) {
			ReleaseTaskAndTransport RTAT = new ReleaseTaskAndTransport();
			RTAT.releaseTRTask();
			RTAT.releaseTransport();			
			QAFIORI QA = new QAFIORI();			
			QA.qaFIORIInititation();
			QA.validateQAFioriData();
		}else {
			System.out.println("Dev Testing Failed");
		}
	}

}
