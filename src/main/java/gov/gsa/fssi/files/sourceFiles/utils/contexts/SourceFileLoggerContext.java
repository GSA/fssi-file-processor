package main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts;

import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileLoggerStrategy;

public class SourceFileLoggerContext {
	   private SourceFileLoggerStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setSourceFileLoggerStrategy(SourceFileLoggerStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public SourceFileLoggerStrategy getSourceFileLoggerStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param field
		 * @param constraint
		 * @param data
		 */
		public void log(String directory, SourceFile sourceFile) {
			strategy.createLog(directory, sourceFile);
	   }
}
