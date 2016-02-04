package org.kettlesolutions.plugin.step.helloworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.TableView;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;
/**
 * 
 * @author liurh
 * @date   2016年1月28日
 * @intro  插件主窗口类，负责视图
 *
 */
public class HelloworldStepDialog extends BaseStepDialog implements StepDialogInterface {	

	// 类变量HelloworldStepMeta
	private static Class<?> PKG = HelloworldStepMeta.class;
	// 元数据
	private HelloworldStepMeta input;
	// 支持变量的文本输入框(列名)
	private TextVar wFieldname;
	// 支持变量的文本输入框(值)
	private TextVar wValuename;
	
	// 下拉列表(选择字段)
	private CCombo wColumnname;
	
	// 支持变量的文本输入框(年龄增加值)
	private TextVar wAgename;
	// 列名列表
	private Map<String, Integer> inputFields;	
	private ColumnInfo[] colinf;
	private String[] columns;
	
	private Label wlFields;
	private TableView wFields;
	private FormData fdlFields, fdFields;
	
	// 构造方法
	public HelloworldStepDialog(Shell parent, Object baseStepMeta, TransMeta transMeta, String stepname) {
		// 初始化父类构造方法
	    super( parent, (BaseStepMeta) baseStepMeta, transMeta, stepname );
		// 获取元数据
	    input = (HelloworldStepMeta) baseStepMeta;
	    // map类初始化
	    inputFields = new HashMap<String, Integer>();
	}

	public String open() {
		Shell parent = getParent();
		Display display = parent.getDisplay();

		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
 		props.setLook(shell);
 		setShellImage(shell, input);
        
		ModifyListener lsMod = new ModifyListener() 
		{
			public void modifyText(ModifyEvent e) 
			{
				input.setChanged();
			}
		};
		changed = input.hasChanged();

		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth  = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		shell.setLayout(formLayout);
		shell.setText(BaseMessages.getString(PKG, "HelloworldDialog.Shell.Title")); //$NON-NLS-1$
		
		int middle = props.getMiddlePct();
		int margin = Const.MARGIN;

		// Stepname line
		wlStepname=new Label(shell, SWT.RIGHT);
		wlStepname.setText(BaseMessages.getString(PKG, "HelloworldDialog.Stepname.Label")); //$NON-NLS-1$
 		props.setLook(wlStepname);
		fdlStepname=new FormData();
		fdlStepname.left = new FormAttachment(0, 0);
		fdlStepname.right= new FormAttachment(middle, -margin);
		fdlStepname.top  = new FormAttachment(0, margin);
		wlStepname.setLayoutData(fdlStepname);
		wStepname=new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wStepname.setText(stepname);
 		props.setLook(wStepname);
		wStepname.addModifyListener(lsMod);
		fdStepname=new FormData();
		fdStepname.left = new FormAttachment(middle, 0);
		fdStepname.top  = new FormAttachment(0, margin);
		fdStepname.right= new FormAttachment(100, 0);
		wStepname.setLayoutData(fdStepname);
		Control lastControl = wStepname;
		
		// Fieldname line
		Label wlFieldname = new Label(shell, SWT.RIGHT);
		wlFieldname.setText(BaseMessages.getString(PKG, "HelloworldDialog.Fieldname.Label")); //$NON-NLS-1$
 		props.setLook(wlFieldname);
		FormData fdlFieldname = new FormData();
		fdlFieldname.left = new FormAttachment(0, 0);
		fdlFieldname.right= new FormAttachment(middle, -margin);
		fdlFieldname.top  = new FormAttachment(lastControl, margin);
		wlFieldname.setLayoutData(fdlFieldname);
		wFieldname=new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		props.setLook(wFieldname);
		wFieldname.addModifyListener(lsMod);
		FormData fdFieldname = new FormData();
		fdFieldname.left = new FormAttachment(middle, 0);
		fdFieldname.top  = new FormAttachment(lastControl, margin);
		fdFieldname.right= new FormAttachment(100, 0);
		wFieldname.setLayoutData(fdFieldname);
		lastControl = wFieldname;
		
		// Value line
		Label wlValuename = new Label(shell, SWT.RIGHT);
		wlValuename.setText(BaseMessages.getString(PKG, "HelloworldDialog.Valuename.Label")); //$NON-NLS-1$
 		props.setLook(wlValuename);
		FormData fdlValuename = new FormData();
		fdlValuename.left = new FormAttachment(0, 0);
		fdlValuename.right= new FormAttachment(middle, -margin);
		fdlValuename.top  = new FormAttachment(lastControl, margin);
		wlValuename.setLayoutData(fdlValuename);
		wValuename=new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		props.setLook(wValuename);
		wValuename.addModifyListener(lsMod);
		FormData fdValuename = new FormData();
		fdValuename.left = new FormAttachment(middle, 0);
		fdValuename.top  = new FormAttachment(lastControl, margin);
		fdValuename.right= new FormAttachment(100, 0);
		wValuename.setLayoutData(fdValuename);
		lastControl = wValuename;
		
		// select column
		Label wlColumnname = new Label( shell, SWT.RIGHT );
	    wlColumnname.setText( BaseMessages.getString( PKG, "HelloworldDialog.changeColumn.Label" ) );
	    props.setLook( wlColumnname );
	    FormData fdlColumnname = new FormData();
	    fdlColumnname.left = new FormAttachment( 0, 0 );
	    fdlColumnname.right = new FormAttachment( middle, -margin );
	    fdlColumnname.top = new FormAttachment( lastControl, margin );
	    wlColumnname.setLayoutData( fdlColumnname );
	    wColumnname = new CCombo( shell, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER );
	    wColumnname.setItems( getColumns() );
	    props.setLook( wColumnname );
	    FormData fdColumnname = new FormData();
	    fdColumnname.left = new FormAttachment( middle, 0 );
	    fdColumnname.top = new FormAttachment( lastControl, margin );
	    fdColumnname.right = new FormAttachment( 100, 0 );
	    wColumnname.setLayoutData( fdColumnname );
	    lastControl = wColumnname;
		
		// ageAdd line
		Label wlAgename = new Label(shell, SWT.RIGHT);
		wlAgename.setText(BaseMessages.getString(PKG, "HelloworldDialog.Agename.Label")); //$NON-NLS-1$
 		props.setLook(wlAgename);
		FormData fdlAgename = new FormData();
		fdlAgename.left = new FormAttachment(0, 0);
		fdlAgename.right= new FormAttachment(middle, -margin);
		fdlAgename.top  = new FormAttachment(lastControl, margin);
		wlAgename.setLayoutData(fdlAgename);
		wAgename=new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		props.setLook(wAgename);
		wAgename.addModifyListener(lsMod);
		FormData fdAgename = new FormData();
		fdAgename.left = new FormAttachment(middle, 0);
		fdAgename.top  = new FormAttachment(lastControl, margin);
		fdAgename.right= new FormAttachment(100, 0);
		wAgename.setLayoutData(fdAgename);
		lastControl = wAgename;	
		
		// Some buttons
		wOK=new Button(shell, SWT.PUSH);
		wOK.setText(BaseMessages.getString(PKG, "System.Button.OK")); //$NON-NLS-1$
		wCancel=new Button(shell, SWT.PUSH);
		wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel")); //$NON-NLS-1$

		setButtonPositions(new Button[] { wOK, wCancel }, margin, null);
		
	    // Table with fields
	    wlFields = new Label( shell, SWT.NONE );
	    wlFields.setText( BaseMessages.getString( PKG, "HelloworldDialog.changeColumn.Label" ) );
	    props.setLook( wlFields );
	    fdlFields = new FormData();
	    fdlFields.left = new FormAttachment( 0, 0 );
	    fdlFields.top = new FormAttachment( lastControl, margin );
	    wlFields.setLayoutData( fdlFields );
	    lastControl = wlFields;

	    final int FieldsCols = 1;
	    final int FieldsRows = 1;

	    colinf = new ColumnInfo[FieldsCols];
	    colinf[0] =
	      new ColumnInfo(
	        BaseMessages.getString( PKG, "HelloworldDialog.changeColumn.Label" ), ColumnInfo.COLUMN_TYPE_CCOMBO,
	        new String[] { "" }, false );
	    wFields =
	      new TableView(
	        transMeta, shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI, colinf, FieldsRows, lsMod, props );

	    fdFields = new FormData();
	    fdFields.left = new FormAttachment( 0, 0 );
	    fdFields.top = new FormAttachment( lastControl, margin );
	    fdFields.right = new FormAttachment( 100, 0 );
	    fdFields.bottom = new FormAttachment( wOK, -2 * margin );
	    wFields.setLayoutData( fdFields );
	    lastControl = wFields;
		
	    //
	    // Search the fields in the background
		final Runnable runnable = new Runnable() {
			public void run() {
				StepMeta stepMeta = transMeta.findStep(stepname);
				if (stepMeta != null) {
					try {
						// 获取列集合
						RowMetaInterface row = transMeta.getPrevStepFields(stepMeta);

						// Remember these fields...
						for (int i = 0; i < row.size(); i++) {
							// 获取列名存入map
							inputFields.put(row.getValueMeta(i).getName(), Integer.valueOf(i));
						}
						setComboBoxes();
					} catch (KettleException e) {
						logError(BaseMessages.getString(PKG, "System.Dialog.GetFieldsFailed.Message"));
					}
				}
			}
		};
	    new Thread( runnable ).start();
		
		// Add listeners
		lsCancel   = new Listener() { public void handleEvent(Event e) { cancel(); } };
		lsOK       = new Listener() { public void handleEvent(Event e) { ok();     } };
		
		wCancel.addListener(SWT.Selection, lsCancel);
		wOK.addListener    (SWT.Selection, lsOK    );
		
		lsDef=new SelectionAdapter() { public void widgetDefaultSelected(SelectionEvent e) { ok(); } };
		
		wStepname.addSelectionListener( lsDef );
		wFieldname.addSelectionListener( lsDef );
		wValuename.addSelectionListener( lsDef );
		wAgename.addSelectionListener( lsDef );
		wColumnname.addSelectionListener( lsDef );
		
		// Detect X or ALT-F4 or something that kills this window...
		shell.addShellListener(	new ShellAdapter() { public void shellClosed(ShellEvent e) { cancel(); } } );

		// Populate the data of the controls
		//
		getData();

		// Set the shell size, based upon previous time...
		setSize();
		
		input.setChanged(changed);
	
		shell.open();
		while (!shell.isDisposed())
		{
				if (!display.readAndDispatch()) display.sleep();
		}
		return stepname;
	}

	/**
	 * Copy information from the meta-data input to the dialog fields.
	 */ 
	public void getData()
	{
		wStepname.selectAll();
		wFieldname.setText(Const.NVL(input.getFieldName(), ""));
		wValuename.setText(Const.NVL(input.getValueName(), ""));
		wAgename.setText(Const.NVL(input.getAgeName() , ""));
		wColumnname.setText(Const.NVL(input.getColumnName(), ""));
	}
	
	private void cancel()
	{
		stepname=null;
		input.setChanged(changed);
		dispose();
	}
	
	private void ok() {
		if (Const.isEmpty(wStepname.getText()))
			return;

		stepname = wStepname.getText(); // return value

		input.setFieldName(wFieldname.getText());
		input.setValueName(wValuename.getText());
		input.setAgeName(wAgename.getText());
		input.setColumnName(wColumnname.getText());

		dispose();
	}

	protected void setComboBoxes() {
		// Something was changed in the row.
		//
		final Map<String, Integer> fields = new HashMap<String, Integer>();

		// Add the currentMeta fields...
		fields.putAll(inputFields);

		Set<String> keySet = fields.keySet();
		List<String> entries = new ArrayList<String>(keySet);

		String[] fieldNames = entries.toArray(new String[entries.size()]);

		Const.sortStrings(fieldNames);
		colinf[0].setComboValues(fieldNames);
	}
	
	private String[] getColumns(){
		StepMeta stepMeta = transMeta.findStep(stepname);
		if (stepMeta != null) {
			try {
				// 获取列集合
				RowMetaInterface row = transMeta.getPrevStepFields(stepMeta);
				columns = new String[row.size()];
				for (int i = 0; i < row.size(); i++) {
					// 获取列名存入数组
					columns[i] = row.getValueMeta(i).getName();
				}
			} catch (KettleException e) {
				logError(BaseMessages.getString(PKG, "System.Dialog.GetFieldsFailed.Message"));
			}
		}
		return columns;
	}

}
