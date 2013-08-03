//package test.com.intuit.txtweb.hoi.platform.util;
//
//import static org.testng.Assert.assertEquals;
//
//import org.testng.annotations.Test;
//
//import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
//import com.intuit.txtweb.hoi.platform.util.MessageParser;
//
//@Test
//public class MessageParserTest {
//
//	@Test
//	public void testMessageParser() throws InvalidOperationException{
//		String message = "test create";
//		assertEquals( MessageParser.getGroupName(message), "test");
//		assertEquals( MessageParser.getOperation(message).toString().toLowerCase(), "create");
//		
//		message = "test register";
//		assertEquals( MessageParser.getGroupName(message), "test");
//		assertEquals( MessageParser.getOperation(message).toString().toLowerCase(), "register");
//		
//		message = "test send <<< &&* 9 testmessage to be sent!";
//		assertEquals( MessageParser.getGroupName(message), "test");
//		assertEquals( MessageParser.getOperation(message).toString().toLowerCase(), "send");
//		assertEquals( MessageParser.getText(message).toString(), "<<< &&* 9 testmessage to be sent!");
//	}
//	
//
//	@Test
//	public void testMessageParserSpace() throws InvalidOperationException{
//		String message = " create create";
//		assertEquals( MessageParser.getGroupName(message), "create");
//		assertEquals( MessageParser.getOperation(message).toString().toLowerCase(), "create");
//		
//		message = " create   register";
//		assertEquals( MessageParser.getGroupName(message), "create");
//		assertEquals( MessageParser.getOperation(message).toString().toLowerCase(), "register");
//		
//		message = "  create       send       <<< &&* 9   testmessage to be sent!";
//		assertEquals( MessageParser.getGroupName(message), "create");
//		assertEquals( MessageParser.getOperation(message).toString().toLowerCase(), "send");
//		assertEquals( MessageParser.getText(message).toString(), "<<< &&* 9 testmessage to be sent!");
//	}
//}

