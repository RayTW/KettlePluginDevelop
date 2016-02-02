package org.kettlesolutions.plugin.step.helloworld;

import org.eclipse.swt.SWT;
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
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
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
	
	// 构造方法
	public HelloworldStepDialog(Shell parent, Object baseStepMeta, TransMeta transMeta, String stepname) {
		// 初始化父类构造方法
	    super( parent, (BaseStepMeta) baseStepMeta, transMeta, stepname );
		// 获取元数据
	    input = (HelloworldStepMeta) baseStepMeta;
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
		
		// Some buttons
		wOK=new Button(shell, SWT.PUSH);
		wOK.setText(BaseMessages.getString(PKG, "System.Button.OK")); //$NON-NLS-1$
		wCancel=new Button(shell, SWT.PUSH);
		wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel")); //$NON-NLS-1$

		setButtonPositions(new Button[] { wOK, wCancel }, margin, lastControl);

		// Add listeners
		lsCancel   = new Listener() { public void handleEvent(Event e) { cancel(); } };
		lsOK       = new Listener() { public void handleEvent(Event e) { ok();     } };
		
		wCancel.addListener(SWT.Selection, lsCancel);
		wOK.addListener    (SWT.Selection, lsOK    );
		
		lsDef=new SelectionAdapter() { public void widgetDefaultSelected(SelectionEvent e) { ok(); } };
		
		wStepname.addSelectionListener( lsDef );
		wFieldname.addSelectionListener( lsDef );
		wValuename.addSelectionListener( lsDef );
		
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
	}
	
	private void cancel()
	{
		stepname=null;
		input.setChanged(changed);
		dispose();
	}
	
	private void ok()
	{
		if (Const.isEmpty(wStepname.getText())) return;

		stepname = wStepname.getText(); // return value
		
		input.setFieldName(wFieldname.getText());
		input.setValueName(wValuename.getText());
		
		dispose();
	}

}
