

import java.awt.BorderLayout;
import java.awt.event.* ;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnnounceCtrl {  // �޲zListener�A�B�zView�PModel������
	
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
	
	private Boolean newPost = false ;  // �P�_�O�_�����s�K�媺��(����Cancel�ɬO�_�R����Ʈw(ArrayList)����Post)
	
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
	
	private class EditHDL implements ActionListener {  // �s��
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
	 *   �]���Q�ոլ�MVC�[�c�A�ҥH�{���X�g�o���I�áA��p (/_ _)/
	 *   �j�������O�]���n���oView��������éI�s�����禡�~�ܱo�o����C
	 */
	private class NewPostHDL implements ActionListener {  // ���s�K��
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
	private class SaveHDL implements ActionListener {  // �x�s
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
	private class Save_asHDL implements ActionListener {  // �t�s�s��
		@Override
		public void actionPerformed(ActionEvent event) {
			model.export_as() ;
		}
	}
	private class LoadHDL implements ActionListener {  // �פJ
		@Override
		public void actionPerformed(ActionEvent event) {
			model.load() ; 
		}
	}
	private class CancelHDL implements ActionListener {  // ����
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
	private class PagesHDL implements ChangeListener {  // ����
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
	private class IconHDL implements ActionListener {  // �I�g
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
