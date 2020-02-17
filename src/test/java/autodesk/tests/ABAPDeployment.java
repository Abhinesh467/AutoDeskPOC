package autodesk.tests;

public class ABAPDeployment {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		ABAPCodeTest ACT = new ABAPCodeTest();
		ACT.driverInvoke();
		ACT.ExportProgramData();
		ACT.testDataComparison();
		Thread.sleep(2000);
		
		if(ACT.ABAPQAPassPercentage.equals("100%")) {
			ABAPReleaseTaskAndTransport ABAPR = new ABAPReleaseTaskAndTransport();
			ABAPR.releaseTRTask();
			ABAPR.releaseTransport();
			Thread.sleep(2000);
			ABAPQACodeTest ACTQ = new ABAPQACodeTest();
			ACTQ.driverInvoke();
			ACTQ.ExportProgramData();
			ACTQ.testDataComparison();
		}
		
	}

}
