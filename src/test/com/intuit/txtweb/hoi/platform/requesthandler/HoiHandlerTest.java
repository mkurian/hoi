//package test.com.intuit.txtweb.hoi.platform.requesthandler;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.intuit.txtweb.hoi.platform.requesthandler.HoiFactory;
//import com.intuit.txtweb.hoi.platform.requesthandler.HoiHandler;
//import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
//import com.intuit.txtweb.hoi.platform.util.Operation;
//
//@Test
//public class HoiHandlerTest {
//
//	//@Test
//	public void testCreateGroupAddAdmin() throws InvalidOperationException  {
//		HoiFactory factory = new HoiFactory();
//		HoiHandler handler = factory.getHandler(Operation.CREATE);
//		String response = handler.handleMessage("groupName", "userName", "mobileHash", Operation.CREATE, "Text");
//		Assert.assertTrue(response.contains("Please choose another name"));
//	}
//	
//	//@Test
//	public void testRegisterUser() throws InvalidOperationException  {
//		HoiFactory factory = new HoiFactory();
//		HoiHandler handler = factory.getHandler(Operation.REGISTER);
//		String response = handler.handleMessage("groupName", "userName1", "mobileHash001", Operation.REGISTER, "Text");
//		Assert.assertTrue(response.contains("Thank you for registering"));
//	}
//	
//	//@Test
//	public void testUnRegisterUser() throws InvalidOperationException  {
//		HoiFactory factory = new HoiFactory();
//		HoiHandler handler = factory.getHandler(Operation.UNREGISTER);
//		String response = handler.handleMessage("groupName", "userName1", "mobileHash001", Operation.UNREGISTER, "Text");
//		Assert.assertTrue(response.contains("You will not receive messages from groupName"));
//	}
//	
//	@Test
//	public void testSendMessage() throws InvalidOperationException  {
//		HoiFactory factory = new HoiFactory();
//		HoiHandler handler = factory.getHandler(Operation.SEND);
//		String response = handler.handleMessage("groupName", "userName", "mobileHash", Operation.SEND, "Text message to be sent");
//		Assert.assertTrue(response.contains("You will not receive messages from groupName"));
//		
//		response = handler.handleMessage("groupName", "userName", "mobileHash001", Operation.SEND, "Text message to be sent");
//		Assert.assertTrue(response.contains("Sorry, you don't have permission to send message"));
//	}
//}
