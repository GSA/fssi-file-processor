package gov.gsa.fssi.files.sourceFiles.utils.contexts;

import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileOrganizerStrategy;

public class SourceFileOrganizerContext {
	   private SourceFileOrganizerStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setSourceFileOrganizerStrategy(SourceFileOrganizerStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public SourceFileOrganizerStrategy getSourceFileOrganizerStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param field
		 * @param constraint
		 * @param data
		 */
		public void organize(SourceFile sourceFile) {
			strategy.organize(sourceFile);
	   }
}
