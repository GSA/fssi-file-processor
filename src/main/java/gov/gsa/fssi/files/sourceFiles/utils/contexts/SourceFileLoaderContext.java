package main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts;

import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileLoaderStrategy;

public class SourceFileLoaderContext {
	   private SourceFileLoaderStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setSourceFileLoaderStrategy(SourceFileLoaderStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public SourceFileLoaderStrategy getSourceFileLoaderStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param field
		 * @param constraint
		 * @param data
		 */
		public void load(String fileName, SourceFile sourceFile) {
			strategy.load(fileName, sourceFile); //Validate Constraint
	   }
}
