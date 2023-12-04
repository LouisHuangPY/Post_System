



public class Main {
	public static void main( String[] args ) {
		AnnounceView view = new AnnounceView() ;
		AnnounceModel model = new AnnounceModel(view) ;
		AnnounceCtrl ctrl = new AnnounceCtrl(view, model) ;
	}
}
