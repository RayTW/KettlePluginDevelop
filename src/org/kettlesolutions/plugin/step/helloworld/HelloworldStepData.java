package org.kettlesolutions.plugin.step.helloworld;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;
/**
 * 
 * @author liurh
 * @date   2016年1月28日
 * @intro  数据维护，临时存储类
 *
 */
public class HelloworldStepData extends BaseStepData implements StepDataInterface {

	public RowMetaInterface outputRowMeta;

}
