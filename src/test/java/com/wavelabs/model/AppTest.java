package com.wavelabs.model;
import org.hibernate.internal.util.xml.XmlDocument;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.wavelabs.metadata.ClassAttributes;
import com.wavelabs.metadata.HbmFileMetaData;
import com.wavelabs.metadata.PropertyAttributes;
import com.wavelabs.metadata.XmlDocumentBuilder;
import com.wavelabs.utility.Helper;

/**
 * Verifies Person domain mappings.
 * 
 * @author gopikrishnag
 *
 */
public class AppTest {
	HbmFileMetaData personHbm = null;

	/**
	 * initialize the {@link HbmFileMetaData} objects. This HbmFileMetaData
	 * object useful through out all test cases.
	 */
	@BeforeTest
	public void intillization() {
		XmlDocumentBuilder xmb = new XmlDocumentBuilder();
		XmlDocument document = xmb.getXmlDocumentObject("src/main/resources/com/wavelabs/model/Person.hbm.xml");
		personHbm = new HbmFileMetaData(document, Helper.getSessionFactory());
	}

	/**
	 * Provides data to {@link #testColumnNames(String, String)}.
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "columnNames")
	public Object[][] nameOfColumns() {
		Object[][] obj = { { "name", "NAME" }, { "phone", "PHONE" }, { "email", "EMAIL" }, { "city", "CITY" } };
		return obj;
	}

	/**
	 * Tests name of Table if {@link table="PERSON"} test case will pass. For
	 * any other name test case will fail.
	 */
	@Test(priority = 1, description = "Verifies name of Person mapping table")
	public void testTableName() {
		Assert.assertEquals(personHbm.getClassAttribute(ClassAttributes.table), "PERSON",
				"Check table name of Person domain");
	}

	/**
	 * Takes input from {@link #nameOfColumns()}, tests each property column
	 * name as per requirement.
	 * 
	 * @param propertyName
	 *            of Person domain
	 * @param columnName
	 *            associated with Person property
	 */
	@Test(dataProvider = "columnNames", priority = 2, description = "Verifies column names for properties, check column names are as per requirment.")
	public void testColumnNames(String propertyName, String columnName) {
		Assert.assertEquals(personHbm.getPropertyAttributeValue(propertyName, PropertyAttributes.column), columnName,
				"Column names not matched");
	}

	/**
	 * If name of id property column name is ID, Then test case will pass. For
	 * any other column name test case will fail.
	 */
	@Test(priority = 3, description = "Checks the name of id column.")
	public void testColumnNameOfId() {
		Assert.assertEquals(personHbm.getNameOfIdColumn(), "ID", "name of id column should be as ID");
	}

	/**
	 * Tests name of generator is assigned or not.
	 */
	@Test(priority = 4, description = "Checks name of generator is assigned or not", dependsOnMethods = "testColumnNameOfId")
	public void testNameOfGenerator() {
		Assert.assertEquals(personHbm.getNameOfGenerator(), "assigned", "Please use assigned generator");
	}
}
