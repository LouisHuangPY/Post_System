

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;

public class AnnounceModel {
	
	private final AnnounceView view ;
	
	private final ArrayList<PostSerializable> postInf = new ArrayList<PostSerializable>() ;  // 因為要換頁，要儲存Post資料
	
	private final JFileChooser fileChooser ;  // 讀、匯檔
	private static ObjectInputStream read ;  // static -> 可以不用在創建時主class創建時物件化
	private static ObjectOutputStream export ;
	private static Scanner scanner ;
	
	private final ImageIcon likeIcon ;  // 圖像處理(大小)
	private final ImageIcon unlikeIcon ;
	private Image like ;
	private Image unlike ;
	
	public AnnounceModel(AnnounceView view) {
		
		this.view = view ;
		
		fileChooser = new JFileChooser() ;
		fileChooser.setCurrentDirectory( new File(".") ) ;
		fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY ) ;
		fileChooser.setDialogTitle( "匯出檔案" ) ;
		
		likeIcon = new ImageIcon("like.png") ;
		unlikeIcon = new ImageIcon("unlike.png") ;
		like = likeIcon.getImage() ;
		unlike = unlikeIcon.getImage() ;
		like = like.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH) ;
		unlike = unlike.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH) ;
		
		System.out.println( PostSerializable.class.getSimpleName() ) ;
		System.out.println( PostSerializable.class.getTypeName() ) ;
		
		try {
			
				read = new ObjectInputStream(  // 以名稱讀檔，較好一次處理多個檔案，但相對地由程式規定檔案名稱，詳情請見下面的export()
					Files.newInputStream( Paths.get("post") ) ) ;  // 讀post
				
				PostSerializable post = (PostSerializable) read.readObject() ;
				postInf.add( post ) ;
				
				int postNum = 2 ;  // 讀post2.post3.post4....
				while(true) {
					
					read = new ObjectInputStream(
							Files.newInputStream( Paths.get("post"+Integer.toString(postNum) ) ) ) ;
				
					post = (PostSerializable) read.readObject() ;
					postInf.add( post ) ;
					postNum++ ;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println( "檔案讀取完畢。" ) ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
			System.out.println( "沒有搜尋到檔案。程式關閉。" ) ;
			System.exit(0);
		} 
		
		view.getPages().setModel( new SpinnerNumberModel( 1, 1, postInf.size(), 1 ) ) ;  // 設置換頁上限
		
		view.getEditTime().setText( postInf.get(0).getEditTime().toString() ) ;  // 將資料匯入View、顯示
		view.getContent().setText( postInf.get(0).getContent() ) ;
		if( postInf.get(0).getIsLike() ) {
			view.getIcon().setIcon( new ImageIcon(like) ) ;
		}
		else {
			view.getIcon().setIcon( new ImageIcon(unlike) ) ;
		}
		
		view.revalidate() ;  //  畫面重整、更新
		view.repaint() ;
	}
	
	public void load() {  // 匯入內容
		try {
			
			fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY ) ;
			fileChooser.showOpenDialog( view ) ;
			
			read = new ObjectInputStream(
					Files.newInputStream( Paths.get( fileChooser.getSelectedFile().getAbsolutePath() ) ) ) ;
				
				PostSerializable post = (PostSerializable) read.readObject() ;
				
				view.getContent().setText( post.getContent() ) ;
				
				view.revalidate( ) ;
				view.repaint() ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}
	}
	public void export() {  // 儲存
		try {
			export = new ObjectOutputStream(
					Files.newOutputStream( Paths.get( 
							(view.getPages().getValue().toString().equalsIgnoreCase("1") ? "post" : "post" + view.getPages().getValue().toString() ) ) ) ) ;
			
			export.writeObject( postInf.get( (int) view.getPages().getValue()-1 ) ) ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void export_as() {  // 另存新檔
		
		fileChooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES ) ;
		fileChooser.showOpenDialog( view ) ;
		
		try {
//			export = new ObjectOutputStream(
//					Files.newOutputStream( Paths.get( ( fileChooser.getSelectedFile().getAbsolutePath() ),
//							(view.getPages().getValue().toString().equalsIgnoreCase("1") ? "post" : "post" + view.getPages().getValue().toString() ) ) ) ) ;
			
			export = new ObjectOutputStream(
					Files.newOutputStream( Paths.get( fileChooser.getSelectedFile().getAbsolutePath() ) ) ) ;
			
			export.writeObject( postInf.get( (int) view.getPages().getValue()-1 ) ) ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<PostSerializable> getPostInf() {
		return postInf;
	}
	public Image getLike() {
		return like;
	}
	public Image getUnlike() {
		return unlike;
	}
}