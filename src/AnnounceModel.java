

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
	
	private final ArrayList<PostSerializable> postInf = new ArrayList<PostSerializable>() ;  // �]���n�����A�n�x�sPost���
	
	private final JFileChooser fileChooser ;  // Ū�B����
	private static ObjectInputStream read ;  // static -> �i�H���Φb�ЫخɥDclass�Ыخɪ����
	private static ObjectOutputStream export ;
	private static Scanner scanner ;
	
	private final ImageIcon likeIcon ;  // �Ϲ��B�z(�j�p)
	private final ImageIcon unlikeIcon ;
	private Image like ;
	private Image unlike ;
	
	public AnnounceModel(AnnounceView view) {
		
		this.view = view ;
		
		fileChooser = new JFileChooser() ;
		fileChooser.setCurrentDirectory( new File(".") ) ;
		fileChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY ) ;
		fileChooser.setDialogTitle( "�ץX�ɮ�" ) ;
		
		likeIcon = new ImageIcon("like.png") ;
		unlikeIcon = new ImageIcon("unlike.png") ;
		like = likeIcon.getImage() ;
		unlike = unlikeIcon.getImage() ;
		like = like.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH) ;
		unlike = unlike.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH) ;
		
		System.out.println( PostSerializable.class.getSimpleName() ) ;
		System.out.println( PostSerializable.class.getTypeName() ) ;
		
		try {
			
				read = new ObjectInputStream(  // �H�W��Ū�ɡA���n�@���B�z�h���ɮסA���۹�a�ѵ{���W�w�ɮצW�١A�Ա��Ш��U����export()
					Files.newInputStream( Paths.get("post") ) ) ;  // Ūpost
				
				PostSerializable post = (PostSerializable) read.readObject() ;
				postInf.add( post ) ;
				
				int postNum = 2 ;  // Ūpost2.post3.post4....
				while(true) {
					
					read = new ObjectInputStream(
							Files.newInputStream( Paths.get("post"+Integer.toString(postNum) ) ) ) ;
				
					post = (PostSerializable) read.readObject() ;
					postInf.add( post ) ;
					postNum++ ;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println( "�ɮ�Ū�������C" ) ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
			System.out.println( "�S���j�M���ɮסC�{�������C" ) ;
			System.exit(0);
		} 
		
		view.getPages().setModel( new SpinnerNumberModel( 1, 1, postInf.size(), 1 ) ) ;  // �]�m�����W��
		
		view.getEditTime().setText( postInf.get(0).getEditTime().toString() ) ;  // �N��ƶפJView�B���
		view.getContent().setText( postInf.get(0).getContent() ) ;
		if( postInf.get(0).getIsLike() ) {
			view.getIcon().setIcon( new ImageIcon(like) ) ;
		}
		else {
			view.getIcon().setIcon( new ImageIcon(unlike) ) ;
		}
		
		view.revalidate() ;  //  �e������B��s
		view.repaint() ;
	}
	
	public void load() {  // �פJ���e
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
	public void export() {  // �x�s
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
	public void export_as() {  // �t�s�s��
		
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