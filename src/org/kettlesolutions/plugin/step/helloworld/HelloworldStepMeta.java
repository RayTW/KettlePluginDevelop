package org.kettlesolutions.plugin.step.helloworld;

import java.util.List;
import java.util.Map;

import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;
/**
 * 
 * @author liurh
 * @date   2016年1月29日
 * @intro  获取元数据类
 *
 */

@Step(
		id="Helloworld",
		name="name",
		description="description",
		categoryDescription="categoryDescription", 
		image="org/kettlesolutions/plugin/step/helloworld/HelloWorld.png",
		i18nPackageName="org.kettlesolutions.plugin.step.helloworld"
) 
public class HelloworldStepMeta extends BaseStepMeta implements StepMetaInterface {

	private static Class<?> PKG = HelloworldStep.class; //for i18n
	public enum Tag {
		field_name,
		value_name,
		age_name,
		column_name
	};
	
	// 定义私有域
	private String fieldName;
	private String valueName;
	private String ageName;
	private String columnName;
	// 存储列名
	private static String[] columns;
		

	/**
	 * checks parameters, adds result to List<CheckResultInterface>
	 * used in Action > Verify transformation
	 */
	public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, 
			RowMetaInterface prev, String input[], String output[], RowMetaInterface info) {
		
		if (Const.isEmpty(fieldName)) {
			CheckResultInterface error = new CheckResult(
				CheckResult.TYPE_RESULT_ERROR, 
				BaseMessages.getString(PKG, "HelloworldMeta.CHECK_ERR_NO_FIELD"), 
				stepMeta
			);
			remarks.add(error);
		} else {
			CheckResultInterface ok = new CheckResult(
				CheckResult.TYPE_RESULT_OK, 
				BaseMessages.getString(PKG, "HelloworldMeta.CHECK_OK_FIELD"), 
				stepMeta
			);
			remarks.add(ok);
		}
	}

	/**
	 *	creates a new instance of the step (factory)
	 */
	public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface,
			int copyNr, TransMeta transMeta, Trans trans) {
		return new HelloworldStep(stepMeta, stepDataInterface, copyNr, transMeta, trans);
	}

	/**
	 * creates new instance of the step data (factory)
	 */
	public StepDataInterface getStepData() {
		return new HelloworldStepData();
	}
	
	@Override
	public String getDialogClassName() {
		return HelloworldStepDialog.class.getName();
	}

	/**
	 * deserialize from xml 
	 * databases = list of available connections
	 * counters = list of sequence steps
	 */
	public void loadXML(Node stepDomNode, List<DatabaseMeta> databases,
			Map<String, Counter> sequenceCounters) throws KettleXMLException {
		fieldName = XMLHandler.getTagValue(stepDomNode, Tag.field_name.name());
		valueName = XMLHandler.getTagValue(stepDomNode, Tag.value_name.name());
		ageName = XMLHandler.getTagValue(stepDomNode, Tag.age_name.name());
		columnName = XMLHandler.getTagValue(stepDomNode, Tag.column_name.name());
	}
	
	/**
	 * @Override
	 */
	public String getXML() throws KettleException {
		StringBuilder xml = new StringBuilder();
		xml.append(XMLHandler.addTagValue(Tag.field_name.name(), fieldName));
		xml.append(XMLHandler.addTagValue(Tag.value_name.name(), valueName));
		xml.append(XMLHandler.addTagValue(Tag.age_name.name(), ageName));
		xml.append(XMLHandler.addTagValue(Tag.column_name.name(), columnName));
		return xml.toString();
	}
	
	/**
	 * De-serialize from repository (see loadXML)
	 */
	public void readRep(Repository repository, ObjectId stepIdInRepository,
			List<DatabaseMeta> databases, Map<String, Counter> sequenceCounters)
			throws KettleException {
		fieldName = repository.getStepAttributeString(stepIdInRepository, Tag.field_name.name());
		valueName = repository.getStepAttributeString(stepIdInRepository, Tag.value_name.name());
		ageName = repository.getStepAttributeString(stepIdInRepository, Tag.age_name.name());
		columnName = repository.getStepAttributeString(stepIdInRepository, Tag.column_name.name());
	}

	/**
	 * serialize to repository
	 */
	public void saveRep(Repository repository, ObjectId idOfTransformation, ObjectId idOfStep)
			throws KettleException {
		repository.saveStepAttribute(idOfTransformation, idOfStep, Tag.field_name.name(), fieldName);
		repository.saveStepAttribute(idOfTransformation, idOfStep, Tag.value_name.name(), valueName);
		repository.saveStepAttribute(idOfTransformation, idOfStep, Tag.age_name.name(), ageName);
		repository.saveStepAttribute(idOfTransformation, idOfStep, Tag.column_name.name(), columnName);
	}
	
	
	/**
	 * initiailize parameters to default
	 */
	public void setDefault() {
		fieldName = "FieldName";
		valueName = "ValueName";
		ageName = "10";
	}
	
	// 添加一列
	@Override
	public void getFields(RowMetaInterface inputRowMeta, String name, RowMetaInterface[] info, StepMeta nextStep,
			VariableSpace space, Repository repository, IMetaStore metaStore) throws KettleStepException {
		String realFieldName = space.environmentSubstitute(fieldName);
		ValueMetaInterface field = new ValueMeta(realFieldName, ValueMetaInterface.TYPE_STRING);
		field.setOrigin(name);		
		inputRowMeta.addValueMeta(field);
	}
	
	
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/**
	 * @return the valueName
	 */
	public String getValueName() {
		return valueName;
	}

	/**
	 * @param valueName the valueName to set
	 */
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getAgeName() {
		return ageName;
	}

	public void setAgeName(String ageName) {
		this.ageName = ageName;
	}

	public static String[] getColumns() {
		return columns;
	}

	public static void setColumns(String[] columns) {
		HelloworldStepMeta.columns = columns;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
