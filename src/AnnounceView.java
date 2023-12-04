


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.* ;
import javax.swing.border.EmptyBorder;

public class AnnounceView extends JFrame {
	
	private int mode ;
	
	private final JPanel postInf ;  // 放用戶、時間
	private final JTextArea content ;  // 放內文
	private final JScrollPane scrollPane ;  // 放TextArea
	private final JPanel toolPane ;  // 下方面板，放愛心、換頁、功能按鈕
	private final JPanel buttonPane ;  // 面板，整合功能按鈕
	private final JPanel editPane ;  // 放編輯畫面按鈕
	private final JPanel editButtonPane ;  // 面版，整合編輯畫面按鈕
	private final JLabel username ;  // 裝用戶
	private final JLabel editTime ;  // 裝時間
	
	private final JButton icon ;   
	private final JButton edit ;  
	private final JButton newPost ;
	private final JButton save ;
	private final JButton save_as ;
	private final JButton load ;
	private final JButton cancel ;
	
	private final JSpinner pages ;  // 換頁紐
	
	private final FlowLayout buttonLayout ;  // 按鈕排版
	
	public AnnounceView() {
		
		super( "NCU Announcement System" ) ;
		
		buttonLayout = new FlowLayout( FlowLayout.LEFT ) ;
		buttonLayout.setHgap(15) ;
		
		mode = JOptionPane.showConfirmDialog( this, "是否為發布者", "登入", JOptionPane.YES_NO_OPTION ) ;  // 選擇模式
		
		if( mode != 0 && mode != 1) {
			System.out.println( "程式關閉。" ) ;
			System.exit(0);
		}
		
		if( mode == 0 ) {  // 發布者模式
			postInf = new JPanel() ;
			content = new JTextArea("") ;
			scrollPane = new JScrollPane(content) ;
			toolPane = new JPanel() ;
			buttonPane = new JPanel() ;
			editPane = new JPanel() ;
			editButtonPane = new JPanel() ;
			username = new JLabel("進JA助教") ;
			editTime = new JLabel() ;
			icon = new JButton() ;
			edit = new JButton("編輯") ;
			newPost = new JButton("全新貼文") ;
			save = new JButton("儲存") ;
			save_as = new JButton("另存新檔") ;
			load = new JButton("匯入內容") ;
			cancel = new JButton("取消") ;
			pages = new JSpinner() ;
			
			username.setFont( new Font("Serif", Font.BOLD, 30) ) ;
			editTime.setFont( new Font("Serif", Font.BOLD, 18) ) ;
			content.setFont( new Font("Serif", Font.PLAIN, 22) ) ;
			edit.setFont( new Font("Dialog", Font.BOLD, 16) ) ;
			newPost.setFont( edit.getFont() ) ;
			edit.setPreferredSize( new Dimension(105,65) ) ;
			newPost.setPreferredSize( edit.getPreferredSize() ) ;
			pages.setPreferredSize( new Dimension(120,40) ) ;
			content.setBorder( new EmptyBorder(10,10,10,10) ) ;
			username.setBorder( new EmptyBorder(15,10,5,10) ) ;
			editTime.setBorder( new EmptyBorder(10,10,10,10) ) ;
			icon.setBorder( new EmptyBorder(5,5,5,5) ) ;
			icon.setContentAreaFilled(false) ;
			postInf.setLayout( new GridLayout(2,1) );
			postInf.add(username) ;
			postInf.add(editTime) ;
			
			buttonPane.setBorder( new EmptyBorder(5,0,5,5) ) ;
			buttonPane.setLayout( buttonLayout ) ;
			buttonPane.add( pages ) ;
			buttonPane.add( newPost ) ;
			
			toolPane.setLayout( new BorderLayout() ) ;
			toolPane.add( icon, BorderLayout.WEST ) ;
			toolPane.add( buttonPane, BorderLayout.EAST ) ;
			
			save.setFont( edit.getFont() ) ;
			save_as.setFont( edit.getFont() ) ;
			load.setFont( edit.getFont() ) ;
			cancel.setFont( edit.getFont() ) ;
			save.setPreferredSize( edit.getPreferredSize() ) ;
			save_as.setPreferredSize( edit.getPreferredSize() ) ;
			load.setPreferredSize( edit.getPreferredSize() ) ;
			cancel.setPreferredSize( edit.getPreferredSize() ) ;
			editButtonPane.setLayout( buttonLayout ) ;
			editButtonPane.setBorder( new EmptyBorder(5,5,5,5) ) ;
			editButtonPane.add( save ) ;
			editButtonPane.add( save_as ) ;
			editButtonPane.add( load ) ;
			editButtonPane.add( cancel ) ;
			editPane.setLayout( new BorderLayout() ) ;
			editPane.add( editButtonPane, BorderLayout.WEST ) ;
			
		}
		else {  // 檢視模式
			postInf = new JPanel() ;
			content = new JTextArea("") ;
			scrollPane = new JScrollPane(content) ;
			toolPane = new JPanel() ;
			buttonPane = new JPanel() ;
			editPane = null ;
			editButtonPane = null ;
			username = new JLabel("進JA助教") ;
			editTime = new JLabel() ;
			icon = new JButton() ;
			edit = null ;
			newPost = null ;
			save = null ;
			save_as = null ;
			load = null ;
			cancel = null ;
			pages = new JSpinner() ;
			
			username.setFont( new Font("Serif", Font.BOLD, 30) );
			editTime.setFont( new Font("Serif", Font.BOLD, 18) ) ;
			content.setFont( new Font("Serif", Font.PLAIN, 22) ) ;
			content.setBorder( new EmptyBorder(10,10,10,10) ) ;
			username.setBorder( new EmptyBorder(15,10,5,10) ) ;
			editTime.setBorder( new EmptyBorder(10,10,10,10) ) ;
			icon.setBorder( new EmptyBorder(5,5,5,5) ) ;
			icon.setBackground( buttonPane.getBackground() ) ;
			icon.setContentAreaFilled(false) ;
			postInf.setLayout( new GridLayout(2,1) );
			postInf.add(username) ;
			postInf.add(editTime) ;
			
			pages.setPreferredSize( new Dimension(120,40) ) ;
			buttonPane.setBorder( new EmptyBorder(5,0,5,5) ) ;
			buttonPane.setLayout( buttonLayout ) ;
			buttonPane.add( pages ) ;
			
			toolPane.setLayout( new BorderLayout() ) ;
			toolPane.add( icon, BorderLayout.WEST ) ;
			toolPane.add( buttonPane, BorderLayout.EAST ) ;
			
		}
		
		content.setEditable(false) ;
		
		add( postInf, BorderLayout.NORTH ) ;
		add( scrollPane, BorderLayout.CENTER ) ;
		add( toolPane, BorderLayout.SOUTH ) ;
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.setSize(900, 1000) ;
		this.setVisible(true) ;
		
	}
	
	public int getMode() {
		return mode;
	}
	public JPanel getPostInf() {
		return postInf ;
	}
	public JTextArea getContent() {
		return content ;
	}
	public JLabel getUsername() {
		return username ;
	}
	public JPanel getButtonPane() {
		return buttonPane ;
	}
	public JLabel getEditTime() {
		return editTime ;
	}
	public JButton getIcon() {
		return icon ;
	}
	public JPanel getToolPane() {
		return toolPane ;
	}
	public JPanel getEditPane() {
		return editPane ;
	}
	public JButton getEdit() {
		return edit ;
	}
	public JButton getNewPost() {
		return newPost ;
	}
	public JButton getSave() {
		return save ;
	}
	public JButton getSaveAs() {
		return save_as ;
	}
	public JButton getLoad() {
		return load ;
	}
	public JButton getCancel() {
		return cancel ;
	}
	public JSpinner getPages() {
		return pages ;
	}
	
}
