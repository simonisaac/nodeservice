
public class CallbackTest {

	/*private static Callback callback;

	private static Node testNode;

	@BeforeClass
	public static void setUp() throws Exception {
		callback = new Callback();

		try {
			Mongo mongo = new Mongo("localhost", 27017);

			DB db = mongo.getDB("mydb");
			
			DBCollection coll = db.getCollection("testCollection");
			System.out.println(coll.count());
			coll.drop();
			coll = db.getCollection("testCollection");
			
			System.out.println(coll.count());
			
			callback.init(coll);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		testNode = new Node("testParent", "testType");
		testNode.getFields().put("attr1", "value1");

		NodeSet childSet = new NodeSet("childSet1");
		testNode.addChildNodeSet(childSet);

		Node childNode = new Node("child1", "childType");
		childSet.addNode(childNode);
		childNode.getFields().put("cattr1", "cvalue1");
	}

	@Test
	public void testProcessor () throws Throwable {
		
		// excel instructions
		XmlValidator validator = new XmlValidator();
		validator.init();
		XmlToSheetTransformer transformer = new XmlToSheetTransformer();
		transformer.setValidator(validator);
		EucExcelInstructionProvider provider = new EucExcelInstructionProvider();
		provider.setXmlToSheetTransformer(transformer);
		provider.init();
		
		Resource resourceXML = new ClassPathResource("test3.xml");
		FileInputStream processingInstructionsStream = new FileInputStream(resourceXML.getFile());

		provider.loadExcelInstruction(processingInstructionsStream);
		
		// node def
		EucNodeDefProvider nodeDefProvider = new EucNodeDefProvider();
		nodeDefProvider.init();
		
		Resource languageXML = new ClassPathResource("testLanguage.xml");
		FileInputStream languageStream = new FileInputStream(languageXML.getFile());
		
		nodeDefProvider.loadNodeDefinition(languageStream);
		
		// processor
		Resource excelDoc = new ClassPathResource("test3.xlsx");
		FileInputStream excelStream = new FileInputStream(excelDoc.getFile());
		Sheet sheet = provider.getSheet("testSheet-1");
		ProcessSpreadsheetSAX sax = new ProcessSpreadsheetSAX();
		Callback callback = new Callback();
		callback.init();
		sax.setCallback(callback);
		sax.setXmlToSheetTransformer(transformer);
		sax.process(excelStream, sheet);
	}
	
	@Test
	public void testCreateRootNode() {
		callback.createRootNode(this.testNode);

	}
	

	/*@Test
	public void testCreateNode() {
		for (Node childNode : this.testNode.getChildNodeSet("childSet1")
				.getNodes().values()) {
			callback.createNode(this.testNode.getId(), childNode);
		}
	}*/

}
