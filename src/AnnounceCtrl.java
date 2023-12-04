

import java.awt.BorderLayout;
import java.awt.event.* ;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnnounceCtrl {  // 管理Listener，處理View與Model的互動
	
	private final AnnounceView view ;
	private final AnnounceModel model ;
	
	private final EditHDL editHDL ;
	private final NewPostHDL newPostHDL ;
	private final SaveHDL saveHDL ;
	private final Save_asHDL save_asHDL ;
	private final LoadHDL loadHDL ;
	private final CancelHDL cancelHDL ;
	private final PagesHDL pagesHDL ;
	private final IconHDL iconHDL ;
	
	private Boolean newPost = false ;  // 判斷是否為全新貼文的值(控制Cancel時是否刪除資料庫(ArrayList)內的Post)
	
	public AnnounceCtrl( AnnounceView view, AnnounceModel model ) {
		
		this.view = view ;
		this.model = model ;
		
		editHDL = new EditHDL() ;
		newPostHDL = new NewPostHDL() ;
		saveHDL = new SaveHDL() ;
		save_asHDL = new Save_asHDL() ;
		loadHDL = new LoadHDL() ;
		cancelHDL = new CancelHDL() ;
		pagesHDL = new PagesHDL() ;
		iconHDL = new IconHDL() ;
		
		if( view.getMode() == 0 ) {  
			view.getEdit().addActionListener(editHDL) ;
			view.getNewPost().addActionListener(newPostHDL) ;
			view.getSave().addActionListener(saveHDL) ;
			view.getSaveAs().addActionListener(save_asHDL) ;
			view.getLoad().addActionListener(loadHDL) ;
			view.getCancel().addActionListener(cancelHDL) ;
		}
		
		view.getPages().addChangeListener(pagesHDL) ;
		view.getIcon().addActionListener(iconHDL) ;
		
	}
	
	private class EditHDL implements ActionListener {  // 編輯
		@Override
		public void actionPerformed(ActionEvent event) {
			view.remove( view.getToolPane() ) ;
			view.add( view.getEditPane(), BorderLayout.SOUTH ) ;
			view.getContent().setEditable(true) ;
			view.revalidate() ;
			view.repaint() ;
		}
	}
	/*
	 *   因為想試試看MVC架構，所以程式碼寫得有點亂，抱歉 (/_ _)/
	 *   大部分都是因為要取得View內的元件並呼叫它的函式才變得這麼長。
	 */
	private class NewPostHDL implements ActionListener {  // 全新貼文
		@Override
		public void actionPerformed(ActionEvent event) {
			view.remove( view.getToolPane() ) ;
			view.add( view.getEditPane(), BorderLayout.SOUTH ) ;
			view.getContent().setText("") ;
			view.getContent().setEditable(true) ;
			model.getPostInf().add( new PostSerializable() ) ;
			model.getPostInf().get( model.getPostInf().size()-1 ).setIsLike(false) ;
			
			view.getPages().setModel( new SpinnerNumberModel( model.getPostInf().size(), 1, model.getPostInf().size(), 1 ) ) ;
			view.revalidate() ;
			view.repaint() ;
			
			newPost = true ;
		}
	}
	private class SaveHDL implements ActionListener {  // 儲存
		@Override
		public void actionPerformed(ActionEvent event) {
			if( model.getPostInf().get( (int) view.getPages().getValue()-1 ).getIsLike() )
			{
				view.getIcon().setIcon( new ImageIcon( model.getLike() ) ) ;
			}
			else {
				view.getIcon().setIcon( new ImageIcon( model.getUnlike() ) ) ;
			}
			model.getPostInf().get( (int) view.getPages().getValue()-1 ).setContent( view.getContent().getText() ) ;
			model.getPostInf().get( (int) view.getPages().getValue()-1 ).setEditTime( new Date() ) ;
			view.getEditTime().setText( model.getPostInf().get( (int) view.getPages().getValue()-1 ).getEditTime().toString() ) ;
			model.export() ; 
			view.getContent().setEditable(false) ;
			view.remove( view.getEditPane() ) ;
			view.add( view.getToolPane(), BorderLayout.SOUTH ) ;
			view.revalidate() ;
			view.repaint() ;
		}
	}
	private class Save_asHDL implements ActionListener {  // 另存新檔
		@Override
		public void actionPerformed(ActionEvent event) {
			model.export_as() ;
		}
	}
	private class LoadHDL implements ActionListener {  // 匯入
		@Override
		public void actionPerformed(ActionEvent event) {
			model.load() ; 
		}
	}
	private class CancelHDL implements ActionListener {  // 取消
		@Override
		public void actionPerformed(ActionEvent event) {
			if(newPost) {
				model.getPostInf().remove( model.getPostInf().size()-1 ) ;
				view.getPages().setModel( new SpinnerNumberModel( 1, 1, model.getPostInf().size(), 1 ) ) ;
				newPost = false ;
			}
			
			view.getContent().setText( model.getPostInf().get( (int) view.getPages().getValue()-1 ).getContent() ) ;
			view.getContent().setEditable(false) ;
			view.remove( view.getEditPane() ) ;
			view.add( view.getToolPane(), BorderLayout.SOUTH ) ;
			view.revalidate() ;
			view.repaint() ;
		}
	}
	private class PagesHDL implements ChangeListener {  // 換頁
		@Override
		public void stateChanged(ChangeEvent event) {
			if( model.getPostInf().get( (int) view.getPages().getValue()-1 ).getIsLike() )
			{
				view.getIcon().setIcon( new ImageIcon( model.getLike() ) ) ;
			}
				
			else {
				view.getIcon().setIcon( new ImageIcon( model.getUnlike() ) ) ;
			}
			view.getEditTime().setText( model.getPostInf().get( (int) view.getPages().getValue()-1 ).getEditTime().toString() ) ;
			view.getContent().setText( model.getPostInf().get( (int) view.getPages().getValue()-1 ).getContent() ) ;
			view.revalidate() ;
			view.repaint() ;
		}
	}
	private class IconHDL implements ActionListener {  // 點讚
		@Override
		public void actionPerformed(ActionEvent event) {
			if( model.getPostInf().get( (int) view.getPages().getValue()-1 ).getIsLike() )
			{
				view.getIcon().setIcon( new ImageIcon( model.getUnlike() ) ) ;
				model.getPostInf().get( (int) view.getPages().getValue()-1 ).setIsLike( false ) ;
			}
			else {
				view.getIcon().setIcon( new ImageIcon( model.getLike() ) ) ;
				model.getPostInf().get( (int) view.getPages().getValue()-1 ).setIsLike( true ) ;
			}
			model.export() ;
			view.revalidate() ;
			view.repaint() ;
		}
	}
	
}
