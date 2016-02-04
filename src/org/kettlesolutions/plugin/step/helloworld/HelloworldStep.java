package org.kettlesolutions.plugin.step.helloworld;

import java.util.List;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
/**
 * 
 * @author liurh
 * @date   2016年1月28日
 * @intro  插件步骤处理类
 *
 */
public class HelloworldStep extends BaseStep implements StepInterface {

	// 列明组集合
	List<ValueMetaInterface> metaList;
	// 选择列类型
	private int columnType;
	// 选择列角标
	private int columnIndex;
	// long类型处理变量
	private long columnLong;
	// string类型处理变量
	private String columnString;

	// 构造方法
	public HelloworldStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta,
			Trans trans) {
		super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
		
		// 初始化
		columnIndex = 0;
		columnType = 0;
		columnLong = 0;
		columnString = null;
	}
	
	// 步骤主要数据处理方法
	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException{
		// 元数据
		HelloworldStepMeta meta = (HelloworldStepMeta) smi;
		// 数据处理
		HelloworldStepData data = (HelloworldStepData) sdi;
		
		// 获取数据
		Object[] row = getRow();
		if (row == null) {
			setOutputDone();
			return false;
		}
		
		// 第一次的处理
		if (first) {
			first = false;
			// 获取元数据
			data.outputRowMeta = getInputRowMeta().clone();
			meta.getFields(data.outputRowMeta, getStepname(), null, null, this, repository, metaStore);
			
			// 列名组设置到metaList中
			metaList = data.outputRowMeta.getValueMetaList();
			for(ValueMetaInterface vmf : metaList) {
		    	if(vmf.getName().equals(meta.getColumnName())) {
		    		// 获取选择列的类型
		    		columnType = vmf.getType();
		    		break;
		    	}
		    	columnIndex++;
		    }
		}
		
		// 设置增加列的值
		String value = meta.getValueName();

		// 判断该列的类型
		switch (columnType) {
		case ValueMetaInterface.TYPE_INTEGER:
			columnLong = (long) row[columnIndex];
		    row[columnIndex] = Long.parseLong(meta.getColumnValue()) + columnLong;
			break;

		case ValueMetaInterface.TYPE_STRING:
			columnString = (String) row[columnIndex];
		    row[columnIndex] = columnString +meta.getColumnValue();
			break;
		case ValueMetaInterface.TYPE_NUMBER:

			break;
		default:
			break;
		}
	    
		// 增加列的value值设置
		Object[] outputRow = RowDataUtil.addValueData(row, getInputRowMeta().size(), value);
		
		// 将新的一列输出
		putRow(data.outputRowMeta, outputRow);
		
		return true;
	}

}
